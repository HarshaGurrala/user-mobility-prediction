from fastapi import HTTPException
from sqlalchemy.orm import Session

from app.models.user import User
from app.schemas.user import UserCreate

from app.core.security import (
    hash_password,
    verify_password,
    create_access_token,
)

from app.utils.id_generator import generate_safe_path_id




def create_user(
    db: Session,
    user_data: UserCreate
):
    print("========== CREATE USER ==========")
    print("Email:", user_data.email)
    print("REGISTER PASSWORD:", repr(user_data.password))
    print("Password Length:", len(user_data.password))

    # Check whether user already exists
    existing_user = db.query(User).filter(
        User.email == user_data.email
    ).first()

    if existing_user:
        print("User already exists")
        return None

    # Hash password
    hashed_password = hash_password(user_data.password)

    print("Hash created successfully")
    print("Generated Hash:", hashed_password)

    print(
        "Immediate Verify:",
        verify_password(
            user_data.password,
            hashed_password
        )
    )

    # Create user
    new_user = User(
        full_name=user_data.full_name,
        email=user_data.email,
        phone_number=user_data.phone_number,
        password=hashed_password,
        role=user_data.role.upper(),
        safe_path_id=generate_safe_path_id()
    )

    # Save user
    db.add(new_user)
    db.commit()
    db.refresh(new_user)

    print("User saved successfully")

    print("HASH AFTER SAVE:", new_user.password)

    print(
        "VERIFY AFTER SAVE:",
        verify_password(
            user_data.password,
            new_user.password
        )
    )

    return new_user


def authenticate_user(
    db: Session,
    email: str,
    password: str,
    device_id: str = None
):

    print("LOGIN PASSWORD:", repr(password))
    print("PASSWORD LENGTH:", len(password))

    # Find user by email
    user = db.query(User).filter(
        User.email == email
    ).first()

    if not user:

        print("User not found")

        return None

    print("HASH FROM DB:", user.password)
    print("HASH LENGTH:", len(user.password))

    # Verify password
    result = verify_password(
        password,
        user.password
    )

    print("VERIFY RESULT:", result)

    if not result:

        print("Password verification failed")

        return None

    print("Password verification successful")


    # ==================================================
    # ONE DEVICE LOGIN CHECK
    # ==================================================

# ==================================================
# GUARDIAN DEVICE ACCOUNT CHECK
# ONE GUARDIAN ACCOUNT PER DEVICE
# ==================================================

    if user.role == "GUARDIAN" and device_id is not None:

        existing_guardian = (
            db.query(User)
            .filter(
                User.device_id == device_id,
                User.role == "GUARDIAN",
                User.id != user.id
            )
            .first()
        )

        if existing_guardian:

            raise HTTPException(
                status_code=403,
                detail="This device is already associated with another Guardian account."
            )
    # ==================================================
# ONE DEVICE LOGIN CHECK
# ONLY FOR REAL ANDROID LOGIN
# ==================================================

    if (
        user.role == "USER"
        and device_id is not None
        and user.device_id is not None
        and user.device_id != device_id
    ):

        raise HTTPException(
            status_code=403,
            detail="This account is already logged in on another device."
        )



    # ==================================================
# SAVE LOGIN STATUS
# ==================================================

        # ==================================================
    # DEVICE LOGIN RULES
    # ==================================================

    if user.role == "USER":

        # --------------------------------------------------
        # USER: ONE DEVICE ONLY
        # --------------------------------------------------

        if (
            device_id is not None
            and user.device_id is not None
            and user.device_id != device_id
        ):

            raise HTTPException(
                status_code=403,
                detail="This account is already logged in on another device."
            )

        # Store device ID ONLY for USER
        user.device_id = device_id

        # USER is online
        user.is_online = True


    elif user.role == "GUARDIAN":

        # --------------------------------------------------
        # GUARDIAN: MULTIPLE DEVICES ALLOWED
        # --------------------------------------------------

        # Never store Guardian device ID
        user.device_id = None

        # Guardian is online
        user.is_online = True


    # Save login status
    db.commit()
    db.refresh(user)


  

        # ==================================================
    # CREATE JWT
    # ==================================================

    token = create_access_token(
        {
            "sub": str(user.id),
            "role": user.role
        }
    )

    return {
        "access_token": token,
        "token_type": "bearer",
        "user": user
    }