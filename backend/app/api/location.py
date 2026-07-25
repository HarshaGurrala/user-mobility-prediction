from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user
from app.models.user import User

from app.schemas.location import LocationCreate

from app.services.location_service import (
    add_location,
    get_current_location,
    get_location_history
)

from app.services.location_check_service import (
    check_safe_location
)


router = APIRouter(
    prefix="/location",
    tags=["Location"]
)


# Save new location (USER only)
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

    return add_location(
        db,
        user_id,
        location
    )


# Get latest location
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


# Get location history
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


# Check safe/unknown location
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
        location.longitude
    )

    return result