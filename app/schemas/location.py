from typing import Optional

from pydantic import BaseModel


class LocationCreate(BaseModel):

    latitude: float

    longitude: float

    accuracy: Optional[float] = None

    address: Optional[str] = None


class LocationResponse(BaseModel):

    id: int

    user_id: int

    latitude: float

    longitude: float

    accuracy: Optional[float]

    address: Optional[str]

    timestamp: str

    class Config:
        from_attributes = True