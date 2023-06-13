from django import forms
from django.contrib import admin

from .models import AssistantConfiguration


class AssistantForm(forms.ModelForm):
    class Meta:
        model = AssistantConfiguration
        fields = ['intro_text', 'qa_prompt']
        widgets = {
            'intro_text': forms.Textarea(attrs={'rows': 15, 'cols': 80}),
            'qa_prompt': forms.Textarea(attrs={'rows': 15, 'cols': 80}),
        }


class AssistantConfigurationAdmin(admin.ModelAdmin):
    form = AssistantForm
    readonly_fields = ('config_id',)
    fieldsets = (
        ('Assistant', {
            'fields': ('name', 'knowledge_base', 'intro_text', 'qa_prompt', 'default_assistant', 'default_manager')
    }),
        ('Configuration', {
            'fields': ('model', 'language_code', 'max_tokens', 'temperature', 'top_p')
        })
    )
    list_display = ('name', 'intro_text', 'default_manager')

    def save_model(self, request, obj, form, change):
        if obj.default_assistant:
            AssistantConfiguration.objects.filter(default_assistant=True).update(default_assistant=False)
        super().save_model(request, obj, form, change)


admin.site.register(AssistantConfiguration, AssistantConfigurationAdmin)
