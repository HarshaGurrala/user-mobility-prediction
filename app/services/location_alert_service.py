from datetime import datetime

from sqlalchemy.orm import Session

from app.models.location_alert import LocationAlert
from app.models.user import User
from app.models.user_guardian_relationship import UserGuardianRelationship
from app.models.safe_location import SafeLocation

from app.utils.location_alert_email import (
    send_unknown_location_email
)


def create_location_alert(
    db: Session,
    user: User,
    latitude: float,
    longitude: float
):

    alert = LocationAlert(
        user_id=user.id,
        latitude=latitude,
        longitude=longitude,
        status="UNKNOWN"
    )

    db.add(alert)
    db.commit()
    db.refresh(alert)

    guardians = (
        db.query(User)
        .join(
            UserGuardianRelationship,
            UserGuardianRelationship.guardian_id == User.id
        )
        .filter(
            UserGuardianRelationship.user_id == user.id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .all()
    )

    sent = 0
    failed = 0

    for guardian in guardians:

        if not guardian.email:
            continue

        try:

            send_unknown_location_email(
                recipient_email=guardian.email,
                guardian_name=guardian.name,
                user_name=user.name,
                latitude=latitude,
                longitude=longitude,
                alert_id=alert.id
            )

            sent += 1

        except Exception as error:

            print(
                "LOCATION ALERT EMAIL ERROR:",
                error
            )

            failed += 1

    return {
        "alert": alert,
        "guardians_notified": sent,
        "guardians_failed": failed
    }


def respond_to_location_alert(
    db: Session,
    alert_id: int,
    guardian: User,
    response: str
):

    alert = (
        db.query(LocationAlert)
        .filter(
            LocationAlert.id == alert_id
        )
        .first()
    )

    if not alert:
        return None, "Location alert not found"

    relationship = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == guardian.id,
            UserGuardianRelationship.user_id == alert.user_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .first()
    )

    if not relationship:
        return None, "User is not connected with this guardian"

    response = response.upper()

    if response not in [
        "SAFE_ZONE",
        "UNSAFE",
        "INFORMATIONAL"
    ]:
        return None, "Invalid response"

    alert.guardian_response = response
    alert.responded_at = datetime.utcnow()

    if response == "SAFE_ZONE":

        safe_location = SafeLocation(
            user_id=alert.user_id,
            location_name="Guardian Approved Location",
            latitude=alert.latitude,
            longitude=alert.longitude,
            radius=100
        )

        db.add(safe_location)

        alert.status = "SAFE"

    elif response == "UNSAFE":

        alert.status = "UNSAFE"

    else:

        alert.status = "INFORMATIONAL"

    db.commit()
    db.refresh(alert)

    return alert, None