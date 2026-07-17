from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user
from app.models.user import User
from app.models.guardian_link import GuardianLink


from app.services.guardian_service import (
    get_my_children,
    get_child_latest_location,
)

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

@router.get("/children")
def my_children(
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    print("=" * 50)
    print("Logged In User ID:", current_user.id)
    print("Logged In Email:", current_user.email)
    print("Logged In Role:", current_user.role)
    print("=" * 50)

    return get_my_children(
        db,
        current_user
    )


# @router.get("/child/{child_id}/location")
# def child_location(
#     child_id: int,
#     db: Session = Depends(get_db),
#     current_user=Depends(get_current_user)
# ):
@router.get("/child/{child_id}/location")
def child_location(
    child_id: int,
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    children = get_my_children(
        db,
        current_user
    )

    child_ids = [
        child["id"]
        for child in children
    ]

    if child_id not in child_ids:
        raise HTTPException(
            status_code=403,
            detail="Access Denied"
        )

    location = get_child_latest_location(
        db,
        child_id
    )

    if location is None:
        raise HTTPException(
            status_code=404,
            detail="Location not found"
        )

    child = db.query(User).filter(
        User.id == child_id
    ).first()

    return {
        "child": child.full_name,
        "latitude": location.latitude,
        "longitude": location.longitude,
        "speed": location.speed,
        "accuracy": location.accuracy,
        "battery": 80
    }

    # children = get_my_children(
    #     db,
    #     current_user
    # )

    # child_ids = [
    #     child["id"]
    #     for child in children
    # ]

    # if child_id not in child_ids:

    #     raise HTTPException(
    #         status_code=403,
    #         detail="Access Denied"
    #     )

    # return get_child_latest_location(
    #     db,
    #     child_id
    # )