from sqlalchemy import (
    Column,
    Integer,
    String,
    Boolean,
    DateTime
)

from sqlalchemy.sql import func
from sqlalchemy.orm import relationship

from app.database.database import Base



class User(Base):

    __tablename__ = "users"


    id = Column(
        Integer,
        primary_key=True,
        index=True
    )


    full_name = Column(
        String(100),
        nullable=False
    )


    email = Column(
        String(100),
        unique=True,
        nullable=False,
        index=True
    )


    phone_number = Column(
        String(15),
        unique=True,
        nullable=True
    )


    password = Column(
        String(255),
        nullable=False
    )


    role = Column(
        String(20),
        default="USER"
    )
    # USER = child/person being tracked
    # GUARDIAN = person monitoring


    safe_path_id = Column(
        String(20),
        unique=True,
        nullable=False,
        index=True
    )


    is_active = Column(
        Boolean,
        default=True
    )


    created_at = Column(
        DateTime,
        server_default=func.now()
    )


    updated_at = Column(
        DateTime,
        server_default=func.now(),
        onupdate=func.now()
    )



    # User's emergency contacts

    emergency_contacts = relationship(
        "EmergencyContact",
        back_populates="user",
        cascade="all, delete"
    )



    # User location history

    locations = relationship(
        "Location",
        back_populates="user",
        cascade="all, delete"
    )


    # Safe locations

    safe_locations = relationship(
        "SafeLocation",
        back_populates="user",
        cascade="all, delete"
    )