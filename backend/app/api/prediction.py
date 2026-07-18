from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db

from app.models.location import Location

from app.services.geocoding_service import get_location_name

from app.services.prediction_service import (
    save_prediction
)

from app.ml.train import train_location_model
from app.ml.predictor import predict_next_location


router = APIRouter(
    prefix="/prediction",
    tags=["Prediction"]
)


# Train ML Model
@router.post("/train/{user_id}")
def train(
    user_id: int,
    db: Session = Depends(get_db)
):

    locations = (
        db.query(Location)
        .filter(
            Location.user_id == user_id
        )
        .order_by(
            Location.timestamp
        )
        .all()
    )


    if len(locations) < 5:

        raise HTTPException(
            status_code=400,
            detail="Not enough location data for training"
        )


    data = []

    for loc in locations:

        data.append(
            {
                "latitude": loc.latitude,
                "longitude": loc.longitude,
                "timestamp": loc.timestamp
            }
        )


    train_location_model(
        data
    )


    return {
        "message": "Model trained successfully",
        "records_used": len(data)
    }



# Predict next location
@router.get("/next/{user_id}")
def predict(
    user_id: int,
    db: Session = Depends(get_db)
):

    last_location = (
        db.query(Location)
        .filter(
            Location.user_id == user_id
        )
        .order_by(
            Location.timestamp.desc()
        )
        .first()
    )


    if not last_location:

        raise HTTPException(
            status_code=404,
            detail="No location history found"
        )


    result = predict_next_location(
        last_location.latitude,
        last_location.longitude,
        last_location.timestamp.hour
    )


    # Convert coordinates to real place name
    location_name = get_location_name(
        result["latitude"],
        result["longitude"]
    )


    prediction = save_prediction(
        db,
        user_id,
        location_name,
        result["confidence"]
    )


    return {

        "predicted_location": location_name,

        "latitude": result["latitude"],

        "longitude": result["longitude"],

        "confidence": 80.0,

        "prediction_id": prediction.id

    }