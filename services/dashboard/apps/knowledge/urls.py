from django.urls import path

from . import views


urlpatterns = [
    path("upload", views.upload, name='upload'),
    path('download/<name>', views.download, name='download'),
    path('upload/file', views.upload_file, name='upload_file'),
]
