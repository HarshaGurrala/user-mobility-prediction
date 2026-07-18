from fastapi import APIRouter, Depends

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



@router.get("/{guardian_id}")
def dashboard(

    guardian_id:int,

    db:Session=Depends(get_db),

    current_user: User = Depends(get_current_user)

):

    return get_guardian_dashboard(

        db,

        guardian_id

    )