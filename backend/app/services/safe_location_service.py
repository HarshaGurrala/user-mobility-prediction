from sqlalchemy.orm import Session

from app.models.safe_location import SafeLocation
from app.schemas.safe_location import SafeLocationCreate


def add_safe_location(
    db: Session,
    current_user,
    location: SafeLocationCreate
):

    new_location = SafeLocation(
        user_id=current_user.id,
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
    current_user
):

    return (
        db.query(SafeLocation)
        .filter(SafeLocation.user_id == current_user.id)
        .all()
    )


def delete_safe_location(
    db: Session,
    current_user,
    location_id: int
):

    location = (
        db.query(SafeLocation)
        .filter(
            SafeLocation.id == location_id,
            SafeLocation.user_id == current_user.id
        )
        .first()
    )

    if location is None:
        return False

    db.delete(location)
    db.commit()

    return True