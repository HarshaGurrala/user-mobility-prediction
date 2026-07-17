from pydantic import BaseModel


class PredictionResponse(BaseModel):

    predicted_place: str

    confidence: float

    eta: int

    latitude: float

    longitude: float