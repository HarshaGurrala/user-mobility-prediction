from sqlalchemy.orm import Session

from datetime import datetime

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
        .order_by(
            Location.timestamp
        )
        .all()
    )



def save_prediction(
    db: Session,
    user_id: int,
    location: str,
    confidence: float
):

    prediction = Prediction(

        user_id=user_id,

        predicted_location=location,

        confidence=confidence

    )


    db.add(prediction)

    db.commit()

    db.refresh(prediction)

    return prediction