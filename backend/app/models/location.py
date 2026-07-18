from sqlalchemy import (
    Column,
    Integer,
    Float,
    DateTime,
    ForeignKey
)

from sqlalchemy.sql import func
from sqlalchemy.orm import relationship

from app.database.database import Base



class Location(Base):

    __tablename__ = "locations"


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


    accuracy = Column(
        Float,
        nullable=True
    )


    timestamp = Column(
        DateTime,
        server_default=func.now()
    )


    user = relationship(
        "User",
        back_populates="locations"
    )