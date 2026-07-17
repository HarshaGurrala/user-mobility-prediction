from sqlalchemy.orm import Session

from app.models.alert import Alert


def get_all_alerts(
    db: Session,
    guardian_id: int
):

    return (
        db.query(Alert)
        .filter(
            Alert.guardian_id == guardian_id
        )
        .order_by(
            Alert.created_at.desc()
        )
        .all()
    )


def get_unread_alerts(
    db: Session,
    guardian_id: int
):

    return (
        db.query(Alert)
        .filter(
            Alert.guardian_id == guardian_id,
            Alert.is_read == False
        )
        .order_by(
            Alert.created_at.desc()
        )
        .all()
    )


def mark_alert_read(
    db: Session,
    guardian_id: int,
    alert_id: int
):

    alert = (
        db.query(Alert)
        .filter(
            Alert.id == alert_id,
            Alert.guardian_id == guardian_id
        )
        .first()
    )

    if alert is None:
        return {
            "message": "Alert not found"
        }

    alert.is_read = True

    db.commit()

    return {
        "message": "Alert marked as read"
    }


def delete_alert(
    db: Session,
    guardian_id: int,
    alert_id: int
):

    alert = (
        db.query(Alert)
        .filter(
            Alert.id == alert_id,
            Alert.guardian_id == guardian_id
        )
        .first()
    )

    if alert is None:
        return {
            "message": "Alert not found"
        }

    db.delete(alert)

    db.commit()

    return {
        "message": "Alert deleted"
    }