from datetime import datetime

from sqlalchemy.orm import Session

from app.models.prediction import Prediction
from app.ml.place_detector import get_frequent_places


def generate_prediction(
    db: Session,
    user_id: int
):

    places = get_frequent_places(
        db,
        user_id
    )


    if not places:

        prediction = Prediction(

            user_id=user_id,

            predicted_place="Unknown",

            confidence=0,

            eta=0,

            latitude=0,

            longitude=0

        )

    else:

        destination = places[0]


        confidence = min(
            destination.visits * 10,
            99
        )


        prediction = Prediction(

            user_id=user_id,

            predicted_place="Frequent Destination",

            confidence=confidence,

            eta=15,

            latitude=float(
                destination.lat
            ),

            longitude=float(
                destination.lng
            )

        )


    db.add(prediction)

    db.commit()

    db.refresh(prediction)


    return prediction