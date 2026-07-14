from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user
from app.schemas.emergency_contact import EmergencyContactCreate
from app.services.emergency_service import (
    add_contact,
    get_contacts,
    delete_contact
)
router = APIRouter(
    prefix="/emergency",
    tags=["Emergency Contacts"]
) 


@router.post("/add")
def add_emergency_contact(
    contact: EmergencyContactCreate,
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    new_contact = add_contact(
        db,
        current_user,
        contact
    )

    return {
        "message": "Emergency contact added successfully",
        "contact_id": new_contact.id
    }


@router.get("/all")
def get_all_contacts(
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    contacts = get_contacts(db, current_user)

    return contacts


@router.delete("/{contact_id}")
def remove_contact(
    contact_id: int,
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):
    success = delete_contact(db, current_user, contact_id)

    if not success:
        return {"message": "Contact not found"}

    return {"message": "Contact deleted successfully"}