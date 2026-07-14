from sqlalchemy.orm import Session

from app.models.location_history import LocationHistory
from app.schemas.location import LocationCreate


def save_location(
    db: Session,
    current_user,
    location: LocationCreate
):

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
        .filter(LocationHistory.user_id == current_user.id)
        .order_by(LocationHistory.created_at.desc())
        .all()
    )


def get_latest_location(
    db: Session,
    current_user
):

    return (
        db.query(LocationHistory)
        .filter(LocationHistory.user_id == current_user.id)
        .order_by(LocationHistory.created_at.desc())
        .first()
    )