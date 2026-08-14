from pydantic_settings import BaseSettings


class Settings(BaseSettings):

    DB_HOST: str = "localhost"
    DB_PORT: int = 3306
    DB_NAME: str = "user_mobility_prediction"
    DB_USER: str = "root"
    DB_PASSWORD: str = ""

    SECRET_KEY: str = "your_secret_key"
    ALGORITHM: str = "HS256"

    SMTP_HOST: str = "smtp.gmail.com"
    SMTP_PORT: int = 587
    SMTP_USERNAME: str = ""
    SMTP_PASSWORD: str = ""
    SMTP_FROM_EMAIL: str = ""


    # TWILIO_ACCOUNT_SID: "REMOVED_TWILIO_ACCOUNT_SID"
    # TWILIO_AUTH_TOKEN: "REMOVED_TWILIO_AUTH_TOKEN"
    # TWILIO_PHONE_NUMBER: "REMOVED_TWILIO_PHONE_NUMBER"


    class Config:
        env_file = ".env"


settings = Settings()