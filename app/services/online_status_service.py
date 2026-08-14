from datetime import datetime, timedelta

from sqlalchemy.orm import Session

from app.models.user import User


ONLINE_TIMEOUT_MINUTES = 2



from datetime import datetime, timedelta

from sqlalchemy.orm import Session

from app.models.user import User


# User is considered online if a location update
# was received within the last 5 minutes.

ONLINE_TIMEOUT_SECONDS = 300


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
        return False

    if not user.last_seen:

        user.is_online = False

        db.commit()

        return False


    time_since_last_seen = (
        datetime.utcnow() -
        user.last_seen
    ).total_seconds()


    if time_since_last_seen <= ONLINE_TIMEOUT_SECONDS:

        user.is_online = True

    else:

        user.is_online = False


    db.commit()

    return user.is_online






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