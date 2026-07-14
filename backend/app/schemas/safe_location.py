from pydantic import BaseModel


class SafeLocationCreate(BaseModel):
    location_name: str
    latitude: float
    longitude: float
    radius: int = 100