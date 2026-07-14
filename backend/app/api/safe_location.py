from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user
from app.schemas.safe_location import SafeLocationCreate
from app.services.safe_location_service import (
    add_safe_location,
    get_safe_locations,
    delete_safe_location
)

router = APIRouter(
    prefix="/safe-locations",
    tags=["Safe Locations"]
)


@router.post("/add")
def add_location(
    location: SafeLocationCreate,
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    location = add_safe_location(
        db,
        current_user,
        location
    )

    return {
        "message": "Safe location added successfully",
        "location_id": location.id
    }


@router.get("/all")
def get_locations(
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    return get_safe_locations(
        db,
        current_user
    )


@router.delete("/{location_id}")
def remove_location(
    location_id: int,
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    success = delete_safe_location(
        db,
        current_user,
        location_id
    )

    if not success:
        return {"message": "Location not found"}

    return {
        "message": "Location deleted successfully"
    }