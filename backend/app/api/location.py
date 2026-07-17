from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user

from app.schemas.location import LocationCreate

from app.models.user import User
from app.models.guardian_link import GuardianLink
from app.models.safe_zone import SafeZone

from app.services.location_service import (
    save_location,
    get_location_history,
    get_latest_location,
)
from app.services.location_alert_service import check_location_alerts
from app.services.geofence_service import is_inside_safe_zone
from app.services.ai_prediction_service import generate_prediction



router = APIRouter(
    prefix="/location",
    tags=["Live Location"]
)


@router.post("/update")
def update_location(
    location: LocationCreate,
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    saved_location = save_location(
        db,
        current_user,
        location
    )

    # Generate AI Prediction
    generate_prediction(
        db,
        current_user.id
    )

    check_location_alerts(
    db,
    current_user,
    saved_location.latitude,
    saved_location.longitude
)

 

    # Find Guardian
    guardian_link = (
        db.query(GuardianLink)
        .filter(
            GuardianLink.child_user_id == current_user.id
        )
        .first()
    )

    if guardian_link:

        safe_zones = (
            db.query(SafeZone)
            .filter(
                SafeZone.guardian_user_id == guardian_link.guardian_user_id
            )
            .all()
        )

        for zone in safe_zones:

            inside = is_inside_safe_zone(
                saved_location.latitude,
                saved_location.longitude,
                zone
            )

            if inside and not zone.is_inside:

                zone.is_inside = True

                print(f"✅ Entered Safe Zone: {zone.name}")

            elif not inside and zone.is_inside:

                zone.is_inside = False

                print(f"🚨 Exited Safe Zone: {zone.name}")

    db.commit()

    return {
        "message": "Location updated successfully",
        "location_id": saved_location.id
    }


@router.get("/history")
def history(
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    return get_location_history(
        db,
        current_user
    )


@router.get("/latest/{child_id}")
def latest(
    child_id: int,
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    guardian = (
        db.query(User)
        .filter(
            User.email == current_user.email
        )
        .first()
    )

    link = (
        db.query(GuardianLink)
        .filter(
            GuardianLink.guardian_user_id == guardian.id,
            GuardianLink.child_user_id == child_id
        )
        .first()
    )

    if not link:

        return {
            "message": "Child not linked"
        }

    child = (
        db.query(User)
        .filter(
            User.id == child_id
        )
        .first()
    )

    return get_latest_location(
        db,
        child
    )