from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.services.safety_status_service import get_safety_status

router = APIRouter(
    prefix="/safety",
    tags=["Safety Status"]
)


@router.get("/status/{user_id}")
def safety_status(
    user_id: int,
    db: Session = Depends(get_db)
):
    return get_safety_status(
        db,
        user_id
    )