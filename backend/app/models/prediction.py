from sqlalchemy import (
    Column,
    Integer,
    String,
    Float,
    DateTime,
    ForeignKey,
    Boolean
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

    predicted_latitude = Column(
        Float,
        nullable=True
    )

    predicted_longitude = Column(
        Float,
        nullable=True
    )

    actual_latitude = Column(
        Float,
        nullable=True
    )

    actual_longitude = Column(
        Float,
        nullable=True
    )

    confidence = Column(
        Float,
        nullable=False
    )

    prediction_accuracy = Column(
        Float,
        nullable=True
    )

    matched = Column(
        Boolean,
        default=False
    )

    created_at = Column(
        DateTime,
        server_default=func.now()
    )

    user = relationship(
        "User",
        backref="predictions"
    )