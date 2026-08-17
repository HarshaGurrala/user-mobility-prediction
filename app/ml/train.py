import pandas as pd

from sklearn.ensemble import RandomForestRegressor

import joblib



def train_location_model(data):

    df = pd.DataFrame(data)


    df["timestamp"] = pd.to_datetime(
        df["timestamp"]
    )


    df["hour"] = (
        df["timestamp"]
        .dt.hour
    )


    df = df.sort_values(
        "timestamp"
    )


    # Next location target

    df["next_latitude"] = (
        df["latitude"]
        .shift(-1)
    )


    df["next_longitude"] = (
        df["longitude"]
        .shift(-1)
    )


    df = df.dropna()


    X = df[
        [
            "latitude",
            "longitude",
            "hour"
        ]
    ]


    y_lat = df["next_latitude"]

    y_lon = df["next_longitude"]


    lat_model = RandomForestRegressor(
        n_estimators=100,
        random_state=42
    )


    lon_model = RandomForestRegressor(
        n_estimators=100,
        random_state=42
    )


    lat_model.fit(
        X,
        y_lat
    )


    lon_model.fit(
        X,
        y_lon
    )


    joblib.dump(
        lat_model,
        "app/ml/lat_model.pkl"
    )


    joblib.dump(
        lon_model,
        "app/ml/lon_model.pkl"
    )


    return True