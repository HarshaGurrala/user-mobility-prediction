from typing import Optional

from pydantic import BaseModel, Field, ConfigDict


class SafeLocationBase(BaseModel):
    location_name: str = Field(..., max_length=100)
    latitude: float
    longitude: float
    radius: float = 100


class SafeLocationCreate(SafeLocationBase):
    pass


class SafeLocationUpdate(BaseModel):
    location_name: Optional[str] = Field(None, max_length=100)
    latitude: Optional[float] = None
    longitude: Optional[float] = None
    radius: Optional[float] = None


class SafeLocationResponse(SafeLocationBase):
    id: int
    user_id: int

    model_config = ConfigDict(from_attributes=True)