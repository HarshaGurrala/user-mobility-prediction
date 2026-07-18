from typing import Optional

from pydantic import BaseModel


class SafeLocationCreate(BaseModel):
    location_name: str
    latitude: float
    longitude: float
    radius: Optional[float] = 100


class SafeLocationResponse(BaseModel):
    id: int
    user_id: int
    location_name: str
    latitude: float
    longitude: float
    radius: float

    class Config:
        orm_mode = True