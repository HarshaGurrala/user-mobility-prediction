from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from app.services.prediction_service import calculate_prediction_eta
from app.database.database import get_db

from app.dependencies.auth import get_current_user
from app.models.user import User
from app.models.location import Location

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

    if current_user.id != user_id and current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Unauthorized"
        )

    latest_prediction = get_latest_prediction(
        db,
        user_id
    )

    if latest_prediction:

        eta_minutes = calculate_prediction_eta(
            db=db,
            user_id=user_id,
            predicted_latitude=latest_prediction.predicted_latitude,
            predicted_longitude=latest_prediction.predicted_longitude
        )

        return {
            "id": latest_prediction.id,

            "location":
                latest_prediction.predicted_location,

            "predicted_latitude":
                latest_prediction.predicted_latitude,

            "predicted_longitude":
                latest_prediction.predicted_longitude,

            "actual_latitude":
                latest_prediction.actual_latitude,

            "actual_longitude":
                latest_prediction.actual_longitude,

            "confidence":
                latest_prediction.confidence,

            "prediction_accuracy":
                latest_prediction.prediction_accuracy,

            "matched":
                latest_prediction.matched,

            "eta":
                f"{eta_minutes} min"
                if eta_minutes is not None
                else None,

            "created_at":
                latest_prediction.created_at
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