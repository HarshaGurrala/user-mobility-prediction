from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session

from app.core.security import get_current_user
from app.database.database import get_db
from app.models.user import User
from app.schemas.user import UserResponse, UserUpdate
import os
import uuid

from fastapi import File, UploadFile


from fastapi import (
    APIRouter,
    Depends,
    HTTPException,
    status,
    File,
    UploadFile
)

router = APIRouter(
    prefix="/users",
    tags=["Users"]
)


@router.get(
    "/me",
    response_model=UserResponse
)
def get_my_profile(
    current_user: User = Depends(get_current_user)
):
    return current_user


@router.put(
    "/me",
    response_model=UserResponse
)
def update_my_profile(
    user_data: UserUpdate,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):

    # Check if another user already owns this email
    existing_user = (
        db.query(User)
        .filter(
            User.email == user_data.email,
            User.id != current_user.id
        )
        .first()
    )

    if existing_user:

        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Email address is already registered"
        )


    # Update allowed fields only

    current_user.full_name = user_data.full_name

    current_user.email = user_data.email

    current_user.phone_number = user_data.phone_number


    db.commit()

    db.refresh(current_user)


    return current_user


@router.post(
    "/me/profile-picture",
    response_model=UserResponse
)
def upload_profile_picture(
    profile_picture: UploadFile = File(...),
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):

    # -----------------------------------------
    # Validate image type
    # -----------------------------------------

    allowed_types = {
        "image/jpeg",
        "image/png",
        "image/webp"
    }

    if profile_picture.content_type not in allowed_types:

        raise HTTPException(
            status_code=400,
            detail="Only JPG, PNG and WEBP images are allowed"
        )


    # -----------------------------------------
    # Create upload directory
    # -----------------------------------------

    upload_dir = "uploads/profile_pictures"

    os.makedirs(
        upload_dir,
        exist_ok=True
    )


    # -----------------------------------------
    # Generate unique filename
    # -----------------------------------------

    extension = os.path.splitext(
        profile_picture.filename
    )[1]

    filename = (
        f"{current_user.id}_"
        f"{uuid.uuid4().hex}"
        f"{extension}"
    )

    file_path = os.path.join(
        upload_dir,
        filename
    )


    # -----------------------------------------
    # Save image
    # -----------------------------------------

    with open(
        file_path,
        "wb"
    ) as buffer:

        buffer.write(
            profile_picture.file.read()
        )


    # -----------------------------------------
    # Save path in database
    # -----------------------------------------

    current_user.profile_picture = (
        f"/uploads/profile_pictures/{filename}"
    )

    db.commit()

    db.refresh(
        current_user
    )


    return current_user


@router.get(
    "/{user_id}",
    response_model=UserResponse
)
def get_user_by_id(
    user_id: int,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

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


    return user


@router.get("/search/{safe_path_id}")
def search_user_by_safe_path_id(
    safe_path_id: str,
    db: Session = Depends(get_db),
    current_user: User = Depends(get_current_user)
):

    if current_user.role != "GUARDIAN":
        raise HTTPException(
            status_code=403,
            detail="Only guardian accounts can search users."
        )

    user = (
        db.query(User)
        .filter(
            User.safe_path_id == safe_path_id,
            User.role == "USER"
        )
        .first()
    )

    if not user:

        raise HTTPException(
            status_code=404,
            detail="User not found"
        )

    return {
        "id": user.id,
        "full_name": user.full_name,
        "email": user.email,
        "phone_number": user.phone_number,
        "safe_path_id": user.safe_path_id,
        "is_online": user.is_online
    }