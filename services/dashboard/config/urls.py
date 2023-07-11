# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.conf import settings
from django.conf.urls import include, url
from django.conf.urls.static import static
from django.contrib import admin
from django.urls import path
from django.views.generic import RedirectView
from django.contrib.staticfiles.storage import staticfiles_storage

urlpatterns = [
      path('jet/', include('jet.urls', 'jet')),
      path('jet/dashboard/', include('jet.dashboard.urls', 'jet-dashboard')),
      url(settings.ADMIN_URL, admin.site.urls),
      path('knowledge/', include('knowledge.urls')),
      path('favicon.ico', RedirectView.as_view(url=staticfiles_storage.url('/favicon.ico'))),
      url('', RedirectView.as_view(url='/admin/')),
              ] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
