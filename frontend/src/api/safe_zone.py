from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user

from app.schemas.safe_zone import SafeZoneCreate

from app.services.safe_zone_service import (
    create_safe_zone,
    get_safe_zones,
)

router = APIRouter(
    prefix="/safe-zone",
    tags=["Safe Zone"]
)


@router.post("/")
def create(
    zone: SafeZoneCreate,
    db: Session = Depends(get_db),
    current_user = Depends(get_current_user)
):

    return create_safe_zone(
        db,
        current_user,
        zone
    )


@router.get("/")
def list_zones(
    db: Session = Depends(get_db),
    current_user = Depends(get_current_user)
):

    return get_safe_zones(
        db,
        current_user
    )