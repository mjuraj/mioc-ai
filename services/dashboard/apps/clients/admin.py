from django.contrib import admin

from .models import Client
from apps.utils import remove_non_digits


class ClientAdmin(admin.ModelAdmin):
    list_display = ('first_name', 'last_name', 'phone_number', 'email', 'manager', 'assistant_configuration')
    readonly_fields = ('id',)
    search_fields = ('first_name', 'last_name', 'phone_number', 'email')
    list_filter = ('manager', 'assistant_configuration')
    fieldsets = (
        ('General info', {
            'fields': ('first_name', 'last_name', 'phone_number', 'email')
        }),
        ('Configuration', {
            'fields': ('description', 'assistant_configuration', 'manager', 'time_zone')
        })
    )

    def save_model(self, request, obj, form, change):
        obj.phone_number = remove_non_digits(obj.phone_number)
        super().save_model(request, obj, form, change)


admin.site.register(Client, ClientAdmin)
