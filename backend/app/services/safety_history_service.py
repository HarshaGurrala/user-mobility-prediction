from sqlalchemy.orm import Session

from app.models.safety_history import SafetyHistory
from app.schemas.safety_history import SafetyHistoryCreate



def create_safety_history(
    db: Session,
    history_data: SafetyHistoryCreate
):

    history = SafetyHistory(
        user_id=history_data.user_id,
        safe_location_id=history_data.safe_location_id,
        event_type=history_data.event_type,
        location_name=history_data.location_name,
        latitude=history_data.latitude,
        longitude=history_data.longitude,
        address=history_data.address,
    )

    db.add(history)

    db.commit()

    db.refresh(history)

    return history



def get_safety_history(
    db: Session,
    user_id: int
):

    return (
        db.query(SafetyHistory)
        .filter(
            SafetyHistory.user_id == user_id
        )
        .order_by(
            SafetyHistory.timestamp.desc()
        )
        .all()
    )