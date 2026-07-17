from sqlalchemy.orm import Session

from app.models.safe_zone import SafeZone


def create_safe_zone(
    db: Session,
    current_user,
    zone
):

    safe_zone = SafeZone(

        guardian_user_id=current_user.id,

        name=zone.name,

        latitude=zone.latitude,

        longitude=zone.longitude,

        radius=zone.radius

    )

    db.add(safe_zone)

    db.commit()

    db.refresh(safe_zone)

    return safe_zone


def get_safe_zones(
    db: Session,
    current_user
):

    return (

        db.query(SafeZone)

        .filter(
            SafeZone.guardian_user_id == current_user.id
        )

        .all()

    )