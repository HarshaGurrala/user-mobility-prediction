# from fastapi import APIRouter, Depends
# from sqlalchemy.orm import Session

# from app.database.database import get_db
# from app.dependencies.auth import get_current_user

# from app.services.ai_prediction_service import generate_prediction
# from app.services.prediction_service import get_latest_prediction

# router = APIRouter(
#     prefix="/prediction",
#     tags=["AI Prediction"]
# )


# @router.post("/generate")
# def generate(
#     db: Session = Depends(get_db),
#     current_user=Depends(get_current_user)
# ):

#     return generate_prediction(
#         db,
#         current_user.id
#     )


# @router.get("/latest")
# def latest(
#     db: Session = Depends(get_db),
#     current_user=Depends(get_current_user)
# ):

#     return get_latest_prediction(
#         db,
#         current_user.id
#     )
from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.database.database import get_db
from app.dependencies.auth import get_current_user

from app.services.smart_prediction_service import smart_prediction

router = APIRouter(
    prefix="/prediction",
    tags=["AI Prediction"]
)


@router.get("/latest")
def latest_prediction(
    db: Session = Depends(get_db),
    current_user=Depends(get_current_user)
):

    prediction = smart_prediction(
        db,
        current_user.id
    )

    if prediction is None:

        raise HTTPException(
            status_code=404,
            detail="Not enough location history."
        )

    return prediction