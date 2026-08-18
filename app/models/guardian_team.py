from sqlalchemy import (
    Column,
    Integer,
    String,
    Text,
    DateTime,
    ForeignKey,
)

from sqlalchemy.sql import func

from app.database.database import Base


class GuardianTeam(Base):

    __tablename__ = "guardian_team"

    id = Column(
        Integer,
        primary_key=True,
        index=True
    )

    guardian_id = Column(
        Integer,
        ForeignKey("users.id", ondelete="CASCADE"),
        nullable=False,
        index=True
    )

    person_type = Column(
        String(20),
        nullable=False
    )
    # HOD
    # MENTOR
    # MEMBER

    roll_no = Column(
        String(50),
        nullable=True
    )

    name = Column(
        String(100),
        nullable=False
    )

    email = Column(
        String(100),
        nullable=True
    )

    role = Column(
        String(100),
        nullable=True
    )

    details = Column(
        Text,
        nullable=True
    )

   

    image = Column(
        String(255),
        nullable=True
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