from sqlalchemy import (
    Column,
    Integer,
    String,
    DateTime,
    ForeignKey
)

from sqlalchemy.orm import relationship
from sqlalchemy.sql import func

from app.database.database import Base


class UserGuardianRelationship(Base):

    __tablename__ = "user_guardian_relationship"

    id = Column(
        Integer,
        primary_key=True,
        index=True
    )

    guardian_id = Column(
        Integer,
        ForeignKey("users.id", ondelete="CASCADE"),
        nullable=False
    )

    user_id = Column(
        Integer,
        ForeignKey("users.id", ondelete="CASCADE"),
        nullable=False
    )

    status = Column(
        String(20),
        default="PENDING"
    )

    requested_at = Column(
        DateTime,
        server_default=func.now()
    )

    accepted_at = Column(
        DateTime,
        nullable=True
    )

    updated_at = Column(
        DateTime,
        server_default=func.now(),
        onupdate=func.now()
    )

    guardian = relationship(
        "User",
        foreign_keys=[guardian_id]
    )

    user = relationship(
        "User",
        foreign_keys=[user_id]
    )