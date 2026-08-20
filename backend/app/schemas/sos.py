from typing import Optional

from pydantic import BaseModel


class SOSRequest(BaseModel):
    latitude: Optional[float] = None
    longitude: Optional[float] = None
    message: str = "Emergency! I need help."