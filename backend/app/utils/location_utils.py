from geopy.distance import geodesic


def calculate_distance(
    lat1,
    lon1,
    lat2,
    lon2
):

    point1 = (
        lat1,
        lon1
    )

    point2 = (
        lat2,
        lon2
    )


    distance = geodesic(
        point1,
        point2
    ).meters


    return distance