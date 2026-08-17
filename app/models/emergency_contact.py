from sqlalchemy import (
    Column,
    Integer,
    String,
    ForeignKey,
    DateTime
)

from sqlalchemy.sql import func
from sqlalchemy.orm import relationship

from app.database.database import Base



class EmergencyContact(Base):

    __tablename__ = "emergency_contacts"


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


    name = Column(
        String(100),
        nullable=False
    )


    relationship_type = Column(
        String(50),
        nullable=True
    )


    phone_number = Column(
        String(15),
        nullable=False
    )


    email = Column(
        String(100),
        nullable=True
    )


    created_at = Column(
        DateTime,
        server_default=func.now()
    )


    user = relationship(
        "User",
        back_populates="emergency_contacts"
    )