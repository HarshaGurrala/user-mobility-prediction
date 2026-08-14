from fastapi import (
    APIRouter,
    Depends,
    HTTPException
)

from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user

from app.models.user import User

from app.services.location_alert_service import (
    respond_to_location_alert
)


router = APIRouter(
    prefix="/location-alert",
    tags=["Location Alerts"]
)


@router.put("/{alert_id}/respond")
def respond_location_alert(
    alert_id: int,
    response: str,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.role != "GUARDIAN":

        raise HTTPException(
            status_code=403,
            detail="Only guardian can respond to location alerts"
        )

    alert, error = respond_to_location_alert(
        db=db,
        alert_id=alert_id,
        guardian=current_user,
        response=response
    )

    if error:

        raise HTTPException(
            status_code=400,
            detail=error
        )

    return {
        "success": True,
        "message": "Location alert response recorded",
        "status": alert.status,
        "guardian_response": alert.guardian_response,
        "alert_id": alert.id
    }