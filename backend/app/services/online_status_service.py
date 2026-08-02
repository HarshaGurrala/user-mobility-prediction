from datetime import datetime, timedelta

from sqlalchemy.orm import Session

from app.models.user import User



ONLINE_TIMEOUT_MINUTES = 2



def update_online_status(
    db: Session,
    user_id: int
):

    user = (
        db.query(User)
        .filter(
            User.id == user_id
        )
        .first()
    )


    if not user:
        return None



    user.last_seen = datetime.utcnow()

    user.is_online = True


    db.commit()

    db.refresh(user)


    return user





def check_user_online_status(
    db: Session,
    user_id: int
):

    user = (
        db.query(User)
        .filter(
            User.id == user_id
        )
        .first()
    )


    if not user:
        return None



    if user.last_seen is None:

        user.is_online = False



    else:

        difference = (
            datetime.utcnow()
            -
            user.last_seen
        )


        if difference > timedelta(
            minutes=ONLINE_TIMEOUT_MINUTES
        ):

            user.is_online = False


        else:

            user.is_online = True



    db.commit()

    db.refresh(user)


    return user