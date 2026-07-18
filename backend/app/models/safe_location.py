from sqlalchemy import (
    Column,
    Integer,
    String,
    Float,
    DateTime,
    ForeignKey
)

from sqlalchemy.sql import func
from sqlalchemy.orm import relationship

from app.database.database import Base



class SafeLocation(Base):

    __tablename__ = "safe_locations"


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


    location_name = Column(
        String(100),
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


    radius = Column(
        Float,
        default=100
    )
    # meters


    created_at = Column(
        DateTime,
        server_default=func.now()
    )


    user = relationship(
        "User",
        back_populates="safe_locations"
    )