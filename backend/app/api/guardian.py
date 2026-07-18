from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db

from app.schemas.guardian import GuardianRequest



from app.services.guardian_service import (
    get_connected_users,
    send_request,
    get_pending_requests,
    accept_request,
    reject_request
)

router = APIRouter(
    prefix="/guardian",
    tags=["Guardian"]
)


@router.post("/connect")
def connect(
    request: GuardianRequest,
    db: Session = Depends(get_db)
):

    result = send_request(
        db,
        request.guardian_id,
        request.safe_path_id
    )

    if result is None:
        raise HTTPException(
            status_code=404,
            detail="Invalid SafePath ID"
        )

    if result == "SELF":
        raise HTTPException(
            status_code=400,
            detail="You cannot connect to yourself."
        )

    if result == "EXISTS":
        raise HTTPException(
            status_code=400,
            detail="Connection request already exists."
        )

    return {
        "message": "Connection request sent successfully."
    }


@router.get("/pending/{user_id}")
def pending_requests(
    user_id: int,
    db: Session = Depends(get_db)
):

    return get_pending_requests(
        db,
        user_id
    )





@router.put("/accept/{request_id}")
def accept(
    request_id: int,
    db: Session = Depends(get_db)
):
    request = accept_request(db, request_id)

    if request is None:
        raise HTTPException(
            status_code=404,
            detail="Request not found"
        )

    return {
        "message": "Connection accepted"
    }


@router.put("/reject/{request_id}")
def reject(
    request_id: int,
    db: Session = Depends(get_db)
):
    request = reject_request(db, request_id)

    if request is None:
        raise HTTPException(
            status_code=404,
            detail="Request not found"
        )

    return {
        "message": "Connection rejected"
    }

@router.get("/connected-users/{guardian_id}")
def connected_users(
    guardian_id: int,
    db: Session = Depends(get_db)
):
    return get_connected_users(db, guardian_id)