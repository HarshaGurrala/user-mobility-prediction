from sqlalchemy.orm import Session

from app.models.emergency_contact import EmergencyContact
from app.schemas.emergency_contact import EmergencyContactCreate


def add_contact(
    db: Session,
    current_user,
    contact: EmergencyContactCreate
):

    new_contact = EmergencyContact(
        user_id=current_user.id,
        name=contact.name,
        relationship_name=contact.relationship_name,
        phone_number=contact.phone_number
    )

    db.add(new_contact)
    db.commit()
    db.refresh(new_contact)

    return new_contact


def get_contacts(db: Session, current_user):

    return (
        db.query(EmergencyContact)
        .filter(EmergencyContact.user_id == current_user.id)
        .all()
    )


# def delete_contact(db: Session, current_user, contact_id: int):

#     contact = (
#         db.query(EmergencyContact)
#         .filter(
#             EmergencyContact.id == contact_id,
#             EmergencyContact.user_id == current_user.id
#         )
#         .first()
#     )

#     if contact is None:
#         return False

#     db.delete(contact)
#     db.commit()

#     return True

def delete_contact(db, current_user, contact_id):
    contact = (
        db.query(EmergencyContact)
        .filter(
            EmergencyContact.id == contact_id,
            EmergencyContact.user_id == current_user.id
        )
        .first()
    )

    if contact is None:
        return False

    db.delete(contact)
    db.commit()

    return True