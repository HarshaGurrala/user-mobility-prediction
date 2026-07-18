from sqlalchemy.orm import Session

from app.models.alert import Alert
from app.schemas.alert import AlertCreate



def create_alert(
    db: Session,
    alert_data: AlertCreate
):

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