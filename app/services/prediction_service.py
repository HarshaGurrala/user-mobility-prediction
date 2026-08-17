from datetime import datetime

from sqlalchemy.orm import Session

from app.models.location import Location
from app.models.prediction import Prediction

from app.ml.train import train_location_model
from app.ml.predictor import predict_next_location

from app.utils.location_utils import calculate_distance

import math
from datetime import datetime

from app.services.geocoding_service import (
    get_location_name
)


def get_location_history(
    db: Session,
    user_id: int
):

    return (
        db.query(Location)
        .filter(
            Location.user_id == user_id
        )
        .order_by(
            Location.timestamp.asc()
        )
        .all()
    )



def train_user_model(
    db: Session,
    user_id: int
):

    history = get_location_history(
        db,
        user_id
    )

    if len(history) < 5:
        return False


    data = []


    for location in history:

        data.append({

            "latitude":
                location.latitude,

            "longitude":
                location.longitude,

            "timestamp":
                location.timestamp

        })


    train_location_model(
        data
    )


    return True

def calculate_distance_km(
    lat1,
    lon1,
    lat2,
    lon2
):
    R = 6371.0

    lat1_rad = math.radians(lat1)
    lat2_rad = math.radians(lat2)

    delta_lat = math.radians(
        lat2 - lat1
    )

    delta_lon = math.radians(
        lon2 - lon1
    )

    a = (
        math.sin(delta_lat / 2) ** 2
        +
        math.cos(lat1_rad)
        *
        math.cos(lat2_rad)
        *
        math.sin(delta_lon / 2) ** 2
    )

    c = 2 * math.atan2(
        math.sqrt(a),
        math.sqrt(1 - a)
    )

    return R * c



def calculate_prediction_eta(
    db: Session,
    user_id: int,
    predicted_latitude: float,
    predicted_longitude: float
):

    history = get_location_history(
        db,
        user_id
    )

    if len(history) < 2:
        return None

    latest = history[-1]

    # Find the most recent previous location
    # where the user actually moved.
    previous = None

    for location in reversed(history[:-1]):

        distance_km = calculate_distance_km(
            location.latitude,
            location.longitude,
            latest.latitude,
            latest.longitude
        )

        if distance_km > 0.001:
            previous = location
            break

    if previous is None:
        return None

    if not latest.timestamp or not previous.timestamp:
        return None

    time_difference = (
        latest.timestamp -
        previous.timestamp
    ).total_seconds()

    if time_difference <= 0:
        return None

    speed_kmh = (
        distance_km /
        (time_difference / 3600)
    )

    # Ignore invalid/unrealistic GPS speed
    if speed_kmh <= 0 or speed_kmh > 150:
        return None

    prediction_distance_km = calculate_distance_km(
        latest.latitude,
        latest.longitude,
        predicted_latitude,
        predicted_longitude
    )

    if prediction_distance_km <= 0:
        return 0

    eta_minutes = round(
        (
            prediction_distance_km /
            speed_kmh
        ) * 60
    )

    return max(
        1,
        eta_minutes
    )



def generate_prediction(
    db: Session,
    user_id: int
):

    history = get_location_history(
        db,
        user_id
    )


    if len(history) < 5:

        return {

            "message":
            "Not enough location history for prediction."

        }



    trained = train_user_model(
        db,
        user_id
    )


    if not trained:

        return {

            "message":
            "Model training failed."

        }



    latest = history[-1]



    prediction = predict_next_location(

        latitude =
            latest.latitude,

        longitude =
            latest.longitude,

        hour =
            datetime.now().hour

    )



    # Convert predicted coordinates
    # into human readable location

    predicted_address = (
        get_location_name(

            prediction["latitude"],

            prediction["longitude"]

        )
    )



    prediction_record = Prediction(

        user_id =
            user_id,


        predicted_location =
            predicted_address,


        predicted_latitude =
            prediction["latitude"],


        predicted_longitude =
            prediction["longitude"],


        confidence =
            prediction["confidence"],


        actual_latitude =
            None,


        actual_longitude =
            None,


        prediction_accuracy =
            None,


        matched =
            False

    )


    db.add(
        prediction_record
    )


    db.commit()


    db.refresh(
        prediction_record
    )



    return {

        "location":
            prediction_record.predicted_location,


        "confidence":
            prediction["confidence"]

    }




def get_latest_prediction(
    db: Session,
    user_id: int
):

    return (

        db.query(Prediction)

        .filter(
            Prediction.user_id == user_id
        )

        .order_by(
            Prediction.created_at.desc()
        )

        .first()

    )





def update_prediction_result(
    db: Session,
    user_id: int,
    latitude: float,
    longitude: float
):


    prediction = (

        db.query(Prediction)

        .filter(

            Prediction.user_id == user_id,

            Prediction.matched == False

        )

        .order_by(
            Prediction.created_at.desc()
        )

        .first()

    )



    if not prediction:

        return



    prediction.actual_latitude = latitude

    prediction.actual_longitude = longitude



    distance = calculate_distance(

        prediction.predicted_latitude,

        prediction.predicted_longitude,

        latitude,

        longitude

    )



    prediction.prediction_accuracy = round(
        distance,
        2
    )


    prediction.matched = (
        distance <= 100
    )


    db.commit()





def get_prediction_history(
    db: Session,
    user_id: int
):

    return (

        db.query(Prediction)

        .filter(
            Prediction.user_id == user_id
        )

        .order_by(
            Prediction.created_at.desc()
        )

        .all()

    )





def get_prediction_statistics(
    db: Session,
    user_id: int
):

    predictions = (

        db.query(Prediction)

        .filter(
            Prediction.user_id == user_id
        )

        .all()

    )



    if not predictions:

        return {

            "total_predictions":
                0,

            "matched_predictions":
                0,

            "average_accuracy":
                0,

            "success_rate":
                0

        }



    matched = [

        p for p in predictions

        if p.matched

    ]



    accuracy = [

        p.prediction_accuracy

        for p in predictions

        if p.prediction_accuracy is not None

    ]



    average_accuracy = (

        round(
            sum(accuracy) / len(accuracy),
            2
        )

        if accuracy

        else 0

    )



    success_rate = round(

        (
            len(matched)
            /
            len(predictions)

        )
        *
        100,

        2

    )



    return {

        "total_predictions":
            len(predictions),


        "matched_predictions":
            len(matched),


        "average_accuracy":
            average_accuracy,


        "success_rate":
            success_rate

    }