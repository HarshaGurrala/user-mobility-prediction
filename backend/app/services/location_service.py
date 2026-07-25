from sqlalchemy.orm import Session

from app.models.location import Location
from app.schemas.location import LocationCreate
from app.services.geocoding_service import (
    get_location_name
)

from app.services.location_check_service import (
    check_safe_location,
)
from app.services.prediction_service import update_prediction_result

def add_location(
    db: Session,
    user_id: int,
    location: LocationCreate
):

    address = get_location_name(
    location.latitude,
    location.longitude
)

    new_location = Location(
    user_id=user_id,
    latitude=location.latitude,
    longitude=location.longitude,
    accuracy=location.accuracy,
    address=address
)

    db.add(new_location)

    db.commit()

    db.refresh(new_location)

    update_prediction_result(
    db=db,
    user_id=user_id,
    latitude=location.latitude,
    longitude=location.longitude
)

    # Automatically check whether the location
    # is inside a safe zone after saving it.
    safety_status = check_safe_location(
        db=db,
        user_id=user_id,
        latitude=location.latitude,
        longitude=location.longitude
    )

    return {
        "location": new_location,
        "safety": safety_status
    }


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