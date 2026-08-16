import secrets
from datetime import datetime, timedelta, timezone

from sqlalchemy.orm import Session

from app.models.user import User
from app.models.password_reset import PasswordResetToken
from app.utils.email import send_reset_email

from app.core.security import hash_password


def create_password_reset(
    db: Session,
    email: str
):
    # Find user
    user = (
        db.query(User)
        .filter(User.email == email)
        .first()
    )

    # Don't reveal whether the email exists
    if not user:
        return True

    # Generate secure token
    token = secrets.token_urlsafe(32)

    # Token valid for 15 minutes
    expires_at = (
    datetime.now(timezone.utc).replace(tzinfo=None)
    + timedelta(minutes=15)
)

    # Remove previous reset tokens
    db.query(PasswordResetToken).filter(
        PasswordResetToken.user_id == user.id
    ).delete()

    reset_token = PasswordResetToken(
        user_id=user.id,
        token=token,
        expires_at=expires_at
    )

    db.add(reset_token)
    db.commit()

    # Your frontend/reset page URL
    reset_link = (
        "https://user-mobility-prediction.onrender.com"
        "/auth/reset-password-link"
        f"?token={token}"
    )

    send_reset_email(
        recipient_email=user.email,
        reset_link=reset_link
    )

    return True





def reset_password(
    db: Session,
    token: str,
    new_password: str
):
    # Find reset token
    reset_token = (
        db.query(PasswordResetToken)
        .filter(
            PasswordResetToken.token == token
        )
        .first()
    )

    # Invalid token
    if not reset_token:
        return False, "Invalid reset token"

    # Check expiration
    if reset_token.expires_at < datetime.now(timezone.utc).replace(tzinfo=None):
        db.delete(reset_token)
        db.commit()

        return False, "Reset token has expired"

    # Find user
    user = (
        db.query(User)
        .filter(
            User.id == reset_token.user_id
        )
        .first()
    )

    if not user:
        return False, "User not found"

    # Hash new password
    user.password = hash_password(new_password)

    # Delete token so it can only be used once
    db.delete(reset_token)

    db.commit()

    return True, "Password reset successfully"