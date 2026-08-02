from datetime import datetime

from sqlalchemy.orm import Session

from app.models.user import User
from app.models.user_guardian_relationship import UserGuardianRelationship

from app.services.online_status_service import check_user_online_status

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




def get_guardian_movement_analytics(
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

    users = []

    daily_chart = defaultdict(dict)
    weekly_chart = defaultdict(dict)
    monthly_chart = defaultdict(dict)
    yearly_chart = defaultdict(dict)

    month_names = [
        "Jan", "Feb", "Mar", "Apr",
        "May", "Jun", "Jul", "Aug",
        "Sep", "Oct", "Nov", "Dec"
    ]

    for relation in relationships:

        user = (
            db.query(User)
            .filter(User.id == relation.user_id)
            .first()
        )

        if not user:
            continue

        user_name = user.full_name or user.email

        users.append({
            "user_id": user.id,
            "user_name": user_name
        })

        locations = (
            db.query(Location)
            .filter(Location.user_id == user.id)
            .order_by(Location.timestamp.asc())
            .all()
        )

        for i in range(1, len(locations)):

            previous = locations[i - 1]
            current = locations[i]

            distance = calculate_distance_km(
                previous.latitude,
                previous.longitude,
                current.latitude,
                current.longitude
            )

            ts = current.timestamp

            # ---------------- DAILY ----------------

            day = ts.strftime("%a")

            if "time" not in daily_chart[day]:
                daily_chart[day]["time"] = day

            daily_chart[day][user_name] = round(
                daily_chart[day].get(user_name, 0) + distance,
                2
            )

            # ---------------- WEEKLY ----------------

            week_of_month = ((ts.day - 1) // 7) + 1

            week_label = f"Week {week_of_month}"

            if "time" not in weekly_chart[week_label]:
                weekly_chart[week_label]["time"] = week_label

            weekly_chart[week_label][user_name] = round(
                weekly_chart[week_label].get(user_name, 0) + distance,
                2
            )

            # ---------------- MONTHLY ----------------

            month_label = month_names[ts.month - 1]

            if "time" not in monthly_chart[month_label]:
                monthly_chart[month_label]["time"] = month_label

            monthly_chart[month_label][user_name] = round(
                monthly_chart[month_label].get(user_name, 0) + distance,
                2
            )

            # ---------------- YEARLY ----------------

            year_label = str(ts.year)

            if "time" not in yearly_chart[year_label]:
                yearly_chart[year_label]["time"] = year_label

            yearly_chart[year_label][user_name] = round(
                yearly_chart[year_label].get(user_name, 0) + distance,
                2
            )

    daily_order = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]

    daily = [
        daily_chart[d]
        for d in daily_order
        if d in daily_chart
    ]

    weekly = sorted(
        weekly_chart.values(),
        key=lambda x: int(x["time"].split(" ")[1])
    )

    monthly = [
        monthly_chart[m]
        for m in month_names
        if m in monthly_chart
    ]

    yearly = sorted(
        yearly_chart.values(),
        key=lambda x: int(x["time"])
    )

    return {
        "users": users,
        "daily": daily,
        "weekly": weekly,
        "monthly": monthly,
        "yearly": yearly
    }
   

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


def get_guardian_movement_analytics(
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


        user = (
            db.query(User)
            .filter(
                User.id == relation.user_id
            )
            .first()
        )


        if not user:
            continue



        locations = (
            db.query(Location)
            .filter(
                Location.user_id == user.id
            )
            .order_by(
                Location.timestamp.asc()
            )
            .all()
        )


        days = {
            "Mon":0,
            "Tue":0,
            "Wed":0,
            "Thu":0,
            "Fri":0,
            "Sat":0,
            "Sun":0
        }



        for i in range(
            1,
            len(locations)
        ):


            previous = locations[i-1]

            current = locations[i]


            distance = calculate_distance_km(
                previous.latitude,
                previous.longitude,
                current.latitude,
                current.longitude
            )


            day = current.timestamp.strftime("%a")


            if day in days:

                days[day] += distance



        result.append({

            "user_id": user.id,

            "user_name": user.full_name,

            "weekly_distance":
                round(sum(days.values()),2),

            "movement": [

                {
                    "day":key,
                    "distance":round(value,2)
                }

                for key,value in days.items()

            ]

        })


    return result




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


        user = (
            db.query(User)
            .filter(
                User.id == relation.user_id
            )
            .first()
        )


        if not user:
            continue



        location = (
            db.query(Location)
            .filter(
                Location.user_id == user.id
            )
            .order_by(
                Location.timestamp.desc()
            )
            .first()
        )


        result.append({

            "user_id": user.id,

            "user_name":
                user.full_name or user.email,


            "is_online":
                user.is_online,


            "last_seen":
                user.last_seen,


            "latitude":
                location.latitude if location else None,


            "longitude":
                location.longitude if location else None,


            "place":
                location.address if location else "Unknown",


            "updated_at":
                location.timestamp if location else None

        })


    return result