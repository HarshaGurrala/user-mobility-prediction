from sqlalchemy import (
    Column,
    Integer,
    String,
    DateTime,
    ForeignKey,
    Boolean
)

from sqlalchemy.sql import func

from app.database.database import Base


class Alert(Base):

    __tablename__ = "alerts"


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


    guardian_id = Column(
        Integer,
        ForeignKey("users.id"),
        nullable=False
    )


    alert_type = Column(
        String(50),
        nullable=False
    )


    message = Column(
        String(255),
        nullable=False
    )


    status = Column(
        String(20),
        default="unread"
    )


    created_at = Column(
        DateTime,
        server_default=func.now()
    )

    is_read = Column(
    Boolean,
    default=False
)