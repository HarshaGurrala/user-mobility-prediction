from sqlalchemy import (
    Boolean,
    Column,
    DateTime,
    Integer,
    String,
    ForeignKey
)

from sqlalchemy.sql import func
from sqlalchemy.orm import relationship

from app.database.database import Base





class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)

    full_name = Column(String(100), nullable=False)

    email = Column(
        String(100),
        unique=True,
        nullable=False,
        index=True
    )

    phone_number = Column(
        String(20),
        unique=True,
        nullable=False
    )

    password_hash = Column(
    String(255),
    nullable=False
)

    # USER / GUARDIAN / ADMIN
    role = Column(
    String(20),
    default="USER",
    nullable=False
)

    guardian_code = Column(
    String(20),
    unique=True,
    nullable=True
)

    is_active = Column(
        Boolean,
        default=True
    )

    created_at = Column(
        DateTime(timezone=True),
        server_default=func.now()
    )

    updated_at = Column(
        DateTime(timezone=True),
        server_default=func.now(),
        onupdate=func.now()
    )

 