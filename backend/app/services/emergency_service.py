from sqlalchemy.orm import Session

from app.models.emergency_contact import EmergencyContact
from app.schemas.emergency_contact import EmergencyContactCreate


def add_contact(
    db: Session,
    user_id: int,
    contact: EmergencyContactCreate
):
    new_contact = EmergencyContact(
        user_id=user_id,
        name=contact.name,
        phone_number=contact.phone_number,
        email=contact.email,
       relationship_type=contact.relationship_type
    )

    db.add(new_contact)
    db.commit()
    db.refresh(new_contact)

    return new_contact


def get_contacts(
    db: Session,
    user_id: int
):
    return db.query(EmergencyContact).filter(
        EmergencyContact.user_id == user_id
    ).all()