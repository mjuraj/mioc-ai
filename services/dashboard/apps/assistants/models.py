from typing import Type

from base.models import BaseModel
from django.core.validators import MaxValueValidator, MinValueValidator
from django.db import models
from django.db.models.signals import post_save, post_delete, pre_save, m2m_changed
from django.dispatch import receiver
from managers.models import Manager
from const import DEFAULT_MANAGER_ID
from forge.core.api.base import DataChangeType
from forge.utils.base import random_string

from services.dashboard.api.api import AssistantConfiguration as AssistantView
from apps.base.consts import QA_PROMPT_DEFAULT, QA_PROMPT_HELP_TEXT, \
    KNOWLEDGE_BASE_HELP_TEXT, INTRO_TEXT_HELP_TEXT, INTRO_TEXT_DEFAULT

class AssistantConfiguration(BaseModel):
    GPT_MODELS = (
        ('GPT-3', 'GPT-3',),
        ('GPT-3.5', 'GPT-3.5'),
        # ('GPT-4', 'GPT-4'), # add back once access is approved
    )
    LANGUAGE_CODES = (
        ('en', 'en'),
        ('hr', 'hr'),
    )

    config_id = models.CharField(max_length=255, unique=True)
    intro_text = models.TextField(default=INTRO_TEXT_DEFAULT,
                                  verbose_name="Persona",
                                  help_text=INTRO_TEXT_HELP_TEXT)
    qa_prompt = models.TextField(default=QA_PROMPT_DEFAULT,
                                 verbose_name="Instructions",
                                 help_text=QA_PROMPT_HELP_TEXT)
    knowledge_base = models.ManyToManyField('knowledge.File',
                                            limit_choices_to={"item_status": "ADDED AS KNOWLEDGE"},
                                            blank=True,
                                            help_text=KNOWLEDGE_BASE_HELP_TEXT)
    default_assistant = models.BooleanField(default=False)
    default_manager = models.ForeignKey(Manager, on_delete=models.RESTRICT, verbose_name='Owner',
                                        default=DEFAULT_MANAGER_ID)
    name = models.CharField(max_length=255, unique=True)
    model = models.CharField(max_length=255, choices=GPT_MODELS, default='GPT-3', help_text="OpenAI model to use.")
    language_code = models.CharField(max_length=255, choices=LANGUAGE_CODES, default='en',
                                     help_text="Language of model input data.")
    max_tokens = models.IntegerField(default=120, validators=[MaxValueValidator(1000), MinValueValidator(1)],
                                     help_text="Max length of generated replies. 1 token is approx. 4 characters.")
    temperature = models.FloatField(default=0.8, validators=[MaxValueValidator(2.0), MinValueValidator(0.0)],
                                    help_text="Measure of response creativity, "
                                              "from 0 (strictest) to 1 (most creative).")
    top_p = models.FloatField(default=1.0, validators=[MaxValueValidator(1.0), MinValueValidator(0.0)],
                              help_text="Alternative to temperature (should be set to 1.0 if top p is used and vice "
                                        "versa). Follows the same scale: from 0 (strictest) to 1 (most creative)")


    class Meta:
        verbose_name = "Assistant"
        verbose_name_plural = "My assistants"

    def __str__(self):
        return f'{self.name}'

    def to_dict(self):
        return {
            'configId': self.config_id,
            'name': self.name,
            'introText': self.intro_text,
            'qaPrompt': self.qa_prompt,
            'knowledgeBaseIds': [file.id for file in self.knowledge_base.all()],
            'defaultAssistant': self.default_assistant,
            'defaultManagerId': self.default_manager.id,
            'model': self.model,
            'languageCode': self.language_code,
            'maxTokens': self.max_tokens,
            'temperature': self.temperature,
            'topP': self.top_p,
        }



@receiver(pre_save, sender=AssistantConfiguration)
def fill_config_id(sender: Type[AssistantConfiguration], instance: AssistantConfiguration, *_, **__):
    instance.config_id = instance.config_id or random_string(10)

@receiver(post_save, sender=AssistantConfiguration)
def assistant_config_updated(sender: Type[AssistantConfiguration], instance: AssistantConfiguration, created: bool, *_,
                             **__):
    AssistantView(**instance.to_dict()).emit(change_type=DataChangeType.CREATED if created else DataChangeType.UPDATED)


@receiver(m2m_changed, sender=AssistantConfiguration.knowledge_base.through)
def assistant_knowledge_base_updated(sender, instance: AssistantConfiguration, action: str, *_, **__):
    if action.startswith('post_'):
        AssistantView(**instance.to_dict()).emit(change_type=DataChangeType.UPDATED)


@receiver(post_delete, sender=AssistantConfiguration)
def assistant_config_deleted(sender: Type[AssistantConfiguration], instance: AssistantConfiguration, *_, **__):
    AssistantView(**instance.to_dict()).emit(change_type=DataChangeType.DELETED)
