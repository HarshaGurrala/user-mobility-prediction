from pydantic import BaseModel


class SOSRequest(BaseModel):
    latitude: float
    longitude: float
    message: str = "Emergency! I need help."