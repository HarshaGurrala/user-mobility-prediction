from typing import Optional

from pydantic import BaseModel, EmailStr


class EmergencyContactCreate(BaseModel):
    name: str
    phone_number: str
    email: Optional[EmailStr] = None
    relationship_type: str


class EmergencyContactResponse(BaseModel):
    id: int
    name: str
    phone_number: str
    email: Optional[EmailStr] = None
    relationship_type: str

    class Config:
        from_attributes = True

class EmergencyContactUpdate(BaseModel):
    name: str
    phone_number: str
    email: Optional[EmailStr] = None
    relationship_type: str