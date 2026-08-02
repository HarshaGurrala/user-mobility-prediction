from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user

from app.models.user import User



from app.services.analytics_service import (
    get_overview,
    get_daily_distance,
    get_weekly_distance,
    get_prediction_analytics,
    get_safety_analytics,
    get_alert_analytics,
    get_safe_zone_analytics,
    get_movement_analytics,
  
)


router = APIRouter(
    prefix="/analytics",
    tags=["Analytics"]
)



@router.get("/overview/{user_id}")
def analytics_overview(
    user_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    print("========== ANALYTICS ROUTE HIT ==========")
    print("CURRENT USER:", current_user.id)
    print("REQUEST USER:", user_id)


    if current_user.id != user_id:

        raise HTTPException(
            status_code=403,
            detail="Unauthorized"
        )


    return get_overview(
        db,
        user_id
    )





@router.get("/daily-distance/{user_id}")
def daily_distance(
    user_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.id != user_id:

        raise HTTPException(
            status_code=403,
            detail="Unauthorized"
        )


    return get_daily_distance(
        db,
        user_id
    )







@router.get("/weekly-distance/{user_id}")
def weekly_distance(
    user_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.id != user_id:

        raise HTTPException(
            status_code=403,
            detail="Unauthorized"
        )


    return get_weekly_distance(
        db,
        user_id
    )







@router.get("/predictions/{user_id}")
def prediction_analytics(
    user_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.id != user_id:

        raise HTTPException(
            status_code=403,
            detail="Unauthorized"
        )


    return get_prediction_analytics(
        db,
        user_id
    )







@router.get("/safety/{user_id}")
def safety_analytics(
    user_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.id != user_id:

        raise HTTPException(
            status_code=403,
            detail="Unauthorized"
        )


    return get_safety_analytics(
        db,
        user_id
    )







@router.get("/alerts/{user_id}")
def alert_analytics(
    user_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.id != user_id:

        raise HTTPException(
            status_code=403,
            detail="Unauthorized"
        )


    return get_alert_analytics(
        db,
        user_id
    )








@router.get("/safe-zones/{user_id}")
def safe_zone_analytics(
    user_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.id != user_id:

        raise HTTPException(
            status_code=403,
            detail="Unauthorized"
        )


    return get_safe_zone_analytics(
        db,
        user_id
    )








# ======================================================
# Movement Analytics
# ======================================================

@router.get("/movement/{user_id}")
def movement_analytics(
    user_id:int,
    db:Session = Depends(get_db),
    current_user:User = Depends(get_current_user)
):

    if current_user.id != user_id:

        raise HTTPException(
            status_code=403,
            detail="Unauthorized"
        )


    return {

        "user_id":user_id,

        "movement":
        get_movement_analytics(
            db,
            user_id
        )

    }

@router.get("/family-movement")
def family_movement(
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian allowed"
        )


    return get_family_movement(
        db,
        current_user.id
    )