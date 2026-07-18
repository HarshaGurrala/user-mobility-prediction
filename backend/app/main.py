from fastapi import FastAPI

from app.database.database import Base, engine
from app.database import base

from app.api.auth import router as auth_router

app = FastAPI(
    title="User Mobility Prediction API",
    version="1.0.0"
)

Base.metadata.create_all(bind=engine)

app.include_router(auth_router)


@app.get("/")
def root():
    return {
        "message": "User Mobility Prediction API is running"
    }

