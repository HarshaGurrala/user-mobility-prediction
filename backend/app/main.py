# from fastapi import FastAPI

# from app.api.auth import router as auth_router
# from app.api.user import router as user_router
# from app.api.emergency import router as emergency_router

# from app.database.database import Base, engine
# from app.database import base

# from app.api.safe_location import router as safe_location_router

# from app.api.location import router as location_router

# from fastapi.middleware.cors import CORSMiddleware

# Base.metadata.create_all(bind=engine)

# app = FastAPI(
#     title="AI-Powered User Mobility Prediction API",
#     version="1.0.0"
# )


# app.include_router(auth_router)
# app.include_router(emergency_router)
# app.include_router(safe_location_router)
# app.include_router(location_router)
# # app.include_router(user_router)


# from fastapi.middleware.cors import CORSMiddleware

# app.add_middleware(
#     CORSMiddleware,
#     allow_origins=[
#         "http://localhost:5173",
#         "http://127.0.0.1:5173",
#     ],
#     allow_credentials=True,
#     allow_methods=["*"],
#     allow_headers=["*"],
# )
# @app.get("/health")
# def health():
#     return {
#         "status": "Healthy"
#     }





from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.api.prediction import router as prediction_router
from app.api.auth import router as auth_router
from app.api.user import router as user_router
from app.api.emergency import router as emergency_router
from app.api.safe_location import router as safe_location_router
from app.api.location import router as location_router

from app.database.database import Base, engine
from app.database import base 
from app.api.guardian import router as guardian_router
from app.api.alert import router as alert_router
Base.metadata.create_all(bind=engine)
from app.api.safe_zone import router as safe_zone_router

app = FastAPI(
    title="AI-Powered User Mobility Prediction API",
    version="1.0.0"
)


# CORS CONFIGURATION
app.add_middleware(
    CORSMiddleware,
    allow_origins=[
        "http://localhost:5173",
        "http://127.0.0.1:5173",
        # "*"
    ],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)



# ROUTERS

app.include_router(auth_router)
app.include_router(emergency_router)
app.include_router(safe_location_router)
app.include_router(location_router)
app.include_router(user_router)
app.include_router(prediction_router)
app.include_router(guardian_router)
app.include_router(safe_zone_router)
app.include_router(alert_router)

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