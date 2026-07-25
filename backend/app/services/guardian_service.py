from datetime import datetime

from sqlalchemy.orm import Session

from app.models.user import User
from app.models.user_guardian_relationship import UserGuardianRelationship


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
    guardian_id: int
):
    users = (
        db.query(User)
        .join(
            UserGuardianRelationship,
            User.id == UserGuardianRelationship.user_id
        )
        .filter(
            UserGuardianRelationship.guardian_id == guardian_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .all()
    )

    result = []

    for user in users:
        result.append(
            {
                "id": user.id,
                "full_name": user.full_name,
                "email": user.email,
                "phone_number": user.phone_number,
                "safe_path_id": user.safe_path_id
            }
        )

    return result