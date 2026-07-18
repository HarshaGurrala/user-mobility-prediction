from sqlalchemy.orm import Session

from app.models.safe_location import SafeLocation
from app.schemas.safe_location import SafeLocationCreate


def create_safe_location(
    db: Session,
    user_id: int,
    location: SafeLocationCreate
):

    new_location = SafeLocation(
        user_id=user_id,
        location_name=location.location_name,
        latitude=location.latitude,
        longitude=location.longitude,
        radius=location.radius
    )

    db.add(new_location)

    db.commit()

    db.refresh(new_location)

    return new_location



def get_safe_locations(
    db: Session,
    user_id: int
):

    return db.query(
        SafeLocation
    ).filter(
        SafeLocation.user_id == user_id
    ).all()