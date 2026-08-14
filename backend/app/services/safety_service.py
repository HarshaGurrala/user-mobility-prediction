from sqlalchemy.orm import Session

from datetime import datetime, timedelta

from app.models.location import Location
from app.models.alert import Alert
from app.models.safe_location import SafeLocation

from app.utils.location_utils import calculate_distance

# from app.services.email_service import send_unknown_location_email
# from app.models.emergency_contact import EmergencyContact
# from app.models.user import User


# def send_unknown_location_emails(
#     db: Session,
#     user_id: int,
#     user_name: str,
#     latitude: float,
#     longitude: float,
#     address: str
# ):

#     contacts = (
#         db.query(EmergencyContact)
#         .filter(
#             EmergencyContact.user_id == user_id
#         )
#         .all()
#     )

#     event_time = datetime.now().strftime(
#         "%Y-%m-%d %H:%M:%S"
#     )

#     for contact in contacts:

#         if not contact.email:
#             continue

#         try:

#             send_unknown_location_email(
#                 recipient_email=contact.email,
#                 user_name=user_name,
#                 latitude=latitude,
#                 longitude=longitude,
#                 location_address=(
#                     address
#                     if address
#                     else "Unknown Location"
#                 ),
#                 event_time=event_time
#             )

#         except Exception as e:

#             print(
#                 f"Unknown location email failed "
#                 f"for {contact.email}: {e}"
#             )

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