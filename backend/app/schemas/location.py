from pydantic import BaseModel


class LocationCreate(BaseModel):
    latitude: float
    longitude: float
    speed: float = 0
    accuracy: float = 0