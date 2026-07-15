from sqlalchemy import Column, Integer, ForeignKey, DateTime
from sqlalchemy.sql import func

from app.database.database import Base


class GuardianLink(Base):
    __tablename__ = "guardian_links"

    id = Column(Integer, primary_key=True, index=True)

    guardian_user_id = Column(
        Integer,
        ForeignKey("users.id"),
        nullable=False
    )

    child_user_id = Column(
        Integer,
        ForeignKey("users.id"),
        nullable=False
    )

    linked_at = Column(
        DateTime(timezone=True),
        server_default=func.now()
    )