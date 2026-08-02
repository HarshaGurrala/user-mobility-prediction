from sqlalchemy import (
    Column,
    Integer,
    ForeignKey,
    String,
    DateTime
)

from sqlalchemy.sql import func
from sqlalchemy.orm import relationship

from app.database.database import Base



class UserGuardian(Base):

    __tablename__ = "user_guardian_relationship"


    id = Column(
        Integer,
        primary_key=True,
        index=True
    )


    child_id = Column(
        Integer,
        ForeignKey("users.id"),
        nullable=False
    )


    guardian_id = Column(
        Integer,
        ForeignKey("users.id"),
        nullable=False
    )


    status = Column(
        String(20),
        default="pending"
    )
    # pending
    # accepted
    # rejected



    created_at = Column(
        DateTime,
        server_default=func.now()
    )



    child = relationship(
        "User",
        foreign_keys=[child_id],
        backref="guardian_requests"
    )


    guardian = relationship(
        "User",
        foreign_keys=[guardian_id],
        backref="children_requests"
    )