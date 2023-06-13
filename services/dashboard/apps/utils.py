import logging

import pytz
from django.db import models
from forge.conf import settings as forge_settings
from forge_storage import CloudStorageHandler
from django.core.exceptions import ValidationError
from assistants.models import AssistantConfiguration
from apps.base.consts import QA_PROMPT_DEFAULT, INTRO_TEXT_DEFAULT

logger = logging.getLogger(forge_settings.DEFAULT_LOGGER)


def upload_media(name: str, data: bytes, path: str) -> str:
    storage = CloudStorageHandler()
    return storage.upload(name=name, data=data, path=path)


TimeZone = models.TextChoices('TimeZone', ' '.join(pytz.common_timezones))

def remove_non_digits(value):
    if not value:
        return ""
    return "".join(char for char in value if char.isdigit())

def is_phone_valid(value):
    phone_number = remove_non_digits(value)
    if not phone_number:
        raise ValidationError('Phone number is not valid')

def get_or_create_default_assistant_config():
    if not AssistantConfiguration.objects.all().exists():
        return AssistantConfiguration.objects.create(name="Moli",
                                                          intro_text=INTRO_TEXT_DEFAULT,
                                                          qa_prompt=QA_PROMPT_DEFAULT,
                                                          default_assistant=True)

    return AssistantConfiguration.objects.get(default_assistant=True)