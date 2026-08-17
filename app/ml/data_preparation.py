import pandas as pd


def prepare_location_data(locations):

    df = pd.DataFrame(locations)


    # Convert timestamp
    df["timestamp"] = pd.to_datetime(
        df["timestamp"]
    )


    # Extract time features
    df["hour"] = (
        df["timestamp"]
        .dt.hour
    )


    # Sort user movement history

    df = df.sort_values(
        [
            "user_id",
            "timestamp"
        ]
    )


    # Create next location target

    df["next_latitude"] = (
        df.groupby("user_id")
        ["latitude"]
        .shift(-1)
    )


    df["next_longitude"] = (
        df.groupby("user_id")
        ["longitude"]
        .shift(-1)
    )


    # Remove last records without next location

    df = df.dropna()


    return df