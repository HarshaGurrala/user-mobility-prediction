from sqlalchemy.orm import Session

from app.models.user import User
from app.schemas.user import UserCreate
from app.core.security import hash_password
from app.core.security import verify_password, create_access_token

def register_user(db: Session, user: UserCreate):

    # Check if email already exists
    existing_user = db.query(User).filter(User.email == user.email).first()

    if existing_user:
        return None

    new_user = User(
    full_name=user.full_name,
    email=user.email,
    phone_number=user.phone_number,
    password_hash=hash_password(user.password)
)

    db.add(new_user)
    db.commit()
    db.refresh(new_user)

    return new_user



def login_user(db: Session, email: str, password: str):

    user = db.query(User).filter(User.email == email).first()

    if user is None:
        return None

    if not verify_password(password, user.password_hash):
        return None

    token = create_access_token(
        data={
            "sub": user.email
        }
    )

    return {
        "access_token": token,
        "token_type": "bearer"
    }

def get_contacts(db: Session, current_user):

    contacts = (
        db.query(EmergencyContact)
        .filter(EmergencyContact.user_id == current_user.id)
        .all()
    )

    return contacts