from sqlalchemy import (
    Column,
    Integer,
    String,
    Float,
    ForeignKey,
    DateTime,
    Boolean
)

from sqlalchemy.sql import func
from sqlalchemy.orm import relationship

from app.database.database import Base


class Alert(Base):

    __tablename__ = "alerts"

    id = Column(Integer, primary_key=True, index=True)

    guardian_id = Column(
        Integer,
        ForeignKey("users.id"),
        nullable=False
    )

    child_id = Column(
        Integer,
        ForeignKey("users.id"),
        nullable=False
    )

    title = Column(
        String(150),
        nullable=False
    )

    message = Column(
        String(500),
        nullable=False
    )

    alert_type = Column(
        String(50),
        nullable=False
    )

    latitude = Column(Float)

    longitude = Column(Float)

    is_read = Column(
        Boolean,
        default=False
    )

    created_at = Column(
        DateTime(timezone=True),
        server_default=func.now()
    )

    guardian = relationship(
        "User",
        foreign_keys=[guardian_id]
    )

    child = relationship(
        "User",
        foreign_keys=[child_id]
    )