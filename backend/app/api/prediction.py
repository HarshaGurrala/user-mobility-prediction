from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db

from app.dependencies.auth import get_current_user
from app.models.user import User


from app.services.prediction_service import (
    train_user_model,
    generate_prediction,
    get_latest_prediction,
    get_prediction_history,
    get_prediction_statistics
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
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.id != user_id:
        raise HTTPException(
            status_code=403,
            detail="Unauthorized"
        )

    latest = get_latest_prediction(
        db,
        user_id
    )

    if latest:

        return {
            "id": latest.id,
            "location": latest.predicted_location,
            "predicted_latitude": latest.predicted_latitude,
            "predicted_longitude": latest.predicted_longitude,
            "actual_latitude": latest.actual_latitude,
            "actual_longitude": latest.actual_longitude,
            "confidence": latest.confidence,
            "prediction_accuracy": latest.prediction_accuracy,
            "matched": latest.matched,
            "created_at": latest.created_at
        }

    return generate_prediction(
        db,
        user_id
    )


@router.get("/history/{user_id}")
def prediction_history(
    user_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.id != user_id:
        raise HTTPException(
            status_code=403,
            detail="Unauthorized"
        )

    history = get_prediction_history(
        db,
        user_id
    )

    return [
        {
            "id": item.id,
            "predicted_location": item.predicted_location,
            "predicted_latitude": item.predicted_latitude,
            "predicted_longitude": item.predicted_longitude,
            "actual_latitude": item.actual_latitude,
            "actual_longitude": item.actual_longitude,
            "confidence": item.confidence,
            "prediction_accuracy": item.prediction_accuracy,
            "matched": item.matched,
            "created_at": item.created_at
        }
        for item in history
    ]

@router.get("/statistics/{user_id}")
def prediction_statistics(
    user_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.id != user_id:
        raise HTTPException(
            status_code=403,
            detail="Unauthorized"
        )

    return get_prediction_statistics(
        db,
        user_id
    )