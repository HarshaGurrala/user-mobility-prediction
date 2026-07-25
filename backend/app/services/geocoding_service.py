import requests

GEOAPIFY_API_KEY = "d4f50c38602a411fb0d8927e139ee705"


def get_location_name(
    latitude: float,
    longitude: float
):

    url = "https://api.geoapify.com/v1/geocode/reverse"

    params = {
        "lat": latitude,
        "lon": longitude,
        "apiKey": GEOAPIFY_API_KEY
    }

    try:

        response = requests.get(
            url,
            params=params,
            timeout=10
        )

        response.raise_for_status()

        data = response.json()

        print("Geoapify Response:", data)

        features = data.get("features", [])

        if features:

            properties = features[0].get("properties", {})

            address = properties.get("formatted")

            if address:
                return address

    except Exception as e:

        print("Geoapify Error:", e)

    return "Unknown Location"