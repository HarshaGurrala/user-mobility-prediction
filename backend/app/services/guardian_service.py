# from sqlalchemy.orm import Session

# from app.models.user import User
# from app.models.guardian_link import GuardianLink
# from app.models.location_history import LocationHistory


# def get_my_children(
#     db: Session,
#     current_user
# ):
#     """
#     Return all children linked to the logged-in guardian.
#     """

#     children = (
#         db.query(User)
#         .join(
#             GuardianLink,
#             GuardianLink.child_user_id == User.id
#         )
#         .filter(
#             GuardianLink.guardian_user_id == current_user.id
#         )
#         .all()
#     )

#     return children


# def get_child_latest_location(
#     db: Session,
#     child_id: int
# ):
#     """
#     Return latest location of a child.
#     """

#     return (
#         db.query(LocationHistory)
#         .filter(
#             LocationHistory.user_id == child_id
#         )
#         .order_by(
#             LocationHistory.created_at.desc()
#         )
#         .first()
#     )
from sqlalchemy.orm import Session

from app.models.user import User
from app.models.guardian_link import GuardianLink
from app.models.location_history import LocationHistory


def get_my_children(
    db: Session,
    current_user
):

    children = (
        db.query(User)
        .join(
            GuardianLink,
            GuardianLink.child_user_id == User.id
        )
        .filter(
            GuardianLink.guardian_user_id == current_user.id
        )
        .all()
    )

    result = []

    for child in children:

        latest_location = (
            db.query(LocationHistory)
            .filter(
                LocationHistory.user_id == child.id
            )
            .order_by(
                LocationHistory.created_at.desc()
            )
            .first()
        )

        result.append({

            "id": child.id,

            "full_name": child.full_name,

            "email": child.email,

            "phone_number": child.phone_number,

            "latest_location": latest_location

        })

    return result


def get_child_latest_location(
    db: Session,
    child_id: int
):

    return (
        db.query(LocationHistory)
        .filter(
            LocationHistory.user_id == child_id
        )
        .order_by(
            LocationHistory.created_at.desc()
        )
        .first()
    )