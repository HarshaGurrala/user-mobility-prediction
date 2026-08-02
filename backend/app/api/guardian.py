from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user

from app.models.user import User
from app.models.user_guardian_relationship import UserGuardianRelationship

from app.schemas.guardian import GuardianRequest
from app.services.guardian_service import (
    send_request,
    get_pending_requests,
    accept_request,
    reject_request,
    get_connected_users,
    get_live_map_users,
    get_guardian_user_details,
    get_guardian_movement_analytics
)




router = APIRouter(
    prefix="/guardian",
    tags=["Guardian"]
)



# ==========================================================
# Guardian sends connection request to User
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
        "message":"Connection request sent successfully."
    }





# ==========================================================
# USER views pending requests
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
    request_id:int,
    current_user:User = Depends(get_current_user),
    db:Session = Depends(get_db),
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
        "message":"Connection accepted"
    }







# ==========================================================
# USER rejects guardian request
# ==========================================================

@router.put("/reject/{request_id}")
def reject(
    request_id:int,
    current_user:User = Depends(get_current_user),
    db:Session = Depends(get_db),
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
        "message":"Connection rejected"
    }







# ==========================================================
# GUARDIAN views connected users
# ==========================================================

@router.get("/connected-users")
def connected_users(
    current_user:User = Depends(get_current_user),
    db:Session = Depends(get_db),
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








# ==========================================================
# USER views connected guardians
# ==========================================================

@router.get("/my-guardians")
def my_guardians(
    current_user:User = Depends(get_current_user),
    db:Session = Depends(get_db),
):

    if current_user.role != "USER":

        raise HTTPException(
            status_code=403,
            detail="Only user accounts can view guardians."
        )


    relationships = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.user_id == current_user.id,
            UserGuardianRelationship.status=="ACCEPTED"
        )
        .all()
    )


    guardians=[]


    for relation in relationships:


        guardian = (
            db.query(User)
            .filter(
                User.id == relation.guardian_id
            )
            .first()
        )


        if guardian:

            guardians.append({

                "id":guardian.id,

                "name":guardian.full_name,

                "email":guardian.email,

                "phone":guardian.phone_number,

                "safe_path_id":guardian.safe_path_id,

                "status":relation.status

            })


    return guardians



# ==========================================================
# GUARDIAN views all connection request status
# ==========================================================

@router.get("/request-status")
def request_status(
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
):

    if current_user.role != "GUARDIAN":

        raise HTTPException(
            status_code=403,
            detail="Only guardian accounts can view request status."
        )


    requests = (

        db.query(
            UserGuardianRelationship,
            User
        )

        .join(
            User,
            User.id == UserGuardianRelationship.user_id
        )

        .filter(
            UserGuardianRelationship.guardian_id == current_user.id
        )

        .all()

    )


    result = []


    for relationship, user in requests:


        result.append({

            "request_id": relationship.id,

            "user_id": user.id,

            "user_name": user.full_name,

            "email": user.email,

            "safe_path_id": user.safe_path_id,

            "status": relationship.status

        })


    return result


@router.get("/linked-users")
def get_linked_users(
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    users = (
        db.query(UserGuardianRelationship, User)
        .join(
            User,
            User.id == UserGuardianRelationship.user_id
        )
        .filter(
            UserGuardianRelationship.guardian_id == current_user.id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .all()
    )


    result = []


    for relationship, user in users:

        result.append({

            "relationship_id": relationship.id,
            "user_id": user.id,
            "user_name": user.full_name,
            "email": user.email,
            "safe_path_id": user.safe_path_id

        })


    return result


@router.get("/stats")
def get_guardian_stats(
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    print("======================")
    print("USER ID:", current_user.id)
    print("ROLE:", current_user.role)


    rows = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == current_user.id
        )
        .all()
    )


    print("RELATION COUNT:", len(rows))


    for row in rows:
        print(
            row.id,
            row.guardian_id,
            row.user_id,
            row.status
        )


    linked_users = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == current_user.id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .count()
    )


    print("FINAL LINKED USERS:", linked_users)


    return {
        "linked_users": linked_users,
        "safety_status": "SAFE",
        "total_alerts": 0,
        "guardian_status": "ACTIVE"
    }

@router.get("/live-map")
def guardian_live_map(
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian accounts can access live map."
        )

    return get_live_map_users(
    db=db,
    guardian_id=current_user.id
)


@router.get("/user/{user_id}")
def guardian_user_details(
    user_id:int,
    current_user:User = Depends(get_current_user),
    db:Session = Depends(get_db)
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian can view user details"
        )


    result = get_guardian_user_details(
        db=db,
        guardian_id=current_user.id,
        user_id=user_id
    )


    if not result:
        raise HTTPException(
            status_code=404,
            detail="User not connected with guardian"
        )


    return result



# ==========================================================
# GUARDIAN FAMILY MOVEMENT ANALYTICS
# ==========================================================

@router.get("/movement-analytics")
def guardian_movement_analytics(
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
):

    if current_user.role != "GUARDIAN":

        raise HTTPException(
            status_code=403,
            detail="Only guardian can view movement analytics."
        )


    return get_guardian_movement_analytics(
        db=db,
        guardian_id=current_user.id
    )