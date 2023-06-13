from django.contrib.admin import apps


class BaseAdminConfig(apps.AdminConfig):
    default_site = 'base.admin.BaseAdminSite'
