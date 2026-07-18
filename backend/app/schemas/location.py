from typing import Optional

from pydantic import BaseModel


class LocationCreate(BaseModel):

    latitude: float
    longitude: float
    accuracy: Optional[float] = None


class LocationResponse(BaseModel):

    id: int
    user_id: int
    latitude: float
    longitude: float
    accuracy: Optional[float]
    timestamp: str

    class Config:
        orm_mode = True