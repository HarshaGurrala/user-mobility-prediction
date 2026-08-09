from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user
from app.models.user import User

from app.services.dashboard_service import (
    get_guardian_dashboard
)

from app.models.safe_location import SafeLocation
from app.models.emergency_contact import EmergencyContact

from app.models.location import Location
from app.models.prediction import Prediction
from app.models.alert import Alert
from app.models.user_guardian_relationship import UserGuardianRelationship
from app.services.safety_service import get_safety_status

from app.models.safety_history import SafetyHistory


router = APIRouter()


# ==================================================
# Guardian Web Dashboard
# ==================================================

guardian_router = APIRouter(
    prefix="/dashboard",
    tags=["Guardian Dashboard"]
)


@guardian_router.get("/{guardian_id}")
def dashboard(
    guardian_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Guardian access required"
        )

    return get_guardian_dashboard(
        db,
        guardian_id
    )


# ==================================================
# Android User Dashboard
# ==================================================


user_dashboard_router = APIRouter(
    prefix="/user-dashboard",
    tags=["User Dashboard"]
)


@user_dashboard_router.get("/me")
def get_user_dashboard(
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    latest_location = (
    db.query(Location)
    .filter(Location.user_id == current_user.id)
    .order_by(Location.timestamp.desc())
    .first()
)

    latest_prediction = (
        db.query(Prediction)
        .filter(Prediction.user_id == current_user.id)
        .order_by(Prediction.created_at.desc())
        .first()
    )

    latest_alert = (
        db.query(Alert)
        .filter(Alert.user_id == current_user.id)
        .order_by(Alert.created_at.desc())
        .first()
    )

    safety = get_safety_status(
        db=db,
        user_id=current_user.id
    )

    
    last_event = (
        db.query(SafetyHistory)
        .filter(
            SafetyHistory.user_id == current_user.id
        )
        .order_by(
            SafetyHistory.timestamp.desc()
        )
        .first()
    )

    guardian = (
            db.query(UserGuardianRelationship)
            .filter(
                UserGuardianRelationship.user_id == current_user.id,
                UserGuardianRelationship.status == "ACCEPTED"
            )
            .first()
        )

    emergency_contacts = (
            db.query(EmergencyContact)
            .filter(
                EmergencyContact.user_id == current_user.id
            )
            .all()
        )

    safe_locations = (
            db.query(SafeLocation)
            .filter(
                SafeLocation.user_id == current_user.id
            )
            .all()
        )




    return {

        "userName": current_user.full_name,

       "safetyStatus":
            safety["status"],

        

        "currentLocation":
            latest_location.address
            if latest_location and latest_location.address
            else "Location unavailable",



        "trackingStatus":
            "Tracking Active"
            if latest_location
            else "Tracking Inactive",


        "safetyMessage":
            safety["message"],

        "lastEvent":
            last_event.event_type
            if last_event
            else None,

        "lastUpdated":
            last_event.timestamp
            if last_event
            else None,

        "safeZone":
            safety["location_name"]
            if safety["location_name"]
            else "Unknown",


        "guardianStatus":
            "Guardian Connected"
            if guardian
            else "No Guardian Connected",


        "emergencyContacts": [

            {
                "id": contact.id,
                "name": contact.name,
                "relationshipType":
                    contact.relationship_type,
                "phoneNumber":
                    contact.phone_number,
                "email":
                    contact.email
            }

            for contact in emergency_contacts

        ],
        


        "safeLocations": [

            {
                "id": location.id,
                "locationName":
                    location.location_name,
                "latitude":
                    location.latitude,
                "longitude":
                    location.longitude,
                "radius":
                    location.radius
            }

            for location in safe_locations

        ],


        "prediction":
            latest_prediction.predicted_location
            if latest_prediction
            else "No prediction available",


        "confidence":
            latest_prediction.confidence
            if latest_prediction
            else "--",


        "recentAlert":
            latest_alert.message
            if latest_alert
            else "No recent alerts",
    }



# ==================================================
# Register Routers
# ==================================================

router.include_router(
    guardian_router
)

router.include_router(
    user_dashboard_router
)