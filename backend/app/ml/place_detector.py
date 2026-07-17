from sqlalchemy.orm import Session
from sqlalchemy import func

from app.models.location_history import LocationHistory


def get_frequent_places(db: Session, user_id: int):

    places = (
        db.query(
            func.round(LocationHistory.latitude, 3).label("lat"),
            func.round(LocationHistory.longitude, 3).label("lng"),
            func.count().label("visits"),
        )
        .filter(LocationHistory.user_id == user_id)
        .group_by(
            func.round(LocationHistory.latitude, 3),
            func.round(LocationHistory.longitude, 3),
        )
        .order_by(func.count().desc())
        .limit(5)
        .all()
    )

    return places
# from sqlalchemy.orm import Session
# from sqlalchemy import func

# from app.models.location_history import LocationHistory


# def get_frequent_places(
#     db: Session,
#     user_id: int
# ):

#     places = (
#         db.query(
#             func.round(
#                 LocationHistory.latitude,
#                 3
#             ).label("latitude"),

#             func.round(
#                 LocationHistory.longitude,
#                 3
#             ).label("longitude"),

#             func.count(
#                 LocationHistory.id
#             ).label("visits")
#         )
#         .filter(
#             LocationHistory.user_id == user_id
#         )
#         .group_by(
#             func.round(
#                 LocationHistory.latitude,
#                 3
#             ),
#             func.round(
#                 LocationHistory.longitude,
#                 3
#             )
#         )
#         .order_by(
#             func.count(
#                 LocationHistory.id
#             ).desc()
#         )
#         .limit(5)
#         .all()
#     )


#     return places