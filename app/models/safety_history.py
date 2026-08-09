from sqlalchemy import (
    Column,
    Integer,
    String,
    Float,
    DateTime,
    ForeignKey,
)

from sqlalchemy.sql import func

from app.database.database import Base


class SafetyHistory(Base):

    __tablename__ = "safety_history"

    id = Column(
        Integer,
        primary_key=True,
        index=True
    )

    user_id = Column(
        Integer,
        ForeignKey("users.id"),
        nullable=False
    )

    safe_location_id = Column(
        Integer,
        ForeignKey("safe_locations.id"),
        nullable=True
    )

    event_type = Column(
        String(50),
        nullable=False
    )

    location_name = Column(
        String(100),
        nullable=True
    )

    latitude = Column(
        Float,
        nullable=False
    )

    longitude = Column(
        Float,
        nullable=False
    )

    address = Column(
        String(500),
        nullable=True
    )

    timestamp = Column(
        DateTime,
        server_default=func.now()
    )