from typing import Optional
from pydantic import BaseModel


class GuardianTeamBase(BaseModel):
    person_type: str
    roll_no: Optional[str] = None
    name: str
    email: Optional[str] = None
    role: Optional[str] = None
    details: Optional[str] = None


class GuardianTeamCreate(GuardianTeamBase):
    pass


class GuardianTeamUpdate(BaseModel):
    roll_no: Optional[str] = None
    name: Optional[str] = None
    email: Optional[str] = None
    role: Optional[str] = None
    details: Optional[str] = None


class GuardianTeamResponse(BaseModel):
    id: int
    guardian_id: int
    person_type: str
    roll_no: Optional[str] = None
    name: str
    email: Optional[str] = None
    role: Optional[str] = None
    details: Optional[str] = None
    image: Optional[str] = None

    class Config:
        from_attributes = True