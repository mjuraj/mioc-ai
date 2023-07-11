import yaml
from django.contrib import admin
from django.shortcuts import redirect
from django.urls import reverse

from configurations.models import Configuration


class ConfigurationAdmin(admin.ModelAdmin):
    exclude = ('screens_compiled', 'id')

    def save_model(self, request, obj, form, change):
        try:
            obj.screens_compiled = yaml.load(obj.screens, Loader=yaml.FullLoader)
        except Exception as e:
            self.message_user(request, f'Error: {e}', level='error')
            return
        super().save_model(request, obj, form, change)

    def changelist_view(self, request, extra_context=None):
        qs = super().get_queryset(request)
        obj = qs.last()
        return redirect(reverse('admin:configurations_configuration_change', args=(obj.pk,)))


admin.site.register(Configuration, ConfigurationAdmin)
