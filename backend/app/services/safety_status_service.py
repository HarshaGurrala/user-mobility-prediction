from sqlalchemy.orm import Session

from app.models.user_safety_state import UserSafetyState
from app.models.safety_history import SafetyHistory


def get_safety_status(
    db: Session,
    user_id: int
):

    state = (
        db.query(UserSafetyState)
        .filter(
            UserSafetyState.user_id == user_id
        )
        .first()
    )

    if not state:
        return {
            "status": "UNKNOWN",
            "location_name": None,
            "message": "No safety status available",
            "last_event": None,
            "updated_at": None
        }

    last_event = (
        db.query(SafetyHistory)
        .filter(
            SafetyHistory.user_id == user_id
        )
        .order_by(
            SafetyHistory.timestamp.desc()
        )
        .first()
    )

    message = "Unknown Location"

    if state.status == "SAFE":
        message = f"At {state.location_name}"

    elif state.status == "WARNING":
        message = f"Left {state.location_name}"

    elif state.status == "UNKNOWN":
        message = "Unknown Location"

    return {
        "status": state.status,
        "location_name": state.location_name,
        "message": message,
        "last_event": last_event.event_type if last_event else None,
        "updated_at": state.updated_at
    }