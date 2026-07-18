from pydantic_settings import BaseSettings


class Settings(BaseSettings):

    DB_HOST: str = "localhost"
    DB_PORT: int = 3306
    DB_NAME: str = "user_mobility_prediction"
    DB_USER: str = "root"
    DB_PASSWORD: str = ""


    SECRET_KEY: str = "your_secret_key"
    ALGORITHM: str = "HS256"


    class Config:
        env_file = ".env"



settings = Settings()