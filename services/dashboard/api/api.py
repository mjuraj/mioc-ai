from typing import List, Optional

from forge.core.db import EmittableDataModel


class AssistantConfiguration(EmittableDataModel):
    configId: str
    name: str
    introText: str
    qaPrompt: str
    defaultAssistant: bool
    knowledgeBaseIds: List[str]
    defaultManagerId: str
    model: str
    languageCode: str
    maxTokens: int
    temperature: float
    topP: float

    @classmethod
    def primary_field(cls) -> str:
        return "configId"


class Configuration(EmittableDataModel):
    id: str
    screens: dict


class Client(EmittableDataModel):
    id: str
    firstName: str
    lastName: Optional[str]
    description: Optional[str]
    phoneNumber: Optional[str]
    email: Optional[str]
    managerId: Optional[str]
    timeZone: Optional[str]
    assistantConfigurationId: Optional[str]

    def to_snakecase(self) -> dict:
        return {
            "id": self.id,
            "first_name": self.firstName,
            "last_name": self.lastName,
            "description": self.description,
            "phone_number": self.phoneNumber,
            "email": self.email,
            "time_zone": self.timeZone,
        }


class Manager(EmittableDataModel):
    id: str
    firstName: str
    lastName: str
    description: str
    phoneNumber: str
    email: str
    emailProvider: str
    authUrl: Optional[str]
    authenticated: bool
    timeZone: str
    hitlHandle: str
    assistantConfigurationId: Optional[str]

    def to_snakecase(self) -> dict:
        return {
            "id": self.id,
            "first_name": self.firstName,
            "last_name": self.lastName,
            "description": self.description,
            "phone_number": self.phoneNumber,
            "email": self.email,
            "email_provider": self.emailProvider,
            "auth_url": self.authUrl,
            "authenticated": self.authenticated,
            "time_zone": self.timeZone,
            "hitl_handle": self.hitlHandle,
        }
