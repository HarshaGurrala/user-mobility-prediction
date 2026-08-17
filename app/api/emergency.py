
from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user

from app.models.user import User
from app.models.emergency_contact import EmergencyContact
from app.models.user_guardian_relationship import UserGuardianRelationship
from app.services.notification_service import create_notification
from app.schemas.sos import SOSRequest
from app.utils.sos_email import send_sos_email

from app.schemas.emergency_contact import (
    EmergencyContactCreate,
    EmergencyContactResponse,
    EmergencyContactUpdate
)

from app.models.alert import Alert
from app.models.notification import Notification
from app.schemas.alert import AlertCreate
from app.services.alert_service import create_alert

from app.services.emergency_service import (
    add_contact,
    get_contacts,
    update_contact,
    delete_contact
)

router = APIRouter(
    prefix="/emergency",
    tags=["Emergency Contacts"]
)


# ==========================================================
# GUARDIAN ADD EMERGENCY CONTACT FOR SELECTED USER
# ==========================================================

@router.post(
    "/guardian/{user_id}",
    response_model=EmergencyContactResponse
)
def guardian_add_emergency_contact(
    user_id: int,
    contact: EmergencyContactCreate,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian can add emergency contact"
        )

    user = (
        db.query(User)
        .filter(User.id == user_id)
        .first()
    )

    if not user:
        raise HTTPException(
            status_code=404,
            detail="User not found"
        )

    relationship = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == current_user.id,
            UserGuardianRelationship.user_id == user_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .first()
    )

    if not relationship:
        raise HTTPException(
            status_code=403,
            detail="User is not connected with this guardian"
        )

    return add_contact(
        db,
        user_id,
        contact
    )


# ==========================================================
# VIEW EMERGENCY CONTACTS
# ==========================================================

@router.get(
    "/{user_id}",
    response_model=list[EmergencyContactResponse]
)
def list_contacts(
    user_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    # User can view their own contacts
    if current_user.id == user_id:
        return get_contacts(
            db,
            user_id
        )

    # Guardian can view connected user's contacts
    if current_user.role == "GUARDIAN":

        relationship = (
            db.query(UserGuardianRelationship)
            .filter(
                UserGuardianRelationship.guardian_id == current_user.id,
                UserGuardianRelationship.user_id == user_id,
                UserGuardianRelationship.status == "ACCEPTED"
            )
            .first()
        )

        if not relationship:
            raise HTTPException(
                status_code=403,
                detail="User is not connected with this guardian"
            )

        return get_contacts(
            db,
            user_id
        )

    raise HTTPException(
        status_code=403,
        detail="Unauthorized"
    )


# ==========================================================
# GUARDIAN UPDATE EMERGENCY CONTACT
# ==========================================================

@router.put(
    "/{contact_id}",
    response_model=EmergencyContactResponse
)
def edit_contact(
    contact_id: int,
    contact: EmergencyContactUpdate,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian can update emergency contact"
        )

    emergency_contact = (
        db.query(EmergencyContact)
        .filter(
            EmergencyContact.id == contact_id
        )
        .first()
    )

    if not emergency_contact:
        raise HTTPException(
            status_code=404,
            detail="Emergency contact not found"
        )

    relationship = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == current_user.id,
            UserGuardianRelationship.user_id == emergency_contact.user_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .first()
    )

    if not relationship:
        raise HTTPException(
            status_code=403,
            detail="User is not connected with this guardian"
        )

    return update_contact(
        db,
        contact_id,
        contact
    )


# ==========================================================
# GUARDIAN DELETE EMERGENCY CONTACT
# ==========================================================

