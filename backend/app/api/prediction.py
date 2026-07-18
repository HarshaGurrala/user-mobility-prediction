from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database.database import get_db

from app.services.prediction_service import (
    train_user_model,
    generate_prediction,
    get_latest_prediction
)

router = APIRouter(
    prefix="/prediction",
    tags=["Prediction"]
)


@router.post("/train/{user_id}")
def train_model(
    user_id: int,
    db: Session = Depends(get_db)
):

    return {
        "trained": train_user_model(
            db,
            user_id
        )
    }


@router.get("/next/{user_id}")
def predict_next_location(
    user_id: int,
    db: Session = Depends(get_db)
):

    latest = get_latest_prediction(
        db,
        user_id
    )

    if latest:

        return {
            "location": latest.predicted_location,
            "confidence": latest.confidence
        }

    return generate_prediction(
        db,
        user_id
    )