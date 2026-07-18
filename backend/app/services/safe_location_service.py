from sqlalchemy.orm import Session

from app.models.safe_location import SafeLocation
from app.schemas.safe_location import (
    SafeLocationCreate,
    SafeLocationUpdate,
)


def create_safe_location(
    db: Session,
    user_id: int,
    location: SafeLocationCreate,
):
    new_location = SafeLocation(
        user_id=user_id,
        location_name=location.location_name,
        latitude=location.latitude,
        longitude=location.longitude,
        radius=location.radius,
    )

    db.add(new_location)
    db.commit()
    db.refresh(new_location)

    return new_location


def get_safe_locations(
    db: Session,
    user_id: int,
):
    return (
        db.query(SafeLocation)
        .filter(SafeLocation.user_id == user_id)
        .all()
    )


def get_safe_location_by_id(
    db: Session,
    location_id: int,
):
    return (
        db.query(SafeLocation)
        .filter(SafeLocation.id == location_id)
        .first()
    )


def update_safe_location(
    db: Session,
    location_id: int,
    location: SafeLocationUpdate,
):
    safe_location = get_safe_location_by_id(
        db,
        location_id,
    )

    if not safe_location:
        return None

    update_data = location.model_dump(exclude_unset=True)

    for key, value in update_data.items():
        setattr(safe_location, key, value)

    db.commit()
    db.refresh(safe_location)

    return safe_location


def delete_safe_location(
    db: Session,
    location_id: int,
):
    safe_location = get_safe_location_by_id(
        db,
        location_id,
    )

    if not safe_location:
        return False

    db.delete(safe_location)
    db.commit()

    return True