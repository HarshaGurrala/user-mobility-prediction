from sqlalchemy.orm import Session

from app.models.guardian_link import GuardianLink
from app.models.safe_zone import SafeZone
from app.models.alert import Alert

from app.services.geofence_service import is_inside_safe_zone


def check_location_alerts(
    db: Session,
    child,
    latitude,
    longitude
):

    guardian_link = (
        db.query(GuardianLink)
        .filter(
            GuardianLink.child_user_id == child.id
        )
        .first()
    )

    if guardian_link is None:
        return

    guardian_id = guardian_link.guardian_user_id

    safe_zones = (
        db.query(SafeZone)
        .filter(
            SafeZone.guardian_user_id == guardian_id
        )
        .all()
    )

    inside_any = False

    for zone in safe_zones:

        if is_inside_safe_zone(
            latitude,
            longitude,
            zone
        ):
            inside_any = True
            break

    if not inside_any:

        alert = Alert(

            guardian_id=guardian_id,

            child_id=child.id,

            title="Safe Zone Exit",

            message=f"{child.full_name} has left all safe zones.",

            alert_type="SAFE_ZONE_EXIT",

            latitude=latitude,

            longitude=longitude,

            is_read=False

        )

        db.add(alert)
        db.commit()