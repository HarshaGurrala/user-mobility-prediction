from sqlalchemy.orm import Session

from app.models.location import Location
from app.models.prediction import Prediction
from app.models.alert import Alert

from app.models.user_guardian_relationship import UserGuardianRelationship
from app.services.safety_service import get_safety_status
from app.services.geocoding_service import get_location_name

def get_guardian_dashboard(
    db: Session,
    guardian_id: int
):

    connections = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == guardian_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .all()
    )


    children = []


    for connection in connections:

        user_id = connection.user_id


        # Latest location

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


        # Latest prediction

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


        # Latest alert

        alert = (
            db.query(Alert)
            .filter(
                Alert.user_id == user_id
            )
            .order_by(
                Alert.created_at.desc()
            )
            .first()
        )


        children.append(
            {
                "user_id": user_id,

                # " safety_status": safety,

               "current_location": {

                "latitude":
                location.latitude if location else None,


                "longitude":
                location.longitude if location else None,


                "place_name":
                get_location_name(
                    location.latitude,
                    location.longitude
                )
                if location else "Unknown Location"

            },


                "prediction": {

                    "location":
                    prediction.predicted_location if prediction else None,

                    "confidence":
                    prediction.confidence if prediction else None

                },


                "safety_status": get_safety_status(db, user_id),


                "latest_alert": {

                    "type":
                    alert.alert_type if alert else None,

                    "message":
                    alert.message if alert else None

                }


            }
        )


    return {

        "guardian_id": guardian_id,

        "children": children

    }