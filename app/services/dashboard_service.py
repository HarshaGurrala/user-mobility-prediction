from sqlalchemy.orm import Session

from app.models.location import Location
from app.models.prediction import Prediction
from app.models.alert import Alert
from app.models.user_guardian_relationship import (
    UserGuardianRelationship
)

from app.services.safety_service import get_safety_status


def get_guardian_dashboard(
    db: Session,
    guardian_id: int
):

    # -----------------------------------------
    # Get accepted connections
    # -----------------------------------------

    connections = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == guardian_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .all()
    )

    children = []


    # -----------------------------------------
    # Process every connected user
    # -----------------------------------------

    for connection in connections:

        user_id = connection.user_id


        # -----------------------------------------
        # Latest Location
        # -----------------------------------------

        location = (
            db.query(Location)
            .filter(
                Location.user_id == user_id
            )
            .order_by(
                Location.timestamp.desc()
            )
            .first()
        )


        # -----------------------------------------
        # Location Name
        # -----------------------------------------

        place_name = (
            location.address
            if location and location.address
            else "Unknown Location"
        )


        # -----------------------------------------
        # Latest AI Prediction
        # -----------------------------------------

        prediction = (
            db.query(Prediction)
            .filter(
                Prediction.user_id == user_id
            )
            .order_by(
                Prediction.created_at.desc()
            )
            .first()
        )


        # -----------------------------------------
        # Latest Alert
        # -----------------------------------------

        alert = (
            db.query(Alert)
            .filter(
                Alert.user_id == user_id,
                Alert.guardian_id == guardian_id
            )
            .order_by(
                Alert.created_at.desc()
            )
            .first()
        )


        # -----------------------------------------
        # Safety Status
        # -----------------------------------------

        safety = get_safety_status(
            db,
            user_id
        )


        # -----------------------------------------
        # Add User Dashboard Data
        # -----------------------------------------

        children.append(
            {
                "user_id": user_id,

                "current_location": {

                    "latitude":
                        location.latitude
                        if location
                        else None,

                    "longitude":
                        location.longitude
                        if location
                        else None,

                    "place_name":
                        place_name

                },


                "prediction": {

                    "location":
                        prediction.predicted_location
                        if prediction
                        else None,

                    "confidence":
                        prediction.confidence
                        if prediction
                        else None

                },


                "safety_status":
                    safety,


                "latest_alert": {

                    "type":
                        alert.alert_type
                        if alert
                        else None,

                    "message":
                        alert.message
                        if alert
                        else None

                }

            }
        )


    # -----------------------------------------
    # Final Dashboard Response
    # -----------------------------------------

    return {

        "guardian_id":
            guardian_id,

        "children":
            children

    }