from forge.core.api.base import BaseAPI
from forge.core.api.decorators import api_interface

from .api import Client, Manager


@api_interface
class DashboardAPI(BaseAPI):
    service_id = 'dashboard'

    @staticmethod
    def update_or_create_client(client: Client) -> None:
        """Creates or updates a client"""

    @staticmethod
    def update_or_create_manager(manager: Manager) -> None:
        """Creates or updates a manager"""
