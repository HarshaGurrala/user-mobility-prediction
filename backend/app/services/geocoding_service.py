import requests


def get_location_name(
    latitude: float,
    longitude: float
):

    url = "https://nominatim.openstreetmap.org/reverse"


    params = {
        "lat": latitude,
        "lon": longitude,
        "format": "json"
    }


    headers = {
        "User-Agent": "UserMobilityPrediction"
    }


    response = requests.get(
        url,
        params=params,
        headers=headers
    )


    if response.status_code != 200:
        return "Unknown Location"


    data = response.json()


    address = data.get(
        "display_name"
    )


    if address:
        return address


    return "Unknown Location"