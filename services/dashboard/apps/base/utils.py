import yaml
from django.contrib.auth.models import Group, Permission

from configurations.models import Configuration
from const import DEFAULT_MANAGER_ID, DEFAULT_MANAGER_EMAIL, DEFAULT_MANAGER_PHONE_NUMBER, DEFAULT_MANAGER_FIRST_NAME, \
    DEFAULT_MANAGER_LAST_NAME, DEFAULT_MANAGER_DESCRIPTION, SCREENS_SPECIFICATION, DEFAULT_KNOWLEDGE
from knowledge.models import File
from knowledge.views import ServiceStub
from managers.models import Manager, EmailProvider
from text_embedder.api import TextEmbedderAPI
from utils import get_or_create_default_assistant_config


def init_database_values():
    if not Configuration.objects.all().exists():
        Configuration.objects.create(
            screens=SCREENS_SPECIFICATION,
            screens_compiled=yaml.load(SCREENS_SPECIFICATION, Loader=yaml.FullLoader)
        )

    if not Group.objects.filter(name='managers').exists():
        group = Group.objects.create(name='managers')
        group.permissions.set([
            Permission.objects.get(name="Can add file"),
            Permission.objects.get(name="Can view file"),
            Permission.objects.get(name="Can change file"),
            Permission.objects.get(name="Can delete file"),

            Permission.objects.get(name="Can add Assistant"),
            Permission.objects.get(name="Can view Assistant"),
            Permission.objects.get(name="Can change Assistant"),
            Permission.objects.get(name="Can delete Assistant"),

            Permission.objects.get(name="Can add User"),
            Permission.objects.get(name="Can view User"),
            Permission.objects.get(name="Can change User"),
            Permission.objects.get(name="Can delete User"),

            Permission.objects.get(name="Can add Manager"),
            Permission.objects.get(name="Can view Manager"),
            Permission.objects.get(name="Can change Manager"),
            Permission.objects.get(name="Can delete Manager"),
        ])

    if not Manager.objects.all().exists():
        Manager.objects.create(id=DEFAULT_MANAGER_ID, first_name=DEFAULT_MANAGER_FIRST_NAME,
                               last_name=DEFAULT_MANAGER_LAST_NAME, description=DEFAULT_MANAGER_DESCRIPTION,
                               phone_number=DEFAULT_MANAGER_PHONE_NUMBER, email=DEFAULT_MANAGER_EMAIL,
                               email_provider=EmailProvider.GMAIL, authenticated=False)

    assistant_config = get_or_create_default_assistant_config()

    if not File.objects.all().exists():
        file = File.objects.create(name="default-knowledge", text=DEFAULT_KNOWLEDGE, item_status="PROCESSING")
        assistant_config.knowledge_base.add(file)
        assistant_config.save()

        TextEmbedderAPI.update_or_create_embeddings(file.text, file.id).then(ServiceStub().update_item_status,
                                                                             callback_data={"id": file.id})