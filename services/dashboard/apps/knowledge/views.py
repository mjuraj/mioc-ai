import logging

import ast
from django.http import HttpResponse
from django.http import JsonResponse
from django.shortcuts import render
from forge.conf import settings as forge_settings
from service import Service

from .models import File
from .utils import ChunkedFileUploadHandler, extract_text_from_file, upload_txt_file_to_azure, strip_extension

logger = logging.getLogger(forge_settings.DEFAULT_LOGGER)


def upload(request):
    if request.method == 'POST':
        file_name = request.POST.get('file_name')
        chunk = request.FILES.get('chunk')
        is_last_chunk = request.POST.get('is_last_chunk')
        is_last_chunk = is_last_chunk == 'true'
        handler = ChunkedFileUploadHandler(file_name)
        handler.save_chunk(chunk.read())
        if is_last_chunk:
            file_data = handler.get_whole_file()
            text = extract_text_from_file(file_data, file_name)
            file_name = strip_extension(file_name) + '.txt'
            file, _ = File.objects.update_or_create(
                name=file_name,
                defaults={
                    'text': text,
                    'item_status': "NOT PROCESSED"
                }
            )
            file.save()
            upload_txt_file_to_azure(file.name, file.text)
            handler.delete_temp_file()
            return HttpResponse("File uploaded successfully")
        else:
            return HttpResponse("Chunk uploaded successfully")

    return HttpResponse("Invalid request method")


def download(request, name):
    response = HttpResponse(content_type='text/plain')
    my_object = File.objects.get(name=name)
    response['Content-Disposition'] = 'attachment; filename={}.txt'.format(f'{my_object.name}')
    response.write(f'{my_object.text}')
    return response


def upload_file(request):
    return render(request, 'admin/upload.html')


class ServiceStub():

    def update_item_status(self):
        pass

    def callback_data(self) -> dict:
        return {
            'name': Service.__name__
        }
