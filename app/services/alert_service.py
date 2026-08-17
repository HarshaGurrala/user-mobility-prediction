from sqlalchemy.orm import Session

from app.models.alert import Alert
from app.schemas.alert import AlertCreate


from app.models.notification import Notification
from app.models.user_guardian_relationship import UserGuardianRelationship
from app.models.user import User
from app.models.location import Location

from app.services.notification_service import (
    delete_expired_notifications
)

def create_alert(
    db: Session,
    alert_data: AlertCreate
):

    existing_alert = (
        db.query(Alert)
        .filter(
            Alert.user_id == alert_data.user_id,
            Alert.guardian_id == alert_data.guardian_id,
            Alert.alert_type == alert_data.alert_type,
            Alert.status == "unread"
        )
        .first()
    )

    if existing_alert:
        return existing_alert


    alert = Alert(
        user_id=alert_data.user_id,
        guardian_id=alert_data.guardian_id,
        alert_type=alert_data.alert_type,
        message=alert_data.message
    )


    db.add(alert)

    db.commit()

    db.refresh(alert)


    return alert



def get_user_alerts(
    db: Session,
    user_id: int
):

    return (
        db.query(Alert)
        .filter(
            Alert.user_id == user_id
        )
        .order_by(
            Alert.created_at.desc()
        )
        .all()
    )






def get_guardian_alerts(
    db: Session,
    guardian_id: int
):

    # Delete notifications older than 48 hours
    delete_expired_notifications(db)

    relations = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == guardian_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .all()
    )

    results = []

    for relation in relations:

        user = (
            db.query(User)
            .filter(
                User.id == relation.user_id
            )
            .first()
        )

        if not user:
            continue

        # ==========================================
        # ALL USER NOTIFICATIONS
        # ==========================================

        notifications = (
            db.query(Notification)
            .filter(
                Notification.user_id == user.id
            )
            .order_by(
                Notification.created_at.desc()
            )
            .all()
        )

        for notification in notifications:

            location = (
                db.query(Location)
                .filter(
                    Location.user_id == user.id
                )
                .order_by(
                    Location.timestamp.desc()
                )
                .first()
            )

            results.append({

                "id": notification.id,

                "user_id": user.id,

                "user": (
                    user.full_name
                    or user.email
                ),

               "type": (
    "SOS Alert"
    if notification.notification_type.upper() == "SOS"
    else "Unknown Location"
    if notification.notification_type.upper() == "UNKNOWN_LOCATION"
    else "Safe Location"
    if notification.notification_type.upper() in ["SAFE", "SAFE_LOCATION"]
    else notification.notification_type.replace("_", " ").title()
),

               "message": (
    f"{user.full_name or user.email} has left their registered safe zone."
    if notification.notification_type == "UNKNOWN_LOCATION"
    else notification.message
),

                "location": (
                    location.address
                    if location and location.address
                    else "Location unavailable"
                ),

                "time": (
                    notification.created_at.isoformat()
                    if notification.created_at
                    else None
                ),

                "status": notification.status

            })

    # ==========================================
    # NEWEST FIRST
    # ==========================================

    results.sort(
        key=lambda item: item["time"] or "",
        reverse=True
    )

    return results