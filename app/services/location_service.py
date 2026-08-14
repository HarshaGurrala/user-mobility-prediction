from sqlalchemy.orm import Session

from app.models.location import Location
from app.schemas.location import LocationCreate
from app.services.geocoding_service import (
    get_location_name
)
import requests

from app.services.location_check_service import (
    check_safe_location,
)
from app.services.prediction_service import update_prediction_result

def add_location(
    db: Session,
    user_id: int,
    location: LocationCreate
):

    address = (
        location.address
        if location.address
        else get_location_name(
            location.latitude,
            location.longitude
        )
    )

    new_location = Location(
        user_id=user_id,
        latitude=location.latitude,
        longitude=location.longitude,
        accuracy=location.accuracy,
        address=address
    )

    db.add(new_location)

    db.commit()

    db.refresh(new_location)

    update_prediction_result(
        db=db,
        user_id=user_id,
        latitude=location.latitude,
        longitude=location.longitude
    )

    # Automatically check whether the location
    # is inside a safe zone after saving it.
    safety_status = check_safe_location(
        db=db,
        user_id=user_id,
        latitude=location.latitude,
        longitude=location.longitude,
        address=address
    )

    return {
        "location": new_location,
        "safety": safety_status
    }


def get_current_location(
    db: Session,
    user_id: int
):

    return (
        db.query(Location)
        .filter(Location.user_id == user_id)
        .order_by(Location.timestamp.desc())
        .first()
    )


def get_location_history(
    db: Session,
    user_id: int
):

    return (
        db.query(Location)
        .filter(Location.user_id == user_id)
        .order_by(Location.timestamp.desc())
        .all()
    )



# def get_address_from_coordinates(
#     latitude: float,
#     longitude: float
# ):

    try:

        url = (
            "https://nominatim.openstreetmap.org/reverse"
        )

        params = {

            "lat": latitude,

            "lon": longitude,

            "format": "json",

            "zoom": 18

        }


        headers = {

            "User-Agent":
            "SafePathAI-App"

        }


        response = requests.get(
            url,
            params=params,
            headers=headers,
            timeout=5
        )


        if response.status_code == 200:

            data = response.json()


            address = data.get(
                "display_name"
            )


            if address:

                return address


        return "Unknown Location"


    except Exception:

        return "Unknown Location"