from typing import Type

from base.models import BaseModel
from django.db import models
from django.db.models.signals import post_save, post_delete
from django.dispatch import receiver
from forge.core.api.base import DataChangeType
from forge.utils import random_string
from apps.utils import TimeZone, is_phone_valid

from services.dashboard.api import Client as ClientView


class Client(BaseModel):
    id = models.CharField(primary_key=True, max_length=128, default=random_string)
    first_name = models.CharField(max_length=255)
    last_name = models.CharField(max_length=255, null=True, blank=True)
    description = models.TextField(null=True, blank=True)
    phone_number = models.CharField(max_length=15, unique=True, validators=[is_phone_valid])
    email = models.EmailField(null=True, blank=True)
    time_zone = models.CharField(max_length=255, choices=TimeZone.choices, default='America/New_York')
    manager = models.ForeignKey('managers.Manager', on_delete=models.RESTRICT)
    assistant_configuration = models.ForeignKey('assistants.AssistantConfiguration', blank=True, null=True,
                                                on_delete=models.SET_NULL,
                                                verbose_name='Dedicated assistant',
                                                limit_choices_to=~models.Q(name__in=['Unregistered Client']))

    class Meta:
        verbose_name = "User"
        verbose_name_plural = "My users"

    def __str__(self):
        return f'{self.first_name} {self.last_name}'

    def to_dict(self):
        return {
            'id': self.id,
            'firstName': self.first_name,
            'lastName': self.last_name,
            'description': self.description,
            'phoneNumber': self.phone_number,
            'email': self.email,
            'managerId': self.manager.id,
            'timeZone': self.time_zone,
            'assistantConfigurationId': self.assistant_configuration.config_id
            if getattr(self, 'assistant_configuration', None) else None,
        }


@receiver(post_save, sender=Client)
def client_updated(sender: Type[Client], instance: Client, created: bool, *_, **__):
    ClientView(**instance.to_dict()).emit(change_type=DataChangeType.CREATED if created else DataChangeType.UPDATED)


@receiver(post_delete, sender=Client)
def client_deleted(sender: Type[Client], instance: Client, *_, **__):
    ClientView(**instance.to_dict()).emit(change_type=DataChangeType.DELETED)
