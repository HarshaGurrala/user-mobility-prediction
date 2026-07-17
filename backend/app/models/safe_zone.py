from sqlalchemy import Column, Integer, Float, String, ForeignKey
from sqlalchemy.orm import relationship

from app.database.database import Base

from sqlalchemy import Boolean
class SafeZone(Base):
    __tablename__ = "safe_zones"

    id = Column(Integer, primary_key=True, index=True)

    guardian_user_id = Column(
        Integer,
        ForeignKey("users.id"),
        nullable=False
    )

    name = Column(String(100), nullable=False)

    latitude = Column(Float, nullable=False)

    longitude = Column(Float, nullable=False)

    radius = Column(Float, nullable=False)

    guardian = relationship("User")

    

    is_inside = Column(Boolean, default=False)