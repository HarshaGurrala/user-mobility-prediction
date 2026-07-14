from pydantic import BaseModel


class EmergencyContactCreate(BaseModel):
    name: str
    relationship_name: str
    phone_number: str