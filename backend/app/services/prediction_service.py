from datetime import datetime

from sqlalchemy.orm import Session

from app.models.location import Location
from app.models.prediction import Prediction

from app.ml.train import train_location_model
from app.ml.predictor import predict_next_location


def get_location_history(
    db: Session,
    user_id: int
):

    return (
        db.query(Location)
        .filter(
            Location.user_id == user_id
        )
        .order_by(Location.timestamp.asc())
        .all()
    )


def train_user_model(
    db: Session,
    user_id: int
):

    history = get_location_history(
        db,
        user_id
    )

    if len(history) < 5:
        return False

    data = []

    for location in history:

        data.append({

            "latitude": location.latitude,

            "longitude": location.longitude,

            "timestamp": location.timestamp

        })

    train_location_model(data)

    return True


def generate_prediction(
    db: Session,
    user_id: int
):

    history = get_location_history(
        db,
        user_id
    )

    if len(history) < 5:

        return {
            "message": "Not enough location history for prediction."
        }

    trained = train_user_model(
        db,
        user_id
    )

    if not trained:

        return {
            "message": "Model training failed."
        }

    latest = history[-1]

    prediction = predict_next_location(

        latitude=latest.latitude,

        longitude=latest.longitude,

        hour=datetime.now().hour

    )

    prediction_record = Prediction(

        user_id=user_id,

        predicted_location=(
            f"{prediction['latitude']}, "
            f"{prediction['longitude']}"
        ),

        confidence=prediction["confidence"]

    )

    db.add(prediction_record)

    db.commit()

    db.refresh(prediction_record)

    return {

        "location": prediction_record.predicted_location,

        "latitude": prediction["latitude"],

        "longitude": prediction["longitude"],

        "confidence": prediction["confidence"]

    }


def get_latest_prediction(
    db: Session,
    user_id: int
):

    return (

        db.query(Prediction)

        .filter(
            Prediction.user_id == user_id
        )

        .order_by(
            Prediction.created_at.desc()
        )

        .first()

    )