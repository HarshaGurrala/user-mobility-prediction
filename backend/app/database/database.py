from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, declarative_base

from app.core.config import settings


from urllib.parse import quote_plus


password = quote_plus(settings.DB_PASSWORD)


DATABASE_URL = (
    f"mysql+pymysql://{settings.DB_USER}:"
    f"{password}@"
    f"{settings.DB_HOST}:"
    f"{settings.DB_PORT}/"
    f"{settings.DB_NAME}"
)

engine = create_engine(
    DATABASE_URL,
    echo=True
)


SessionLocal = sessionmaker(
    autocommit=False,
    autoflush=False,
    bind=engine
)


Base = declarative_base()



def get_db():

    db = SessionLocal()

    try:
        yield db

    finally:
        db.close()