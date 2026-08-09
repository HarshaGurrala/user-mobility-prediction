from datetime import datetime, timedelta

from fastapi import Depends, HTTPException, status
from fastapi.security import OAuth2PasswordBearer
from jose import JWTError, jwt
from passlib.context import CryptContext
from sqlalchemy.orm import Session

from app.core.config import settings
from app.database.database import get_db
from app.models.user import User


# ============================================================
# PASSWORD HASHING
# ============================================================

pwd_context = CryptContext(
    schemes=["bcrypt"],
    deprecated="auto"
)


# ============================================================
# OAUTH2 / JWT
# ============================================================

oauth2_scheme = OAuth2PasswordBearer(
    tokenUrl="/auth/token"
)


# ============================================================
# GET CURRENT AUTHENTICATED USER
# ============================================================

def get_current_user(
    token: str = Depends(oauth2_scheme),
    db: Session = Depends(get_db)
):
    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Could not validate credentials",
        headers={
            "WWW-Authenticate": "Bearer"
        }
    )

    try:
        # Decode JWT
        payload = jwt.decode(
            token,
            settings.SECRET_KEY,
            algorithms=[settings.ALGORITHM]
        )

        # Get user ID from JWT "sub"
        user_id = payload.get("sub")

        if user_id is None:
            raise credentials_exception

        # Convert JWT subject to integer user ID
        try:
            user_id = int(user_id)
        except (TypeError, ValueError):
            raise credentials_exception

    except JWTError:
        raise credentials_exception

    # Find user in database
    user = db.query(User).filter(
        User.id == user_id
    ).first()

    if user is None:
        raise credentials_exception

    return user


# ============================================================
# PASSWORD FUNCTIONS
# ============================================================

def hash_password(password: str):
    return pwd_context.hash(password)


def verify_password(
    password: str,
    hashed_password: str
):
    return pwd_context.verify(
        password,
        hashed_password
    )


# ============================================================
# CREATE ACCESS TOKEN
# ============================================================

def create_access_token(data: dict):

    payload = data.copy()

    expire = datetime.utcnow() + timedelta(
        hours=24
    )

    payload.update({
        "exp": expire
    })

    return jwt.encode(
        payload,
        settings.SECRET_KEY,
        algorithm=settings.ALGORITHM
    )