@router.delete(
    "/{contact_id}"
)
def remove_contact(
    contact_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian can delete emergency contact"
        )

    emergency_contact = (
        db.query(EmergencyContact)
        .filter(
            EmergencyContact.id == contact_id
        )
        .first()
    )

    if not emergency_contact:
        raise HTTPException(
            status_code=404,
            detail="Emergency contact not found"
        )

    relationship = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.guardian_id == current_user.id,
            UserGuardianRelationship.user_id == emergency_contact.user_id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .first()
    )

    if not relationship:
        raise HTTPException(
            status_code=403,
            detail="User is not connected with this guardian"
        )

    result = delete_contact(
        db,
        contact_id
    )

    return {
        "success": result
    }


# ==========================================================
# SOS EMERGENCY ALERT
# ==========================================================

@router.post("/sos")
def trigger_sos(
    request: SOSRequest,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    contacts = get_contacts(
        db,
        current_user.id
    )

    if not contacts:
        raise HTTPException(
            status_code=400,
            detail="No emergency contacts found"
        )


    # =====================================================
    # CREATE SOS NOTIFICATION FOR USER
    # =====================================================

    existing_notification = (
        db.query(Notification)
        .filter(
            Notification.user_id == current_user.id,
            Notification.notification_type == "SOS",
            Notification.status == "unread"
        )
        .first()
    )

    if not existing_notification:

        create_notification(
            db=db,
            user_id=current_user.id,
            notification_type="SOS",
            title="🚨 SOS Alert",
            message="Emergency SOS alert has been triggered."
        )


    # =====================================================
    # CREATE SOS ALERT FOR GUARDIANS
    # =====================================================

    guardians = (
        db.query(UserGuardianRelationship)
        .filter(
            UserGuardianRelationship.user_id == current_user.id,
            UserGuardianRelationship.status == "ACCEPTED"
        )
        .all()
    )

    for guardian in guardians:

        existing_alert = (
            db.query(Alert)
            .filter(
                Alert.user_id == current_user.id,
                Alert.guardian_id == guardian.guardian_id,
                Alert.alert_type == "SOS",
                Alert.status == "unread"
            )
            .first()
        )

        if existing_alert:
            continue

        alert = AlertCreate(
            user_id=current_user.id,
            guardian_id=guardian.guardian_id,
            alert_type="SOS",
            message="Emergency SOS alert has been triggered."
        )

        create_alert(
            db,
            alert
        )


    # =====================================================
    # SEND SOS EMAIL / SMS
    # =====================================================

    user_name = (
        getattr(current_user, "name", None)
        or getattr(current_user, "full_name", None)
        or current_user.email
    )

    email_sent = 0
    email_failed = 0

    sms_sent = 0
    sms_failed = 0

    for contact in contacts:

        # ==============================================
        # EMAIL
        # ==============================================

        if contact.email:

            try:

                send_sos_email(
                    recipient_email=contact.email,
                    user_name=user_name,
                    latitude=request.latitude,
                    longitude=request.longitude,
                    sos_message=request.message
                )

                email_sent += 1

            except Exception as e:

                print(
                    f"SOS email failed for "
                    f"{contact.email}: {e}"
                )

                email_failed += 1

        # ==============================================
        # SMS
        # ==============================================

        if contact.phone_number:

            try:

                phone = contact.phone_number.strip()

                # Convert Indian number to +91 format
                if phone.startswith("0"):
                    phone = "+91" + phone[1:]

                elif len(phone) == 10:
                    phone = "+91" + phone

                # send_sos_sms(
                #     recipient_phone=phone,
                #     user_name=user_name,
                #     latitude=request.latitude,
                #     longitude=request.longitude,
                #     sos_message=request.message
                # )

                sms_sent += 1

            except Exception as e:

                print(
                    f"SOS SMS failed for "
                    f"{contact.phone_number}: {e}"
                )

                sms_failed += 1


    if email_sent == 0 and sms_sent == 0:

        raise HTTPException(
            status_code=400,
            detail="No emergency contacts could be notified"
        )


    return {
        "success": True,
        "message": "SOS alert sent successfully",
        "email_sent": email_sent,
        "email_failed": email_failed,
        "sms_sent": sms_sent,
        "sms_failed": sms_failed
    }

