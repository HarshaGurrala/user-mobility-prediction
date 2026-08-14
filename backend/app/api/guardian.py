from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user
from datetime import datetime, timedelta
from app.models.notification import Notification
from app.models.user import User
from app.models.user_guardian_relationship import UserGuardianRelationship
from app.models.alert import Alert
from app.schemas.guardian import GuardianRequest
from app.services.guardian_service import (
    send_request,
    get_pending_requests,
    accept_request,
    reject_request,
    get_connected_users,
    get_live_map_users,
    get_guardian_user_details,
    get_guardian_movement_analytics,
    get_guardian_user_movement_analytics,
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

            "id": user.id,

            "full_name": user.full_name,

            "email": user.email,

            "safe_path_id": user.safe_path_id,

            "is_online": user.is_online,

            "profile_picture": user.profile_picture

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

    # Get IDs of guardian's accepted users
    user_ids = [
        row.user_id
        for row in rows
        if row.status == "ACCEPTED"
    ]

    # Count real alerts generated for those users
    total_alerts = 0

    if user_ids:
        total_alerts = (
            db.query(Alert)
            .filter(
                Alert.user_id.in_(user_ids)
            )
            .count()
        )

    print("FINAL TOTAL ALERTS:", total_alerts)

    return {
        "linked_users": linked_users,
        "safety_status": "SAFE",
        "total_alerts": total_alerts,
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
    filter: str = "weekly",
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
        guardian_id=current_user.id,
        filter=filter
    )


@router.get("/user/{user_id}/movement-analytics")
def guardian_user_movement_analytics(
    user_id: int,
    filter: str = "weekly",
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian can view movement analytics."
        )

    return get_guardian_user_movement_analytics(
        db=db,
        guardian_id=current_user.id,
        user_id=user_id,
        filter=filter
    )


# ==========================================================
# GUARDIAN AI FAMILY SAFETY REPORT
# ==========================================================

@router.get("/{guardian_id}/ai-report")
def guardian_ai_report(
    guardian_id: int,
    db: Session = Depends(get_db)
):

    # Get accepted users connected to this guardian
    relationships = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == guardian_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .all()
    )

    total_users = len(relationships)

    if total_users == 0:

        return {
            "score": 100,
            "safeUsers": 0,
            "warnings": 0,
            "summary": "No connected users available for AI safety analysis."
        }

    cutoff_time = datetime.now() - timedelta(hours=48)

    warnings = 0
    safe_users = 0

    # ==========================================
    # CHECK EACH CONNECTED USER
    # ==========================================

    for relationship in relationships:

        user_id = relationship.user_id

        recent_notifications = (
            db.query(Notification)
            .filter(
                Notification.user_id == user_id,
                Notification.created_at >= cutoff_time
            )
            .all()
        )

        user_has_warning = False

        for notification in recent_notifications:

            notification_type = (
                notification.notification_type
                or ""
            ).upper()

            # Safety-related warnings
            if notification_type in [
                "SOS",
                "UNKNOWN_LOCATION",
                "ALERT",
                "DANGER"
            ]:

                warnings += 1

                user_has_warning = True

        if not user_has_warning:

            safe_users += 1

    # ==========================================
    # CALCULATE SAFETY SCORE
    # ==========================================

    # Each warning reduces the score.
    score = 100 - (warnings * 10)

    # Keep score between 0 and 100.
    score = max(
        0,
        min(
            100,
            score
        )
    )

    # ==========================================
    # AI SUMMARY
    # ==========================================

    if warnings == 0:

        summary = (
            "All connected users are currently safe. "
            "No recent safety warnings were detected."
        )

    elif score >= 80:

        summary = (
            "The family is mostly safe. "
            "A small number of safety warnings were detected "
            "during the last 48 hours."
        )

    elif score >= 50:

        summary = (
            "Some safety concerns were detected. "
            "Review the recent alerts and monitor the affected users."
        )

    else:

        summary = (
            "Multiple safety concerns were detected. "
            "Immediate attention to recent alerts is recommended."
        )

    return {
        "score": score,
        "safeUsers": safe_users,
        "warnings": warnings,
        "summary": summary
    }