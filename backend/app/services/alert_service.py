from sqlalchemy.orm import Session

from app.models.alert import Alert
from app.models.guardian_link import GuardianLink

from app.services.geofence_service import is_inside_safe_zone


def create_unknown_location_alert(
    db: Session,
    child,
    latitude,
    longitude
):

    links = (
        db.query(GuardianLink)
        .filter(
            GuardianLink.child_user_id == child.id
        )
        .all()
    )

    for link in links:

        alert = Alert(

            guardian_id=link.guardian_user_id,

            child_id=child.id,

            title="Unknown Location",

            message=f"{child.full_name} entered an unknown location.",

            alert_type="UNKNOWN_LOCATION",

            latitude=latitude,

            longitude=longitude

        )

        db.add(alert)

    db.commit()


def create_safe_zone_exit_alert(
    db: Session,
    child,
    latitude,
    longitude,
    safe_zone
):

    if is_inside_safe_zone(
        latitude,
        longitude,
        safe_zone.latitude,
        safe_zone.longitude,
        safe_zone.radius
    ):
        return

    links = (
        db.query(GuardianLink)
        .filter(
            GuardianLink.child_user_id == child.id
        )
        .all()
    )

    for link in links:

        alert = Alert(

            guardian_id=link.guardian_user_id,

            child_id=child.id,

            title="Safe Zone Exit",

            message=f"{child.full_name} left {safe_zone.name}.",

            alert_type="SAFE_ZONE_EXIT",

            latitude=latitude,

            longitude=longitude

        )

        db.add(alert)

    db.commit()