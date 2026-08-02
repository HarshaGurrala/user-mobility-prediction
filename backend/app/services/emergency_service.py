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



def update_contact(
    db: Session,
    contact_id: int,
    contact_data: EmergencyContactCreate
):

    contact = db.query(EmergencyContact).filter(
        EmergencyContact.id == contact_id
    ).first()


    if not contact:
        return None


    contact.name = contact_data.name
    contact.phone_number = contact_data.phone_number
    contact.email = contact_data.email
    contact.relationship_type = contact_data.relationship_type


    db.commit()
    db.refresh(contact)

    return contact



def delete_contact(
    db: Session,
    contact_id: int
):

    contact = db.query(EmergencyContact).filter(
        EmergencyContact.id == contact_id
    ).first()


    if not contact:
        return False


    db.delete(contact)
    db.commit()

    return True