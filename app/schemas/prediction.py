from pydantic import BaseModel


class PredictionResponse(BaseModel):

    id: int
    user_id: int
    predicted_location: str
    confidence: float

    class Config:
        orm_mode = True