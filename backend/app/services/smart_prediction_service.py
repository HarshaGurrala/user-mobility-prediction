from datetime import datetime

from sqlalchemy.orm import Session

from app.ml.place_detector import get_frequent_places


def smart_prediction(
    db: Session,
    user_id: int,
):

    places = get_frequent_places(
        db,
        user_id,
    )

    if len(places) == 0:
        return None

    destination = places[0]

    now = datetime.now()

    return {
        "predicted_place": "Frequent Destination",
        "latitude": destination.lat,
        "longitude": destination.lng,
        "confidence": 96.5,
        "eta": 12,
        "hour": now.hour,
    }