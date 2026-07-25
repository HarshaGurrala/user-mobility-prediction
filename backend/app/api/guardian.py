from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user
from app.models.user import User
from app.schemas.guardian import GuardianRequest

from app.services.guardian_service import (
    send_request,
    get_pending_requests,
    accept_request,
    reject_request,
    get_connected_users,
)

router = APIRouter(
    prefix="/guardian",
    tags=["Guardian"]
)


# ==========================================================
# Guardian sends connection request to a User
# ==========================================================

@router.post("/connect")
def connect(
    request: GuardianRequest,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian accounts can send connection requests."
        )

    result = send_request(
        db=db,
        guardian_id=current_user.id,
        safe_path_id=request.safe_path_id
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

    if result == "INVALID_ROLE":
        raise HTTPException(
            status_code=400,
            detail="Guardian can connect only with USER accounts."
        )

    if result == "EXISTS":
        raise HTTPException(
            status_code=400,
            detail="Connection request already exists."
        )

    return {
        "message": "Connection request sent successfully."
    }


# ==========================================================
# USER views pending guardian requests
# ==========================================================

@router.get("/pending")
def pending_requests(
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
):

    if current_user.role != "USER":
        raise HTTPException(
            status_code=403,
            detail="Only user accounts can view pending requests."
        )

    return get_pending_requests(
        db=db,
        user_id=current_user.id
    )


# ==========================================================
# USER accepts guardian request
# ==========================================================

@router.put("/accept/{request_id}")
def accept(
    request_id: int,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
):

    if current_user.role != "USER":
        raise HTTPException(
            status_code=403,
            detail="Only user accounts can accept requests."
        )

    request = accept_request(
        db=db,
        request_id=request_id,
        user_id=current_user.id
    )

    if request is None:
        raise HTTPException(
            status_code=404,
            detail="Request not found"
        )

    return {
        "message": "Connection accepted"
    }


# ==========================================================
# USER rejects guardian request
# ==========================================================

@router.put("/reject/{request_id}")
def reject(
    request_id: int,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
):

    if current_user.role != "USER":
        raise HTTPException(
            status_code=403,
            detail="Only user accounts can reject requests."
        )

    request = reject_request(
        db=db,
        request_id=request_id,
        user_id=current_user.id
    )

    if request is None:
        raise HTTPException(
            status_code=404,
            detail="Request not found"
        )

    return {
        "message": "Connection rejected"
    }


# ==========================================================
# GUARDIAN views connected users
# ==========================================================

@router.get("/connected-users")
def connected_users(
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian accounts can view connected users."
        )

    return get_connected_users(
        db=db,
        guardian_id=current_user.id
    )