from fastapi import FastAPI
from app.api.auth import router as auth_router
from app.database.database import Base, engine
from app.database import base

Base.metadata.create_all(bind=engine)

app = FastAPI(
    title="AI-Powered User Mobility Prediction API",
    version="1.0.0"
)
app.include_router(auth_router)

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