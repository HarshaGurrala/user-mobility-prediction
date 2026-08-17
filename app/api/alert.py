from fastapi import APIRouter, Depends

from sqlalchemy.orm import Session

from app.database.database import get_db

from app.schemas.alert import AlertCreate

from app.services.alert_service import (
    create_alert,
    get_user_alerts,
    get_guardian_alerts
)


router = APIRouter(
    prefix="/alert",
    tags=["Alerts"]
)




@router.post("/create")
def create(
    alert: AlertCreate,
    db: Session = Depends(get_db)
):

    return create_alert(
        db,
        alert
    )



@router.get("/user/{user_id}")
def user_alerts(
    user_id: int,
    db: Session = Depends(get_db)
):

    return get_user_alerts(
        db,
        user_id
    )



@router.get("/guardian/{guardian_id}/alerts")
def guardian_alerts(
    guardian_id: int,
    db: Session = Depends(get_db)
):

    return get_guardian_alerts(
        db,
        guardian_id
    )