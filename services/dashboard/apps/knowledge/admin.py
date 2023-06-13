from django.contrib import admin
from django.urls import reverse
from django.utils.html import format_html
from django import forms
from text_embedder.api import TextEmbedderAPI

from .models import File
from .views import ServiceStub
from .utils import upload_txt_file_to_azure


class FileForm(forms.ModelForm):
    class Meta:
        model = File
        fields = ['text']
        widgets = {
            'text': forms.Textarea(attrs={'rows': 30, 'cols': 90}),
        }


class KnowledgeBaseAdmin(admin.ModelAdmin):
    form = FileForm
    fields = ('name', 'download_file', 'item_status', 'text')
    readonly_fields = ('name', 'download_file', 'item_status')
    list_display = ('name', 'item_status')
    actions = ['process_data', 'remove_embeddings']

    @admin.action(description='Add to knowledge base')
    def process_data(self, request, queryset):
        for file in queryset:
            TextEmbedderAPI.update_or_create_embeddings_from_storage(file.name, file.id).then(
                ServiceStub().update_item_status,
                callback_data={"id": file.id})
            file.item_status = "PROCESSING"
            file.save()

    @admin.action(description='Remove from knowledge base')
    def remove_embeddings(self, request, queryset):
        for file in queryset:
            TextEmbedderAPI.remove_embeddings(file.id)
            file.item_status = "NOT PROCESSED"
            file.save()

    def delete_queryset(self, request, queryset):
        for file in queryset:
            TextEmbedderAPI.remove_embeddings(file.id)

        super().delete_queryset(request, queryset)

    def save_model(self, request, obj, form, change):
        if not change or "text" in form.changed_data:
            obj.item_status = 'NOT PROCESSED'
        upload_txt_file_to_azure(obj.name, obj.text)
        super().save_model(request, obj, form, change)

    def get_readonly_fields(self, request, obj=None):
        if obj and obj.name:
            return ['name', 'download_file', 'item_status']
        else:
            return ['download_file', 'item_status']

    def download_file(self, obj):
        directed_path = reverse('download', args=[File.objects.get(pk=obj.pk).name])
        download_link = f'<a href={directed_path}>{File.objects.get(pk=obj.pk).name}</a>'
        return format_html(download_link)

    download_file.short_description = "Name"


admin.site.register(File, KnowledgeBaseAdmin)
