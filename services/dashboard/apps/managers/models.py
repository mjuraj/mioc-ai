from typing import Type

from django.db import models
from django.db.models.signals import post_save, post_delete
from django.dispatch import receiver
from django.contrib.auth.models import User, Group
from forge.core.api.base import DataChangeType
from forge.utils import random_string

from base.models import BaseModel
from services.dashboard.api import Manager as ManagerView

EmailProvider = models.TextChoices('EmailProvider', 'GMAIL OFFICE365 ICLOUD EXCHANGE IMAP')


class Manager(BaseModel):
    id = models.CharField(primary_key=True, max_length=128, default=random_string)
    first_name = models.CharField(max_length=255)
    last_name = models.CharField(max_length=255)
    description = models.TextField(null=True, blank=True)
    phone_number = models.CharField(max_length=15, unique=True, null=True, blank=True)
    email = models.EmailField(unique=True)
    email_provider = models.CharField(max_length=255, choices=EmailProvider.choices, default=EmailProvider.GMAIL)
    time_zone = models.CharField(max_length=255, default='America/New_York')
    hitl_handle = models.CharField(max_length=255, null=True, blank=True, default="@everyone")
    auth_url = models.CharField(max_length=1023, null=True, blank=True)
    authenticated = models.BooleanField(default=False)
    assistant_configuration = models.ForeignKey('assistants.AssistantConfiguration', blank=True, null=True,
                                                on_delete=models.SET_NULL,
                                                verbose_name='Dedicated assistant',
                                                limit_choices_to=~models.Q(name__in=['Unregistered Client']))

    class Meta:
        verbose_name = "Manager"
        verbose_name_plural = "My managers"

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
            'emailProvider': self.email_provider,
            'timeZone': self.time_zone,
            'hitlHandle': self.hitl_handle,
            'authUrl': self.auth_url,
            'authenticated': self.authenticated,
            'assistantConfigurationId': self.assistant_configuration.config_id if getattr(self,
                                                                                          'assistant_configuration',
                                                                                          None) else None,
        }


@receiver(post_save, sender=Manager)
def manager_updated(sender: Type[Manager], instance: Manager, created: bool, *_, **__):
    ManagerView(**instance.to_dict()).emit(change_type=DataChangeType.CREATED if created else DataChangeType.UPDATED)

    if not User.objects.filter(username=instance.email).exists():
        user = User.objects.create_user(username=instance.email,
                                        first_name=instance.first_name,
                                        last_name=instance.last_name,
                                        email=instance.email,
                                        is_staff=True,
                                        password=instance.first_name.lower() + instance.last_name.lower())
        group = Group.objects.get(name='managers')
        group.user_set.add(user)


@receiver(post_delete, sender=Manager)
def manager_deleted(sender: Type[Manager], instance: Manager, *_, **__):
    ManagerView(**instance.to_dict()).emit(change_type=DataChangeType.DELETED)
