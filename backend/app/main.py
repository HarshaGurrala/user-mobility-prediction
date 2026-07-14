from fastapi import FastAPI

from app.api.auth import router as auth_router
from app.api.user import router as user_router
from app.api.emergency import router as emergency_router

from app.database.database import Base, engine
from app.database import base

from app.api.safe_location import router as safe_location_router

from app.api.location import router as location_router



Base.metadata.create_all(bind=engine)

app = FastAPI(
    title="AI-Powered User Mobility Prediction API",
    version="1.0.0"
)


app.include_router(auth_router)
app.include_router(emergency_router)
app.include_router(safe_location_router)
app.include_router(location_router)
# app.include_router(user_router)


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