from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.database.database import Base, engine
from app.database import base

from app.api.auth import router as auth_router
from app.api.emergency import router as emergency_router
from app.api.safe_location import router as safe_location_router
from app.api.location import router as location_router
from app.api.alert import router as alert_router
from app.api.prediction import router as prediction_router
from app.api.dashboard import router as dashboard_router
from app.api.users import router as users_router
from app.api.guardian import router as guardian_router
from app.api.safety_history import router as safety_history_router
from app.api.safety_status import router as safety_status_router
from app.api.analytics import router as analytics_router
app = FastAPI(
    title="User Mobility Prediction API",
    version="1.0.0"
)



# ============================================================
# CORS
# ============================================================

app.add_middleware(
    CORSMiddleware,
    allow_origins=[
        "http://localhost:5173",
        "http://127.0.0.1:5173",
    ],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


# ============================================================
# DATABASE TABLES
# ============================================================

Base.metadata.create_all(bind=engine)


# ============================================================
# ROUTERS
# ============================================================

app.include_router(auth_router)

app.include_router(emergency_router)

app.include_router(safe_location_router)

app.include_router(location_router)

app.include_router(alert_router)

app.include_router(prediction_router)

# Contains:
# /dashboard/{guardian_id}
# /user-dashboard/me
app.include_router(dashboard_router)

app.include_router(users_router)

# Guardian request/relationship APIs
app.include_router(guardian_router)

app.include_router(
    safety_history_router
)
app.include_router(safety_status_router)


app.include_router(analytics_router)

# ============================================================
# ROOT ENDPOINT
# ============================================================

@app.get("/")
def root():

    return {
        "message": "User Mobility Prediction API is running"
    }