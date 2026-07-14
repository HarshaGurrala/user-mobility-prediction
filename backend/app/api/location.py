from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user
from app.schemas.location import LocationCreate
from app.services.location_service import (
    save_location,
    get_location_history,
    get_latest_location,
)

router = APIRouter(
    prefix="/location",
    tags=["Live Location"]
)


@router.post("/update")
def update_location(
    location: LocationCreate,
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    location = save_location(
        db,
        current_user,
        location
    )

    return {
        "message": "Location updated successfully",
        "location_id": location.id
    }


@router.get("/history")
def history(
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    return get_location_history(
        db,
        current_user
    )


@router.get("/latest")
def latest(
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    return get_latest_location(
        db,
        current_user
    )