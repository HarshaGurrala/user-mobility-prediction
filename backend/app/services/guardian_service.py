from datetime import datetime

from sqlalchemy.orm import Session

from app.models.user import User
from app.models.user_guardian_relationship import UserGuardianRelationship

from app.services.online_status_service import check_user_online_status

from datetime import datetime, timedelta

from collections import defaultdict

import calendar


from sqlalchemy import desc

from app.models.location import Location

from math import radians, sin, cos, sqrt, atan2
from datetime import datetime

def send_request(
    db: Session,
    guardian_id: int,
    safe_path_id: str
):
    # Find the USER using SafePath ID
    user = (
        db.query(User)
        .filter(User.safe_path_id == safe_path_id)
        .first()
    )

    if not user:
        return None

    # SafePath ID must belong to a USER
    if user.role != "USER":
        return "INVALID_ROLE"

    # Guardian cannot connect to themselves
    if guardian_id == user.id:
        return "SELF"

    # Check existing relationship
    existing = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == guardian_id,
            UserGuardianRelationship.user_id == user.id
        )
        .first()
    )

    if existing:
        return "EXISTS"

    relationship = UserGuardianRelationship(
        guardian_id=guardian_id,
        user_id=user.id,
        status="PENDING"
    )

    db.add(relationship)
    db.commit()
    db.refresh(relationship)

    return relationship

def get_pending_requests(
    db: Session,
    user_id: int
):
    requests = (
        db.query(
            UserGuardianRelationship,
            User
        )
        .join(
            User,
            User.id == UserGuardianRelationship.guardian_id
        )
        .filter(
            UserGuardianRelationship.user_id == user_id,
            UserGuardianRelationship.status == "PENDING"
        )
        .all()
    )

    result = []


    for relationship, guardian in requests:
        result.append(
    {
        "request_id": relationship.id,
        "guardian_id": guardian.id,
        "guardian_name": guardian.full_name,
        "guardian_email": guardian.email,
        "guardian_phone": guardian.phone_number,
        "guardian_safe_path_id": guardian.safe_path_id,
        "status": relationship.status
    }
)

    return result


def accept_request(
    db: Session,
    request_id: int,
    user_id: int
):
    request = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.id == request_id,
            UserGuardianRelationship.user_id == user_id,
            UserGuardianRelationship.status == "PENDING"
        )
        .first()
    )

    if not request:
        return None

    request.status = "ACCEPTED"
    request.accepted_at = datetime.utcnow()

    db.commit()
    db.refresh(request)

    return request


def reject_request(
    db: Session,
    request_id: int,
    user_id: int
):
    request = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.id == request_id,
            UserGuardianRelationship.user_id == user_id,
            UserGuardianRelationship.status == "PENDING"
        )
        .first()
    )

    if not request:
        return None

    request.status = "REJECTED"

    db.commit()
    db.refresh(request)

    return request


def get_connected_users(
    db: Session,
    guardian_id:int
):

    users = (
        db.query(User)
        .join(
            UserGuardianRelationship,
            User.id == UserGuardianRelationship.user_id
        )
        .filter(
            UserGuardianRelationship.guardian_id == guardian_id,
            UserGuardianRelationship.status=="ACCEPTED"
        )
        .all()
    )


    result=[]


    for user in users:

        result.append({

            "id":user.id,

            "full_name":user.full_name,

            "email":user.email,

            "phone_number":user.phone_number,

            "safe_path_id":user.safe_path_id,

            "is_online":user.is_online,

            "last_seen":user.last_seen

        })


    return result





def get_guardian_user_details(
    db: Session,
    guardian_id: int,
    user_id: int
):

    # Check connection exists
    relation = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == guardian_id,
            UserGuardianRelationship.user_id == user_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .first()
    )


    if not relation:
        return None



    # Get user
    user = (
        db.query(User)
        .filter(
            User.id == user_id
        )
        .first()
    )


    if not user:
        return None



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



    # Location history

    history = (
        db.query(Location)
        .filter(
            Location.user_id == user_id
        )
        .order_by(
            Location.timestamp.desc()
        )
        .limit(20)
        .all()
    )



    return {

        "user_id": user.id,

        "user_name": user.full_name,

        "safe_path_id": user.safe_path_id,


        "is_online": user.is_online,

        "last_seen": user.last_seen,


        "location": {

            "latitude":
                location.latitude if location else None,

            "longitude":
                location.longitude if location else None,

            "place":
                location.address if location else "Unknown"

        },


        "history":[

            {
                "latitude":item.latitude,

                "longitude":item.longitude,

                "place":item.address,

                "timestamp":item.timestamp

            }

            for item in history

        ]

    }



