from math import radians, sin, cos, sqrt, atan2
from sqlalchemy.orm import Session
from sqlalchemy import func
from collections import defaultdict
from datetime import datetime
from app.models.location import Location
from app.models.safe_location import SafeLocation
from app.models.alert import Alert
from app.models.prediction import Prediction

from app.services.safety_service import get_safety_status


EARTH_RADIUS = 6371000


def calculate_distance(
    lat1,
    lon1,
    lat2,
    lon2
):
    lat1 = radians(lat1)
    lon1 = radians(lon1)
    lat2 = radians(lat2)
    lon2 = radians(lon2)

    dlat = lat2 - lat1
    dlon = lon2 - lon1

    a = (
        sin(dlat / 2) ** 2
        + cos(lat1)
        * cos(lat2)
        * sin(dlon / 2) ** 2
    )

    c = 2 * atan2(
        sqrt(a),
        sqrt(1 - a)
    )

    return EARTH_RADIUS * c


def get_overview(
    db: Session,
    user_id: int
):

    locations = (
        db.query(Location)
        .filter(Location.user_id == user_id)
        .order_by(Location.timestamp.asc())
        .all()
    )

    total_distance = 0

    for i in range(1, len(locations)):

        total_distance += calculate_distance(

            locations[i - 1].latitude,
            locations[i - 1].longitude,

            locations[i].latitude,
            locations[i].longitude
        )

    latest = locations[-1] if locations else None

    safety = get_safety_status(
        db,
        user_id
    )

    return {

        "total_locations": len(locations),

        "total_distance": round(
            total_distance / 1000,
            2
        ),

        "safe_locations":

        db.query(SafeLocation)
        .filter(
            SafeLocation.user_id == user_id
        )
        .count(),

        "total_alerts":

        db.query(Alert)
        .filter(
            Alert.user_id == user_id
        )
        .count(),

        "total_predictions":

        db.query(Prediction)
        .filter(
            Prediction.user_id == user_id
        )
        .count(),

        "prediction_success_rate":

        db.query(
            func.avg(
                Prediction.prediction_accuracy
            )
        )
        .filter(
            Prediction.user_id == user_id
        )
        .scalar(),

        "current_safety_status":
            safety["status"],

        "current_location":
            latest.address
            if latest
            else "Unknown"

    }


def get_daily_distance(
    db: Session,
    user_id: int
):

    locations = (
        db.query(Location)
        .filter(
            Location.user_id == user_id
        )
        .order_by(Location.timestamp.asc())
        .all()
    )

    daily_distance = defaultdict(float)

    for i in range(1, len(locations)):

        previous = locations[i - 1]
        current = locations[i]

        distance = calculate_distance(
            previous.latitude,
            previous.longitude,
            current.latitude,
            current.longitude
        )

        date = current.timestamp.strftime("%Y-%m-%d")

        daily_distance[date] += distance

    result = []

    for date, distance in sorted(daily_distance.items()):

        result.append(
            {
                "date": date,
                "distance": round(distance / 1000, 2)
            }
        )

    return result



def get_weekly_distance(
    db: Session,
    user_id: int
):

    locations = (
        db.query(Location)
        .filter(
            Location.user_id == user_id
        )
        .order_by(Location.timestamp.asc())
        .all()
    )

    weekly = defaultdict(float)

    for i in range(1, len(locations)):

        previous = locations[i - 1]
        current = locations[i]

        distance = calculate_distance(
            previous.latitude,
            previous.longitude,
            current.latitude,
            current.longitude
        )

        year, week, _ = current.timestamp.isocalendar()

        key = f"{year}-W{week}"

        weekly[key] += distance

    result = []

    for week, distance in sorted(weekly.items()):

        result.append(
            {
                "week": week,
                "distance": round(distance / 1000, 2)
            }
        )

    return result


def get_prediction_analytics(
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

    total = len(predictions)

    matched = sum(
        1 for p in predictions
        if p.matched
    )

    failed = total - matched

    avg_confidence = (
        sum(p.confidence for p in predictions) / total
        if total > 0
        else 0
    )

    valid_accuracy = [
        p.prediction_accuracy
        for p in predictions
        if p.prediction_accuracy is not None
    ]

    avg_accuracy = (
        sum(valid_accuracy) / len(valid_accuracy)
        if valid_accuracy
        else 0
    )

    return {

        "total_predictions": total,

        "matched_predictions": matched,

        "failed_predictions": failed,

        "average_confidence": round(
            avg_confidence,
            2
        ),

        "average_accuracy": round(
            avg_accuracy,
            2
        )
    }


def get_safety_analytics(
    db: Session,
    user_id: int
):

    from app.models.safety_history import SafetyHistory

    history = (
        db.query(SafetyHistory)
        .filter(
            SafetyHistory.user_id == user_id
        )
        .all()
    )

    safe = 0
    warning = 0
    unknown = 0

    for event in history:

        if event.event_type == "ENTERED_SAFE_ZONE":
            safe += 1

        elif event.event_type == "LEFT_SAFE_ZONE":
            warning += 1

        elif event.event_type == "UNKNOWN_LOCATION":
            unknown += 1

    return {

        "safe_events": safe,

        "warning_events": warning,

        "unknown_events": unknown,

        "total_events": len(history)

    }


def get_alert_analytics(
    db: Session,
    user_id: int
):

    alerts = (
        db.query(Alert)
        .filter(
            Alert.user_id == user_id
        )
        .all()
    )

    total_alerts = len(alerts)

    read_alerts = sum(
        1 for alert in alerts
        if alert.is_read
    )

    unread_alerts = (
        total_alerts - read_alerts
    )

    alert_types = defaultdict(int)

    for alert in alerts:

        alert_types[
            alert.alert_type
        ] += 1


    return {

        "total_alerts": total_alerts,

        "read_alerts": read_alerts,

        "unread_alerts": unread_alerts,

        "alert_distribution": [
            {
                "type": key,
                "count": value
            }
            for key, value
            in alert_types.items()
        ]
    }



def get_safe_zone_analytics(
    db: Session,
    user_id: int
):

    safe_locations = (
        db.query(SafeLocation)
        .filter(
            SafeLocation.user_id == user_id
        )
        .all()
    )


    zones = []

    for zone in safe_locations:

        zones.append(
            {
                "location_name":
                    zone.location_name,

                "radius":
                    zone.radius,

                "latitude":
                    zone.latitude,

                "longitude":
                    zone.longitude
            }
        )


    return {

        "total_safe_zones":
            len(safe_locations),

        "zones":
            zones
    }