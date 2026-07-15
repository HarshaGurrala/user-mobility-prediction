from sqlalchemy.orm import Session

from app.models.user import User
from app.schemas.user import UserCreate
from app.core.security import (
    hash_password,
    verify_password,
    create_access_token,
)
from app.utils.generate_guardian_code import generate_guardian_code
from app.models.guardian_link import GuardianLink

def register_user(db: Session, user: UserCreate):

    existing_user = (
        db.query(User)
        .filter(User.email == user.email)
        .first()
    )

    if existing_user:
        return None


    guardian = None
    guardian_code = None


    # If Guardian registers
    if user.role == "GUARDIAN":

        guardian_code = generate_guardian_code()


    # If Child registers
    elif user.role == "USER":

        if not user.guardian_code:
            return "Guardian Code Required"

        guardian = (
            db.query(User)
            .filter(
                User.guardian_code == user.guardian_code,
                User.role == "GUARDIAN"
            )
            .first()
        )

        if guardian is None:
            return "Invalid Guardian Code"


    new_user = User(

        full_name=user.full_name,

        email=user.email,

        phone_number=user.phone_number,

        password_hash=hash_password(user.password),

        role=user.role,

        guardian_code=guardian_code

    )

    db.add(new_user)
    db.commit()
    db.refresh(new_user)


    # Create Guardian Link
    if guardian:

        link = GuardianLink(

            guardian_user_id=guardian.id,

            child_user_id=new_user.id

        )

        db.add(link)
        db.commit()


    return new_user


def login_user(db: Session, email: str, password: str):

    user = db.query(User).filter(User.email == email).first()

    if user is None:
        return None

    if not verify_password(password, user.password_hash):
        return None

    token = create_access_token(
        data={"sub": user.email}
    )

    return {
        "access_token": token,
        "token_type": "bearer",
    }