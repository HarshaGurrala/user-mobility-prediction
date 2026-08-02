from sqlalchemy.orm import Session

from datetime import datetime, timedelta

from app.models.location import Location
from app.models.alert import Alert
from app.models.safe_location import SafeLocation

from app.utils.location_utils import calculate_distance



def get_safety_status(
    db: Session,
    user_id: int
):

    location = (
        db.query(Location)
        .filter(
            Location.user_id == user_id
        )
        .order_by(
            Location.timestamp.desc()
        )
        .first()
    )

    if not location:

        return {
            "status": "UNKNOWN",
            "message": "No location data",
            "location_name": None
        }

    safe_locations = (
        db.query(SafeLocation)
        .filter(
            SafeLocation.user_id == user_id
        )
        .all()
    )

    for safe in safe_locations:

        distance = calculate_distance(
            location.latitude,
            location.longitude,
            safe.latitude,
            safe.longitude
        )

        if distance <= safe.radius:

            return {
                "status": "SAFE",
                "message": f"User is at {safe.location_name}",
                "location_name": safe.location_name
            }

    recent_alert = (
        db.query(Alert)
        .filter(
            Alert.user_id == user_id
        )
        .filter(
            Alert.created_at >= datetime.now() - timedelta(minutes=30)
        )
        .first()
    )

    if recent_alert:

        return {
            "status": "DANGER",
            "message": recent_alert.message,
            "location_name": None
        }

    return {
        "status": "WARNING",
        "message": "User is outside safe locations",
        "location_name": None
    }