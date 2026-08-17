from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.schemas.password_reset import ForgotPasswordRequest
from app.services.password_reset_service import create_password_reset
from app.schemas.password_reset import ResetPasswordRequest
from app.services.password_reset_service import reset_password
from fastapi.responses import RedirectResponse


from fastapi.security import OAuth2PasswordRequestForm

from app.schemas.user import (
    UserCreate,
    UserLogin,
    UserResponse,
    ChangePasswordRequest
)

from app.services.auth_service import (
    create_user,
    authenticate_user
)

from app.core.security import (
    get_current_user,
    verify_password,
    hash_password
)

from app.models.user import User


router = APIRouter(

    prefix="/auth",

    tags=["Authentication"]

)


# ============================================================
# REGISTER
# ============================================================

@router.post(
    "/register",
    response_model=UserResponse
)
def register(

    user: UserCreate,

    db: Session = Depends(get_db)

):
    

    new_user = create_user(

        db,

        user

    )

    if new_user is None:

        raise HTTPException(

            status_code=400,

            detail="Email already registered"

        )

    return new_user



# ============================================================
# GUARDIAN WEBSITE REGISTER
# ============================================================

@router.post(
    "/guardian-register",
    response_model=UserResponse
)
def guardian_register(

    user: UserCreate,

    db: Session = Depends(get_db)

):

    # Website accounts are ALWAYS GUARDIANS
    user.role = "GUARDIAN"

    new_user = create_user(
        db,
        user
    )

    if new_user is None:

        raise HTTPException(
            status_code=400,
            detail="Email already registered"
        )

    return new_user


# ============================================================
# LOGIN
# ============================================================

@router.post("/login")
def login(

    user: UserLogin,

    db: Session = Depends(get_db)

):

    result = authenticate_user(
    db,
    user.email,
    user.password,
    user.device_id
)

    if result is None:

        raise HTTPException(

            status_code=401,

            detail="Invalid email or password"

        )

    return result


# ============================================================
# SWAGGER LOGIN
# ============================================================

@router.post("/token")
def swagger_login(

    form_data: OAuth2PasswordRequestForm = Depends(),

    db: Session = Depends(get_db)

):

    result = authenticate_user(

        db,

        form_data.username,

        form_data.password,

        "swagger-device"
    )

    if result is None:

        raise HTTPException(

            status_code=401,

            detail="Invalid email or password"

        )

    return result


# ============================================================
# CHANGE PASSWORD
# ============================================================

@router.post("/change-password")
def change_password(

    password_data: ChangePasswordRequest,

    current_user: User = Depends(
        get_current_user
    ),

    db: Session = Depends(get_db)

):

    # --------------------------------------------------------
    # Verify current password
    # --------------------------------------------------------

    if not verify_password(

        password_data.current_password,

        current_user.password

    ):

        raise HTTPException(

            status_code=400,

            detail="Current password is incorrect"

        )


    # --------------------------------------------------------
    # Prevent using the same password
    # --------------------------------------------------------

    if verify_password(

        password_data.new_password,

        current_user.password

    ):

        raise HTTPException(

            status_code=400,

            detail="New password must be different from current password"

        )


    # --------------------------------------------------------
    # Hash new password
    # --------------------------------------------------------

    current_user.password = hash_password(

        password_data.new_password

    )


    # --------------------------------------------------------
    # Save new password
    # --------------------------------------------------------

    db.commit()

    db.refresh(current_user)


    return {

        "message":
            "Password changed successfully"

    }


@router.put(
    "/me/password"
)
def change_password(
    password_data: ChangePasswordRequest,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):

    if not verify_password(
        password_data.current_password,
        current_user.password
    ):
        raise HTTPException(
            status_code=400,
            detail="Current password is incorrect"
        )

    if password_data.current_password == password_data.new_password:
        raise HTTPException(
            status_code=400,
            detail="New password must be different from current password"
        )

    current_user.password = hash_password(
        password_data.new_password
    )

    db.commit()

    return {
        "message": "Password changed successfully"
    }


@router.post("/forgot-password")
def forgot_password(
    request: ForgotPasswordRequest,
    db: Session = Depends(get_db)
):

    create_password_reset(
        db=db,
        email=request.email
    )

    return {
        "message": (
            "If an account exists with this email, "
            "a password reset link has been sent."
        )
    }


# @router.put("/reset-password")
# def reset_password_endpoint(
#     request: ResetPasswordRequest,
#     db: Session = Depends(get_db)
# ):

#     success, message = reset_password(
#         db=db,
#         token=request.token,
#         new_password=request.new_password
#     )

#     if not success:
#         raise HTTPException(
#             status_code=400,
#             detail=message
#         )

#     return {
#         "message": message
#     }

@router.get("/reset-password-link")
def reset_password_link(token: str):

    return RedirectResponse(
        url=f"safepathai://reset-password?token={token}"
    )


# @router.put("/reset-password")
# def reset_password_endpoint(
#     request: ResetPasswordRequest,
#     db: Session = Depends(get_db)
# ):
#     print("RESET TOKEN RECEIVED:", request.token)
#     print("NEW PASSWORD RECEIVED:", request.new_password)

#     success, message = reset_password(
#         db=db,
#         token=request.token,
#         new_password=request.new_password
#     )

#     print("RESET RESULT:", success, message)

#     if not success:
#         raise HTTPException(
#             status_code=400,
#             detail=message
#         )

#     return {
#         "message": message
#     }


@router.put("/reset-password")
def reset_password_endpoint(
    request: ResetPasswordRequest,
    db: Session = Depends(get_db)
):
    print("========== RESET PASSWORD ==========")
    print("TOKEN:", request.token)
    print("NEW PASSWORD RECEIVED:", bool(request.new_password))

    success, message = reset_password(
        db=db,
        token=request.token,
        new_password=request.new_password
    )

    print("SUCCESS:", success)
    print("MESSAGE:", message)
    print("====================================")

    if not success:
        raise HTTPException(
            status_code=400,
            detail=message
        )

    return {
        "message": message
    }