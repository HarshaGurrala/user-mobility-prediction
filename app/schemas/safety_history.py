from datetime import datetime
from typing import Optional

from pydantic import BaseModel


class SafetyHistoryCreate(BaseModel):

    user_id: int

    safe_location_id: Optional[int] = None

    event_type: str

    location_name: Optional[str] = None

    latitude: float

    longitude: float

    address: Optional[str] = None



class SafetyHistoryResponse(BaseModel):

    id: int

    user_id: int

    safe_location_id: Optional[int]

    event_type: str

    location_name: Optional[str]

    latitude: float

    longitude: float

    address: Optional[str]

    timestamp: datetime


    class Config:
        orm_mode = True