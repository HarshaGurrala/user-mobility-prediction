from fastapi import FastAPI

from app.database.database import Base, engine
from app.database import base

Base.metadata.create_all(bind=engine)

app = FastAPI(
    title="AI-Powered User Mobility Prediction API",
    version="1.0.0"
)


@app.get("/")
def home():
    return {
        "message": "Welcome to User Mobility Prediction API"
    }


@app.get("/health")
def health():
    return {
        "status": "Healthy"
    }