from sqlalchemy import (
    Column,
    Integer,
    String,
    Float,
    ForeignKey,
    DateTime
)

from sqlalchemy.sql import func
from sqlalchemy.orm import relationship

from app.database.database import Base


class LocationAlert(Base):

    __tablename__ = "location_alerts"

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

    latitude = Column(
        Float,
        nullable=False
    )

    longitude = Column(
        Float,
        nullable=False
    )

    location_name = Column(
        String(255),
        nullable=True
    )

    status = Column(
        String(30),
        nullable=False,
        default="UNKNOWN"
    )

    guardian_response = Column(
        String(30),
        nullable=True
    )

    created_at = Column(
        DateTime,
        server_default=func.now()
    )

    responded_at = Column(
        DateTime,
        nullable=True
    )

    user = relationship(
        "User"
    )