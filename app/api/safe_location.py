from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database.database import get_db

from app.schemas.safe_location import SafeLocationCreate

from app.schemas.safe_location import (
    SafeLocationCreate,
    SafeLocationUpdate,
)

from app.services.safe_location_service import (
    create_safe_location,
    get_safe_locations,
    get_safe_location_by_id,
    update_safe_location,
    delete_safe_location,
)

router = APIRouter(
    prefix="/safe-location",
    tags=["Safe Locations"]
)


@router.post("/{user_id}")
def add_safe_location(
    user_id: int,
    location: SafeLocationCreate,
    db: Session = Depends(get_db)
):

    return create_safe_location(
        db,
        user_id,
        location
    )



@router.get("/{user_id}")
def list_safe_locations(
    user_id: int,
    db: Session = Depends(get_db)
):

    return get_safe_locations(
        db,
        user_id
    )

@router.get("/detail/{location_id}")
def get_safe_location_detail(
    location_id: int,
    db: Session = Depends(get_db)
):
    return get_safe_location_by_id(
        db,
        location_id
    )


@router.put("/{location_id}")
def edit_safe_location(
    location_id: int,
    location: SafeLocationUpdate,
    db: Session = Depends(get_db)
):
    return update_safe_location(
        db,
        location_id,
        location
    )


@router.delete("/{location_id}")
def remove_safe_location(
    location_id: int,
    db: Session = Depends(get_db)
):
    return delete_safe_location(
        db,
        location_id
    )