from datetime import datetime

from sqlalchemy.orm import Session

from app.models.safe_location import SafeLocation
from app.models.user_guardian_relationship import UserGuardianRelationship
from app.models.alert import Alert
from app.models.user_safety_state import UserSafetyState
from app.models.notification import Notification

from app.schemas.alert import AlertCreate
from app.schemas.safety_history import SafetyHistoryCreate

from app.services.alert_service import create_alert
from app.services.safety_history_service import create_safety_history
from app.services.notification_service import create_notification

from app.utils.location_utils import calculate_distance

from app.models.emergency_contact import EmergencyContact
from app.models.user import User

from app.utils.sos_email import send_unknown_location_email


def get_user_state(
    db: Session,
    user_id: int
):

    return (
        db.query(UserSafetyState)
        .filter(
            UserSafetyState.user_id == user_id
        )
        .first()
    )


def update_user_state(
    db: Session,
    user_id: int,
    status: str,
    safe_location_id=None,
    location_name=None
):

    state = get_user_state(
        db,
        user_id
    )

    if state:

        state.status = status
        state.safe_location_id = safe_location_id
        state.location_name = location_name

    else:

        state = UserSafetyState(
            user_id=user_id,
            status=status,
            safe_location_id=safe_location_id,
            location_name=location_name
        )

        db.add(state)

    db.commit()

    db.refresh(state)

    return state


def create_history(
    db: Session,
    user_id: int,
    event_type: str,
    latitude: float,
    longitude: float,
    address: str,
    safe_location_id=None,
    location_name=None
):

    history = SafetyHistoryCreate(

        user_id=user_id,

        safe_location_id=safe_location_id,

        event_type=event_type,

        location_name=location_name,

        latitude=latitude,

        longitude=longitude,

        address=address
    )

    return create_safety_history(
        db,
        history
    )


# ==========================================================
# CREATE UNKNOWN LOCATION ALERT
# ==========================================================

def create_unknown_alert(
    db: Session,
    user_id: int
):

    guardians = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.user_id == user_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .all()
    )

    alert_created = False

    # ======================================================
    # USER APP NOTIFICATION
    # ======================================================

    existing_notification = (
        db.query(Notification)
        .filter(
            Notification.user_id == user_id,
            Notification.notification_type == "UNKNOWN_LOCATION",
            Notification.status == "unread"
        )
        .first()
    )

    if not existing_notification:

        create_notification(
            db,
            user_id,
            "UNKNOWN_LOCATION",
            "⚠️ Unknown Location Detected",
            "You have entered an unknown location. Your guardian has been notified."
        )

    # ======================================================
    # GUARDIAN ALERTS
    # ======================================================

    for guardian in guardians:

        existing_alert = (
            db.query(Alert)
            .filter(
                Alert.user_id == user_id,
                Alert.guardian_id == guardian.guardian_id,
                Alert.alert_type == "UNKNOWN_LOCATION",
                Alert.status == "unread"
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

    return alert_created


# ==========================================================
# SEND UNKNOWN LOCATION EMAILS
# ==========================================================

def send_unknown_location_emails(
    db: Session,
    user_id: int,
    latitude: float,
    longitude: float,
    address: str = None
):

    user = (
        db.query(User)
        .filter(
            User.id == user_id
        )
        .first()
    )

    if not user:
        return

    user_name = (
        user.full_name
        or user.email
    )

    contacts = (
        db.query(EmergencyContact)
        .filter(
            EmergencyContact.user_id == user_id
        )
        .all()
    )

    event_time = datetime.now().strftime(
        "%Y-%m-%d %H:%M:%S"
    )

    for contact in contacts:

        if not contact.email:
            continue

        try:

            send_unknown_location_email(
                recipient_email=contact.email,
                user_name=user_name,
                latitude=latitude,
                longitude=longitude,
                location_address=(
                    address
                    if address
                    else "Unknown Location"
                ),
                event_time=event_time
            )

        except Exception as e:

            print(
                f"Unknown location email failed "
                f"for {contact.email}: {e}"
            )


# ==========================================================
# CHECK SAFE LOCATION
# ==========================================================

def check_safe_location(
    db: Session,
    user_id: int,
    latitude: float,
    longitude: float,
    address: str = None
):

    safe_locations = (
        db.query(SafeLocation)
        .filter(
            SafeLocation.user_id == user_id
        )
        .all()
    )

    current_state = get_user_state(
        db,
        user_id
    )

    current_status = "UNKNOWN"
    matched_location = None
    distance_value = None

    # ======================================================
    # CHECK SAFE ZONES
    # ======================================================

    for location in safe_locations:

        distance = calculate_distance(
            latitude,
            longitude,
            location.latitude,
            location.longitude
        )

        if distance <= location.radius:

            current_status = "SAFE"

            matched_location = location

            distance_value = distance

            break

    # ======================================================
    # FIRST TIME USER STATE
    # ======================================================

    if current_state is None:

        if current_status == "SAFE":

            create_history(
                db,
                user_id,
                "ENTERED_SAFE_ZONE",
                latitude,
                longitude,
                address,
                matched_location.id,
                matched_location.location_name
            )

        else:

            create_history(
                db,
                user_id,
                "UNKNOWN_LOCATION",
                latitude,
                longitude,
                address
            )

            create_unknown_alert(
                db,
                user_id
            )

            print(
                "CALLING CREATE UNKNOWN ALERT:",
                user_id
            )

            send_unknown_location_emails(
                db=db,
                user_id=user_id,
                latitude=latitude,
                longitude=longitude,
                address=address
            )

        update_user_state(
            db,
            user_id,
            current_status,
            matched_location.id
            if matched_location
            else None,
            matched_location.location_name
            if matched_location
            else None
        )

    # ======================================================
    # EXISTING USER STATE
    # ======================================================

    else:

        previous_status = current_state.status

        previous_location = current_state.safe_location_id

        # ==================================================
        # ENTER SAFE ZONE
        # ==================================================

        if (
            current_status == "SAFE"
            and previous_status != "SAFE"
        ):

            create_history(
                db,
                user_id,
                "ENTERED_SAFE_ZONE",
                latitude,
                longitude,
                address,
                matched_location.id,
                matched_location.location_name
            )

        # ==================================================
        # LEFT SAFE ZONE
        # ==================================================

        elif (
            previous_status == "SAFE"
            and current_status != "SAFE"
        ):

            create_history(
                db,
                user_id,
                "LEFT_SAFE_ZONE",
                latitude,
                longitude,
                address,
                previous_location,
                current_state.location_name
            )

            # ==============================================
            # UNKNOWN LOCATION
            # ==============================================

            if current_status == "UNKNOWN":

                create_history(
                    db,
                    user_id,
                    "UNKNOWN_LOCATION",
                    latitude,
                    longitude,
                    address
                )

                create_unknown_alert(
                    db,
                    user_id
                )

                send_unknown_location_emails(
                    db=db,
                    user_id=user_id,
                    latitude=latitude,
                    longitude=longitude,
                    address=address
                )

        update_user_state(
            db,
            user_id,
            current_status,
            matched_location.id
            if matched_location
            else None,
            matched_location.location_name
            if matched_location
            else None
        )

    # ======================================================
    # RESPONSE
    # ======================================================

    return {

        "status": current_status,

        "location":
            matched_location.location_name
            if matched_location
            else "Unknown Location",

        "distance":
            round(distance_value, 2)
            if distance_value is not None
            else None,

        "latitude": latitude,

        "longitude": longitude
    }