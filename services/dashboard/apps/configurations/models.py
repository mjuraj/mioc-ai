from typing import Type

from django.db import models
from django.db.models.signals import post_save
from django.dispatch import receiver
from forge.core.api.base import DataChangeType
from forge.utils import random_string

from base.models import BaseModel
from services.dashboard.api import Configuration as ConfigurationView


class Configuration(BaseModel):
    id = models.CharField(max_length=128, primary_key=True, default=random_string)
    screens = models.TextField(null=True, blank=True, default="")
    screens_compiled = models.JSONField(null=True, blank=True)

    def __str__(self):
        return f'{self.id}'

    def to_dict(self):
        return {
            'id': self.id,
            'screens': self.screens_compiled,
        }


@receiver(post_save, sender=Configuration)
def configuration_updated(sender: Type[Configuration], instance: Configuration, created: bool, *_, **__):
    ConfigurationView(**instance.to_dict()).emit(
        change_type=DataChangeType.CREATED if created else DataChangeType.UPDATED)
