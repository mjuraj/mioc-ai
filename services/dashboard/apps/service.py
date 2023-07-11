import logging

from asgiref.sync import sync_to_async
from forge.conf import settings as forge_settings
from forge.core.api.decorators import api
from forge.core.api.base import ErrorReply
from forge.core.base import BaseService

from clients.models import Client
from knowledge.models import File
from managers.models import Manager
from assistants.models import AssistantConfiguration
from services.dashboard.api import Client as ClientView
from services.dashboard.api import Manager as ManagerView
from text_embedder.api import EmbeddingsCreated
from apps.utils import get_or_create_default_assistant_config

logger = logging.getLogger(forge_settings.DEFAULT_LOGGER)


class Service(BaseService):

    @api
    async def update_or_create_client(self, client: ClientView) -> None:
        def _update_or_create_client():
            default_assistant_config = get_or_create_default_assistant_config()
            client_obj, _ = Client.objects.update_or_create(
                id=client.id,
                defaults={
                "first_name": client.firstName,
                "last_name": client.lastName,
                "description": client.description,
                "phone_number": client.phoneNumber,
                "email": client.email,
                "time_zone": client.timeZone if client.timeZone else "America/New_York",
                "manager": Manager.objects.get(id=client.managerId) if client.managerId
                else Manager.objects.get(id=default_assistant_config.to_dict().get("defaultManagerId")),
                "assistant_configuration":
                    AssistantConfiguration.objects.get(config_id=client.assistantConfigurationId)
                    if client.assistantConfigurationId
                    else default_assistant_config
                }
            )

        await sync_to_async(_update_or_create_client)()

    @api
    async def update_or_create_manager(self, manager: ManagerView) -> None:
        def _update_or_create_manager():
            client_obj, _ = Manager.objects.update_or_create(
                id=manager.id,
                defaults={
                    "id": manager.id,
                    "first_name": manager.firstName,
                    "last_name": manager.lastName,
                    "description": manager.description,
                    "phone_number": manager.phoneNumber,
                    "email": manager.email,
                    "email_provider": manager.emailProvider,
                    "auth_url": manager.authUrl,
                    "authenticated": manager.authenticated,
                    "time_zone": manager.timeZone,
                    "hitl_handle": manager.hitlHandle,
                    "assistant_configuration":
                        AssistantConfiguration.objects.get(config_id=manager.assistantConfigurationId)
                        if manager.assistantConfigurationId else get_or_create_default_assistant_config(),
                }
            )

        await sync_to_async(_update_or_create_manager)()

    async def update_item_status(self, reply: EmbeddingsCreated, error: ErrorReply, callback_data: dict):
        if error:
            logger.error(f"Error while processing file {callback_data['id']}: {error}")
            status = "ERROR WHILE PROCESSING"
        else:
            status = "ADDED AS KNOWLEDGE"

        def _update_item_status():
            file = File.objects.get(id=callback_data["id"])
            file.item_status = status
            file.save()

        await sync_to_async(_update_item_status)()
