from pydantic import BaseModel


class SafeZoneCreate(BaseModel):

    name: str

    address: str

    radius: float