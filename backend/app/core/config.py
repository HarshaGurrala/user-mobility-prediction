import os
from urllib.parse import quote_plus
from dotenv import load_dotenv

load_dotenv()


class Settings:
    PROJECT_NAME = "User Mobility Prediction"

    DB_HOST = os.getenv("DB_HOST", "localhost")
    DB_PORT = os.getenv("DB_PORT", "3306")
    DB_NAME = os.getenv("DB_NAME", "user_mobility_prediction")
    DB_USER = os.getenv("DB_USER", "root")

    # Encode special characters like @
    DB_PASSWORD = quote_plus(os.getenv("DB_PASSWORD", ""))

    DATABASE_URL = (
        f"mysql+pymysql://{DB_USER}:{DB_PASSWORD}"
        f"@{DB_HOST}:{DB_PORT}/{DB_NAME}"
    )


settings = Settings()