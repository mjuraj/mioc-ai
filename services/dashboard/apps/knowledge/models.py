from base.models import BaseModel
from django.db import models
from django.db.models.signals import post_delete
from django.dispatch import receiver

from services.dashboard.apps.knowledge.utils import remove_file_from_azure


class File(BaseModel):
    name = models.CharField(max_length=255, unique=True)
    text = models.TextField()
    item_status = models.CharField(max_length=255)

    def __str__(self):
        return f'{self.name}'

    def to_dict(self):
        return {
            'name': self.name,
            'text': self.text,
            'itemStatus': self.item_status,
        }

@receiver(post_delete, sender=File)
def file_deleted(instance: File, *_, **__):
    remove_file_from_azure(instance.name)
