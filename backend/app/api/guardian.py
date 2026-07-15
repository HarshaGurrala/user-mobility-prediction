from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user
from app.models.user import User
from app.models.guardian_link import GuardianLink


router = APIRouter(
    prefix="/guardian",
    tags=["Guardian"]
)


@router.post("/link")
def link_child(
    guardian_code: str,
    db: Session = Depends(get_db),
    current_user = Depends(get_current_user)
):

    guardian = db.query(User).filter(
        User.guardian_code == guardian_code
    ).first()


    if guardian is None:
        raise HTTPException(
            status_code=404,
            detail="Invalid guardian code"
        )


    if guardian.role != "GUARDIAN":
        raise HTTPException(
            status_code=400,
            detail="User is not a guardian"
        )


    link = GuardianLink(
        guardian_user_id=guardian.id,
        child_user_id=current_user.id
    )


    db.add(link)
    db.commit()
    db.refresh(link)


    return {
        "message": "Child linked successfully"
    }