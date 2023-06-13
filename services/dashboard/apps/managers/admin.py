import re

from django.contrib import admin
from django.utils.html import format_html
from managers.models import Manager
from apps.utils import remove_non_digits


class ManagerAdmin(admin.ModelAdmin):
    fields = (
        'first_name', 'last_name', 'description', 'phone_number', 'email', 'email_provider', 'authentication_url',
        'time_zone', 'assistant_configuration', 'hitl_handle', '_authenticated')
    readonly_fields = ('authentication_url', '_authenticated')
    search_fields = ('first_name', 'last_name', 'phone_number', 'email')
    list_display = (
        'first_name', 'last_name', 'phone_number', 'email', 'time_zone', 'authentication_url', '_authenticated')
    exclude = ('auth_url',)

    def authentication_url(self, obj):
        return format_html("<a href='{url}'>Click here to authenticate</a>", url=obj.auth_url) if obj.auth_url else None

    def _authenticated(self, obj):
        return "True" if obj.authenticated else "False"

    def save_model(self, request, obj, form, change):
        obj.phone_number = remove_non_digits(obj.phone_number)
        super().save_model(request, obj, form, change)

    _authenticated.short_description = "Authenticated"


admin.site.register(Manager, ManagerAdmin)
