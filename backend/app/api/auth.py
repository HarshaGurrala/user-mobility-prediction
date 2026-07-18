from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db

from app.schemas.user import (
    UserCreate,
    UserLogin,
    UserResponse
)

from app.services.auth_service import (
    create_user,
    authenticate_user
)


router = APIRouter(
    prefix="/auth",
    tags=["Authentication"]
)



@router.post(
    "/register",
    response_model=UserResponse
)
# def register(
#     user: UserCreate,
#     db: Session = Depends(get_db)
# ):

#     new_user = create_user(
#         db,
#         user
#     )


#     if new_user is None:

#         raise HTTPException(
#             status_code=400,
#             detail="Email already registered"
#         )


#     return new_user
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


@router.post("/login")
def login(
    user: UserLogin,
    db: Session = Depends(get_db)
):

    result = authenticate_user(
        db,
        user.email,
        user.password
    )

    if result is None:
        raise HTTPException(
            status_code=401,
            detail="Invalid email or password"
        )

    return result

