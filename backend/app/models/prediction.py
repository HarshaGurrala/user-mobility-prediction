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


class Prediction(Base):

    __tablename__ = "predictions"


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


    predicted_location = Column(
        String(255),
        nullable=False
    )


    confidence = Column(
        Float,
        nullable=False
    )


    created_at = Column(
        DateTime,
        server_default=func.now()
    )


    user = relationship(
        "User",
        backref="predictions"
    )