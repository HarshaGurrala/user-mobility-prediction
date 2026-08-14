from sqlalchemy.orm import Session

from app.models.notification import Notification



from datetime import datetime, timedelta

from app.models.alert import Alert



def create_notification(
    db: Session,
    user_id: int,
    notification_type: str,
    title: str,
    message: str
):

    notification = Notification(
        user_id=user_id,
        notification_type=notification_type,
        title=title,
        message=message,
        status="unread"
    )

    db.add(notification)
    db.commit()
    db.refresh(notification)

    return notification


# def create_notification(
#     db: Session,
#     user_id: int,
#     notification_type: str,
#     title: str,
#     message: str,
#     alert_id: int = None
# ):

#     notification = Notification(
#         user_id=user_id,
#         alert_id=alert_id,
#         notification_type=notification_type,
#         title=title,
#         message=message,
#         status="unread"
#     )

#     db.add(notification)
#     db.commit()
#     db.refresh(notification)

#     return notification


def get_user_notifications(
    db: Session,
    user_id: int
):

    delete_expired_notifications(db)

    return (
        db.query(Notification)
        .filter(
            Notification.user_id == user_id
        )
        .order_by(
            Notification.created_at.desc()
        )
        .all()
    )


def mark_notification_read(
    db: Session,
    notification_id: int,
    user_id: int
):

    notification = (
        db.query(Notification)
        .filter(
            Notification.id == notification_id,
            Notification.user_id == user_id
        )
        .first()
)

    if not notification:
        return None

    notification.status = "read"

    alerts = (
        db.query(Alert)
        .filter(
            Alert.user_id == user_id,
            Alert.alert_type == notification.notification_type,
            Alert.is_read == False
        )
        .all()
    )

    for alert in alerts:
        alert.is_read = True
        alert.status = "read"

    db.commit()
    db.refresh(notification)

    return notification



# def delete_expired_notifications(
#     db: Session
# ):
#     expiry_time = datetime.utcnow() - timedelta(hours=24)

#     deleted_count = (
#         db.query(Notification)
#         .filter(
#             Notification.created_at < expiry_time
#         )
#         .delete(
#             synchronize_session=False
#         )
#     )

#     db.commit()

#     return deleted_count


def delete_expired_notifications(
    db: Session
):

    cutoff_time = datetime.now() - timedelta(
        hours=48
    )

    deleted_count = (
        db.query(Notification)
        .filter(
            Notification.created_at < cutoff_time
        )
        .delete(
            synchronize_session=False
        )
    )

    db.commit()

    return deleted_count