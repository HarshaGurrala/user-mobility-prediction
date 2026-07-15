import { useEffect, useState } from "react";

export default function useLocation() {

  const [location, setLocation] = useState(null);

  const [error, setError] = useState(null);

  useEffect(() => {

    if (!navigator.geolocation) {

      setError("Geolocation is not supported.");

      return;

    }

    const watchId = navigator.geolocation.watchPosition(

      (position) => {

        setLocation({

          latitude: position.coords.latitude,

          longitude: position.coords.longitude,

          accuracy: position.coords.accuracy,

          speed: position.coords.speed || 0,

        });

      },

      (err) => {

        setError(err.message);

      },

      {
        enableHighAccuracy: true,
        maximumAge: 0,
        timeout: 10000,
      }

    );

    return () => {

      navigator.geolocation.clearWatch(watchId);

    };

  }, []);

  return { location, error };

}