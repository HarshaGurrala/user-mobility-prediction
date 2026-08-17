from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.services.safety_history_service import (
    get_safety_history
)


router = APIRouter(
    prefix="/safety",
    tags=["Safety History"]
)


@router.get("/history/{user_id}")
def safety_history(
    user_id: int,
    db: Session = Depends(get_db)
):

    return get_safety_history(
        db,
        user_id
    )