def get_guardian_movement_analytics(
    db: Session,
    guardian_id: int,
    filter: str = "weekly"
):

    relationships = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == guardian_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .all()
    )


    users = []

    daily_chart = {}
    weekly_chart = {}
    monthly_chart = {}
    yearly_chart = {}


    month_names = [
        "Jan", "Feb", "Mar", "Apr",
        "May", "Jun", "Jul", "Aug",
        "Sep", "Oct", "Nov", "Dec"
    ]


    now = datetime.now()


    # ================= DATE RANGE =================

    start = None
    end = None


    if filter == "daily":

        start = now.replace(
            hour=0,
            minute=0,
            second=0,
            microsecond=0
        )

        end = start + timedelta(days=1)



    elif filter == "weekly":

        start = now - timedelta(days=now.weekday())

        start = start.replace(
            hour=0,
            minute=0,
            second=0,
            microsecond=0
        )

        end = start + timedelta(days=7)



    elif filter == "monthly":

        start = now.replace(
            day=1,
            hour=0,
            minute=0,
            second=0,
            microsecond=0
        )

        last_day = calendar.monthrange(
            now.year,
            now.month
        )[1]

        end = start + timedelta(days=last_day)



    else:

        start = datetime(
            now.year,
            1,
            1
        )

        end = datetime(
            now.year + 1,
            1,
            1
        )



    # ================= LABELS =================


    daily_labels = [
        "00","02","04","06",
        "08","10","12","14",
        "16","18","20","22"
    ]


    weekly_labels = [
        "Mon",
        "Tue",
        "Wed",
        "Thu",
        "Fri",
        "Sat",
        "Sun"
    ]


    monthly_labels = [
        str(i)
        for i in range(
            1,
            calendar.monthrange(
                now.year,
                now.month
            )[1] + 1
        )
    ]


    yearly_labels = month_names



    # ================= USERS =================


    for relation in relationships:




        user = (
            db.query(User)
            .filter(
                User.id == relation.user_id
            )
            .first()
        )


        if not user:
            continue



        user_name = (
            user.full_name
            or
            user.email
        )


        users.append({

            "user_id": user.id,

            "user_name": user_name

        })



        locations = (
            db.query(Location)
            .filter(
                Location.user_id == user.id,
                Location.timestamp >= start,
                Location.timestamp < end
            )
            .order_by(
                Location.timestamp.asc()
            )
            .all()
        )



        for i in range(1, len(locations)):


            previous = locations[i-1]

            current = locations[i]


            distance = calculate_distance_km(
                previous.latitude,
                previous.longitude,
                current.latitude,
                current.longitude
            )


            ts = current.timestamp



            # DAILY

            hour = (ts.hour // 2) * 2

            daily_key = f"{hour:02d}"


            if daily_key not in daily_chart:

                daily_chart[daily_key] = {
                    "time": daily_key
                }


            daily_chart[daily_key][user_name] = round(
                daily_chart[daily_key].get(
                    user_name,
                    0
                )
                +
                distance,
                2
            )



            # WEEKLY

            week_key = ts.strftime("%a")


            if week_key not in weekly_chart:

                weekly_chart[week_key] = {
                    "time": week_key
                }


            weekly_chart[week_key][user_name] = round(
                weekly_chart[week_key].get(
                    user_name,
                    0
                )
                +
                distance,
                2
            )



            # MONTHLY

            month_key = str(ts.day)


            if month_key not in monthly_chart:

                monthly_chart[month_key] = {
                    "time": month_key
                }


            monthly_chart[month_key][user_name] = round(
                monthly_chart[month_key].get(
                    user_name,
                    0
                )
                +
                distance,
                2
            )



            # YEARLY

            year_key = ts.strftime("%b")


            if year_key not in yearly_chart:

                yearly_chart[year_key] = {
                    "time": year_key
                }


            yearly_chart[year_key][user_name] = round(
                yearly_chart[year_key].get(
                    user_name,
                    0
                )
                +
                distance,
                2
            )



    daily = []

    for label in daily_labels:

        if label in daily_chart:

            daily.append(daily_chart[label])

        else:

            item = {
                "time": label
            }

            for user in users:
                item[user["user_name"]] = 0

            daily.append(item)


    weekly = []

    for label in weekly_labels:

        if label in weekly_chart:

            weekly.append(weekly_chart[label])

        else:

            item = {
                "time": label
            }

            for user in users:
                item[user["user_name"]] = 0

            weekly.append(item)


    monthly = []

    for label in monthly_labels:

        if label in monthly_chart:

            monthly.append(monthly_chart[label])

        else:

            item = {
                "time": label
            }

            for user in users:
                item[user["user_name"]] = 0

            monthly.append(item)


    yearly = []

    for label in yearly_labels:

        if label in yearly_chart:

            yearly.append(yearly_chart[label])

        else:

            item = {
                "time": label
            }

            for user in users:
                item[user["user_name"]] = 0

            yearly.append(item)

    print("ANALYTICS RESULT USERS:", users)
    print("ANALYTICS DAILY:", daily)
    print("ANALYTICS WEEKLY:", weekly)
    print("ANALYTICS MONTHLY:", monthly)
    print("ANALYTICS YEARLY:", yearly)
    return {

        "users": users,

        "daily": daily,

        "weekly": weekly,

        "monthly": monthly,

        "yearly": yearly

    }


def calculate_distance_km(
    lat1,
    lon1,
    lat2,
    lon2
):

    R = 6371


    lat1 = radians(lat1)
    lon1 = radians(lon1)

    lat2 = radians(lat2)
    lon2 = radians(lon2)


    dlat = lat2 - lat1
    dlon = lon2 - lon1


    a = (
        sin(dlat / 2) ** 2
        +
        cos(lat1)
        *
        cos(lat2)
        *
        sin(dlon / 2) ** 2
    )


    c = 2 * atan2(
        sqrt(a),
        sqrt(1-a)
    )


    return round(R*c,2)







def get_live_map_users(
    db: Session,
    guardian_id: int
):

    relationships = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == guardian_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .all()
    )

    result = []

    for relation in relationships:
        
        print("PROCESSING USER:", relation.user_id)

        user = (
            db.query(User)
            .filter(User.id == relation.user_id)
            .first()
        )

        if not user:
            continue

        latest_location = (
            db.query(Location)
            .filter(Location.user_id == user.id)
            .order_by(Location.timestamp.desc())
            .first()
        )

        if not latest_location:
            continue

        result.append({
            "user_id": user.id,
            "user_name": user.full_name or user.email,
            "latitude": latest_location.latitude,
            "longitude": latest_location.longitude,
            "timestamp": latest_location.timestamp
        })

    return result