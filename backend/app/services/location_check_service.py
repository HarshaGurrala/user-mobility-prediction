from sqlalchemy.orm import Session

from app.models.safe_location import SafeLocation
from app.models.user_guardian_relationship import UserGuardianRelationship

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
                "distance": distance
            }



    # Unknown location detected

    guardians = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.user_id == user_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .all()
    )


    for guardian in guardians:

        alert = AlertCreate(

            user_id=user_id,

            guardian_id=guardian.guardian_id,

            alert_type="UNKNOWN_LOCATION",

            message="User entered an unknown location"

        )


        create_alert(
            db,
            alert
        )


    return {
        "status": "UNKNOWN",
        "location": None,
        "alert_created": True
    }