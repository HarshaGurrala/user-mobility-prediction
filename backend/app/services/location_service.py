from sqlalchemy.orm import Session

from app.models.location_history import LocationHistory
from app.schemas.location import LocationCreate


def save_location(
    db: Session,
    current_user,
    location: LocationCreate
):

    if location.accuracy > 50:
        return None

    new_location = LocationHistory(
        user_id=current_user.id,
        latitude=location.latitude,
        longitude=location.longitude,
        speed=location.speed,
        accuracy=location.accuracy
    )

    db.add(new_location)
    db.commit()
    db.refresh(new_location)

    return new_location


def get_location_history(
    db: Session,
    current_user
):

    return (
        db.query(LocationHistory)
        .filter(
            LocationHistory.user_id == current_user.id
        )
        .order_by(
            LocationHistory.created_at.desc()
        )
        .all()
    )


def get_latest_location(
    db: Session,
    user
):

    location = (
        db.query(LocationHistory)
        .filter(
            LocationHistory.user_id == user.id
        )
        .order_by(
            LocationHistory.created_at.desc()
        )
        .first()
    )

    if location is None:

        return {
            "child": user.full_name,
            "latitude": 16.5062,
            "longitude": 80.6480,
            "speed": 0,
            "accuracy": 20,
            "battery": 80,
        }

    return {
        "child": user.full_name,
        "latitude": location.latitude,
        "longitude": location.longitude,
        "speed": location.speed,
        "accuracy": location.accuracy,
        "battery": 80,
    }