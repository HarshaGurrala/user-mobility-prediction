from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session

from app.database.database import get_db

from app.schemas.emergency_contact import (
    EmergencyContactCreate,
    EmergencyContactResponse
)

from app.services.emergency_service import (
    add_contact,
    get_contacts
)

router = APIRouter(
    prefix="/emergency",
    tags=["Emergency Contacts"]
)


@router.post("/{user_id}")
def create_contact(
    user_id: int,
    contact: EmergencyContactCreate,
    db: Session = Depends(get_db)
):
    return add_contact(
        db,
        user_id,
        contact
    )


@router.get("/{user_id}")
def list_contacts(
    user_id: int,
    db: Session = Depends(get_db)
):
    return get_contacts(
        db,
        user_id
    )