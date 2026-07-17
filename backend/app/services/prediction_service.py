from sqlalchemy.orm import Session

from app.models.prediction import Prediction


def get_latest_prediction(
    db: Session,
    user_id: int
):

    prediction = (
        db.query(Prediction)
        .filter(
            Prediction.user_id == user_id
        )
        .order_by(
            Prediction.created_at.desc()
        )
        .first()
    )

    return prediction