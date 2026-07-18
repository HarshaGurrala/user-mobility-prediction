import os
import joblib
import pandas as pd


LAT_MODEL_PATH = "app/ml/lat_model.pkl"
LON_MODEL_PATH = "app/ml/lon_model.pkl"



def predict_next_location(
    latitude,
    longitude,
    hour
):

    if not os.path.exists(LAT_MODEL_PATH) or not os.path.exists(LON_MODEL_PATH):

        raise FileNotFoundError(
            "Model not trained. Train the model first."
        )


    lat_model = joblib.load(
        LAT_MODEL_PATH
    )


    lon_model = joblib.load(
        LON_MODEL_PATH
    )


    data = pd.DataFrame(
        [
            {
                "latitude": latitude,
                "longitude": longitude,
                "hour": hour
            }
        ]
    )


    next_lat = lat_model.predict(
        data
    )[0]


    next_lon = lon_model.predict(
        data
    )[0]


    # Model prediction confidence
    lat_error = abs(
        next_lat - latitude
    )

    lon_error = abs(
        next_lon - longitude
    )


    error = lat_error + lon_error


    confidence = max(
        0,
        100 - (error * 10000)
    )


    return {

        "latitude": round(next_lat, 6),

        "longitude": round(next_lon, 6),

        "confidence": round(confidence, 2)

    }