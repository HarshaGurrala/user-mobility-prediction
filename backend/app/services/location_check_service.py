from sqlalchemy.orm import Session

from app.models.safe_location import SafeLocation
from app.models.user_guardian_relationship import UserGuardianRelationship
from app.models.alert import Alert

from app.schemas.alert import AlertCreate

from app.services.alert_service import create_alert

from app.utils.location_utils import calculate_distance


def check_safe_location(
    db: Session,
    user_id: int,
    latitude: float,
    longitude: float
):

    safe_locations = (
        db.query(SafeLocation)
        .filter(
            SafeLocation.user_id == user_id
        )
        .all()
    )

    # Check if inside any safe location
    for location in safe_locations:

        distance = calculate_distance(
            latitude,
            longitude,
            location.latitude,
            location.longitude
        )

        if distance <= location.radius:

            return {
                "status": "SAFE",
                "location": location.location_name,
                "distance": round(distance, 2),
                "latitude": latitude,
                "longitude": longitude,
                "alert_created": False
            }

    # User is outside all safe locations

    guardians = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.user_id == user_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .all()
    )

    alert_created = False

    for guardian in guardians:

        # Prevent duplicate UNKNOWN_LOCATION alerts
        existing_alert = (
            db.query(Alert)
            .filter(
                Alert.user_id == user_id,
                Alert.guardian_id == guardian.guardian_id,
                Alert.alert_type == "UNKNOWN_LOCATION",
                Alert.is_read == False
            )
            .first()
        )

        if existing_alert:
            continue

        alert = AlertCreate(
            user_id=user_id,
            guardian_id=guardian.guardian_id,
            alert_type="UNKNOWN_LOCATION",
            message="User entered an unknown location."
        )

        create_alert(
            db,
            alert
        )

        alert_created = True

    return {
        "status": "UNKNOWN",
        "location": None,
        "distance": None,
        "latitude": latitude,
        "longitude": longitude,
        "alert_created": alert_created
    }