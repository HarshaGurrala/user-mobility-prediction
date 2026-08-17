from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from datetime import datetime

from app.database.database import get_db
from app.dependencies.auth import get_current_user
from app.models.user import User
from app.schemas.location import LocationCreate

from app.services.location_service import (
    add_location,
    get_current_location,
    get_location_history
)

from app.services.location_alert_service import (
    create_location_alert
)

from app.services.location_check_service import (
    check_safe_location
)

from app.services.online_status_service import update_online_status


router = APIRouter(
    prefix="/location",
    tags=["Location"]
)


# ==========================================================
# Save new location (USER only)
# ==========================================================

# @router.post("/update/{user_id}")
# def update_location(
#     user_id: int,
#     location: LocationCreate,
#     db: Session = Depends(get_db),
#     current_user: User = Depends(get_current_user)
# ):

#     if current_user.id != user_id:
#         raise HTTPException(
#             status_code=403,
#             detail="Cannot update another user's location"
#         )


#     # Save location history
#     result = add_location(
#         db,
#         user_id,
#         location
#     )


#     # Update user online status
#     user = (
#         db.query(User)
#         .filter(
#             User.id == user_id
#         )
#         .first()
#     )


#     if user:

#         user.last_seen = datetime.utcnow()

#         user.is_online = True

#         db.commit()


#     return result

#     # Update user online status

#     user = (
#         db.query(User)
#         .filter(
#             User.id == user_id
#         )
#         .first()
#     )


#     if user:

#         user.last_seen = datetime.utcnow()

#         user.is_online = True

#         db.commit()


#     return {
#         "message": "Location updated successfully",
#         "location": result,
#         "is_online": True,
#         "last_seen": user.last_seen if user else None
#     }





# ==========================================================
# Get latest location
# ==========================================================

@router.get("/current/{user_id}")
def current_location(
    user_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    return get_current_location(
        db,
        user_id
    )





# ==========================================================
# Get location history
# ==========================================================

@router.get("/history/{user_id}")
def location_history(
    user_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    return get_location_history(
        db,
        user_id
    )





# ==========================================================
# Check safe/unknown location
# ==========================================================

@router.post("/check/{user_id}")
def check_location(
    user_id: int,
    location: LocationCreate,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    result = check_safe_location(
        db,
        user_id,
        location.latitude,
        location.longitude,
        location.address
    )

    return result


@router.post("/update/{user_id}")
def update_location(
    user_id: int,
    location: LocationCreate,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.id != user_id:

        raise HTTPException(
            status_code=403,
            detail="Cannot update another user's location"
        )

    # --------------------------------------------------
    # Save location history
    # --------------------------------------------------

    result = add_location(
        db,
        user_id,
        location
    )

    # --------------------------------------------------
    # Update user online status
    # --------------------------------------------------

    user = (
        db.query(User)
        .filter(
            User.id == user_id
        )
        .first()
    )

    if user:

        user.last_seen = datetime.utcnow()

        user.is_online = True

        db.commit()

    # --------------------------------------------------
    # Check Safe Zone
    # --------------------------------------------------

    location_status = check_safe_location(
        db,
        user_id,
        location.latitude,
        location.longitude
    )

    # --------------------------------------------------
    # Unknown location
    # --------------------------------------------------

    alert_created = False

    if location_status.get("status") == "UNKNOWN":

        create_location_alert(
            db=db,
            user=user,
            latitude=location.latitude,
            longitude=location.longitude
        )

        alert_created = True

    # --------------------------------------------------
    # Response
    # --------------------------------------------------

    return {
        "message": "Location updated successfully",

        "location": result,

        "is_online": True,

        "last_seen":
            user.last_seen if user else None,

        "location_status":
            location_status,

        "alert_created":
            alert_created
    }