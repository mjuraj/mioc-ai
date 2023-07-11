from django.contrib import admin


class BaseAdminSite(admin.AdminSite):
    site_header = 'Moli Administration'
    site_title = 'Moli Admin'
    index_title = 'Moli Home'
    site_url = None
