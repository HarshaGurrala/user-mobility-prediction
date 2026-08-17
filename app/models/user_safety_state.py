from sqlalchemy import (
    Column,
    Integer,
    String,
    DateTime,
    ForeignKey,
)

from sqlalchemy.sql import func

from app.database.database import Base


class UserSafetyState(Base):

    __tablename__ = "user_safety_state"


    id = Column(
        Integer,
        primary_key=True,
        index=True
    )


    user_id = Column(
        Integer,
        ForeignKey("users.id"),
        nullable=False,
        unique=True
    )


    status = Column(
        String(50),
        nullable=False,
        default="UNKNOWN"
    )


    safe_location_id = Column(
        Integer,
        ForeignKey("safe_locations.id"),
        nullable=True
    )


    location_name = Column(
        String(100),
        nullable=True
    )


    updated_at = Column(
        DateTime,
        server_default=func.now(),
        onupdate=func.now()
    )