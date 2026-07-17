import joblib
from pathlib import Path

MODEL = joblib.load(
    Path(__file__).parent / "model.pkl"
)


def predict_destination(hour, day):

    prediction = MODEL.predict([[hour, day]])[0]

    confidence = (
        MODEL.predict_proba([[hour, day]]).max()
        * 100
    )

    latitude, longitude = prediction.split(",")

    return {
        "latitude": float(latitude),
        "longitude": float(longitude),
        "confidence": round(confidence, 2),
    }