from typing import Optional

from sqlalchemy.orm import Session

from app.models.guardian_team import GuardianTeam

def get_guardian_team(
    db: Session,
    guardian_id: int,
):
    return (
        db.query(GuardianTeam)
        .filter(
            GuardianTeam.guardian_id == guardian_id
        )
        .order_by(
            GuardianTeam.id
        )
        .all()
    )


def get_team_person(
    db: Session,
    guardian_id: int,
    team_id: int,
):
    return (
        db.query(GuardianTeam)
        .filter(
            GuardianTeam.id == team_id,
            GuardianTeam.guardian_id == guardian_id
        )
        .first()
    )


def create_team_person(
    db: Session,
    guardian_id: int,
    person_type: str,
    roll_no: Optional[str],
    name: str,
    email: Optional[str],
    role: Optional[str],
    details: Optional[str],
):
    team_person = GuardianTeam(
        guardian_id=guardian_id,
        person_type=person_type,
        roll_no=roll_no,
        name=name,
        email=email,
        role=role,
        details=details,
    )

    db.add(team_person)
    db.commit()
    db.refresh(team_person)

    return team_person


def update_team_person(
    db: Session,
    team_person: GuardianTeam,
    roll_no: Optional[str] = None,
    name: Optional[str] = None,
    email: Optional[str] = None,
    role: Optional[str] = None,
    details: Optional[str] = None,
):
    if roll_no is not None:
        team_person.roll_no = roll_no

    if name is not None:
        team_person.name = name

    if email is not None:
        team_person.email = email

    if role is not None:
        team_person.role = role

    if details is not None:
        team_person.details = details

    db.commit()
    db.refresh(team_person)

    return team_person


def delete_team_person(
    db: Session,
    team_person: GuardianTeam,
):
    db.delete(team_person)
    db.commit()

    return True
