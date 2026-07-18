from sqlalchemy.orm import Session

from app.models.location import Location
from app.schemas.location import LocationCreate



def add_location(
    db: Session,
    user_id: int,
    location: LocationCreate
):

    new_location = Location(
        user_id=user_id,
        latitude=location.latitude,
        longitude=location.longitude,
        accuracy=location.accuracy
    )

    db.add(new_location)

    db.commit()

    db.refresh(new_location)

    return new_location



def get_current_location(
    db: Session,
    user_id: int
):

    return (
        db.query(Location)
        .filter(Location.user_id == user_id)
        .order_by(Location.timestamp.desc())
        .first()
    )



def get_location_history(
    db: Session,
    user_id: int
):

    return (
        db.query(Location)
        .filter(Location.user_id == user_id)
        .order_by(Location.timestamp.desc())
        .all()
    )