from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user

from app.services.alert_query_service import (
    get_all_alerts,
    get_unread_alerts,
    mark_alert_read,
    delete_alert,
)

router = APIRouter(
    prefix="/alert",
    tags=["Alerts"]
)


@router.get("/all")
def all_alerts(
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    return get_all_alerts(
        db,
        current_user.id
    )


@router.get("/unread")
def unread_alerts(
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    return get_unread_alerts(
        db,
        current_user.id
    )


@router.put("/read/{alert_id}")
def read_alert(
    alert_id: int,
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    return mark_alert_read(
        db,
        current_user.id,
        alert_id
    )


@router.delete("/{alert_id}")
def remove_alert(
    alert_id: int,
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    return delete_alert(
        db,
        current_user.id,
        alert_id
    )