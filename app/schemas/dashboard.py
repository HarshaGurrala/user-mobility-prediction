from pydantic import BaseModel
from typing import Optional


class LocationMapResponse(BaseModel):

    latitude: Optional[float]

    longitude: Optional[float]

    place_name: Optional[str]


class PredictionMapResponse(BaseModel):

    location: Optional[str]

    confidence: Optional[float]


class SafetyResponse(BaseModel):

    status: str

    message: str


class ChildDashboardResponse(BaseModel):

    user_id: int

    current_location: LocationMapResponse

    prediction: PredictionMapResponse

    safety_status: SafetyResponse