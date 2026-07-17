// import { useEffect, useState } from "react";

// export default function useLocation() {

//   const [location, setLocation] = useState(null);

//   const [error, setError] = useState(null);

//   useEffect(() => {

//     if (!navigator.geolocation) {

//       setError("Geolocation is not supported.");

//       return;

//     }

//     const watchId = navigator.geolocation.watchPosition(

//       (position) => {

//         setLocation({

//           latitude: position.coords.latitude,

//           longitude: position.coords.longitude,

//           accuracy: position.coords.accuracy,

//           speed: position.coords.speed || 0,

//         });

//       },

//       (err) => {

//         setError(err.message);

//       },

//       {
//         enableHighAccuracy: true,
//         maximumAge: 0,
//         timeout: 10000,
//       }

//     );

//     return () => {

//       navigator.geolocation.clearWatch(watchId);

//     };

//   }, []);

//   return { location, error };

// }
import { useEffect, useState } from "react";
import { updateLocation } from "../services/locationService";

export default function useLocation() {
  const [location, setLocation] = useState(null);

  const [error, setError] = useState(null);

  useEffect(() => {
    if (!navigator.geolocation) {
      setError("Geolocation is not supported.");
      return;
    }

    const watchId = navigator.geolocation.watchPosition(
      async (position) => {
        const currentLocation = {
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
          accuracy: position.coords.accuracy,
          speed: position.coords.speed || 0,
        };
        if (position.coords.accuracy > 50) {
    return;
}

        setLocation(currentLocation);

        try {
          await updateLocation(currentLocation);
        } catch (err) {
          console.log("Location Upload Failed", err);
        }
      },

      (err) => {
        setError(err.message);
      },

      {
        enableHighAccuracy: true,
        maximumAge: 1000,
        timeout: 30000,
      }
    );

    return () => {
      navigator.geolocation.clearWatch(watchId);
    };
  }, []);

  return {
    location,
    error,
  };
}