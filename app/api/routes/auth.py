from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user
from app.models.user import User

from app.services.dashboard_service import (
    get_guardian_dashboard
)


router = APIRouter(
    prefix="/dashboard",
    tags=["Guardian Dashboard"]
)


@router.get("/me")
def dashboard(
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):
    # Only GUARDIAN accounts can access the guardian dashboard
    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian accounts can access the guardian dashboard."
        )

    return get_guardian_dashboard(
        db=db,
        guardian_id=current_user.id
    )