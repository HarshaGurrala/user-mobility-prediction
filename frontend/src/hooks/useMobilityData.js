import { useCallback, useEffect, useState } from "react";

import {
  getCurrentLocation,
  getLocationHistory,
  getPrediction,
  getAlerts,
  getUserProfile,
} from "../services/locationService";

import {
    getMovementAnalytics
} from "../services/locationService";

import {
  getSafeLocations,
} from "../services/locationService";


const useMobilityData = (userId) => {

  const [safeLocations,setSafeLocations] = useState([]);

  const [location, setLocation] = useState(null);

  const [history, setHistory] = useState([]);

  const [prediction, setPrediction] = useState(null);

  const [alerts, setAlerts] = useState([]);

  const [user, setUser] = useState(null);



  const [loading, setLoading] = useState(true);

  const [error, setError] = useState(null);


const [movement, setMovement] = useState(null);

  const fetchDashboardData = useCallback(async () => {


    if (!userId) return;



    try {


      setLoading(true);

      setError(null);



      const [
  locationResponse,
  historyResponse,
  predictionResponse,
  alertsResponse,
  userResponse,
  safeLocationResponse

] = await Promise.all([


  getCurrentLocation(userId),
  getLocationHistory(userId),
  getPrediction(userId),
  getAlerts(userId),
  getUserProfile(userId)


]);







      setLocation(locationResponse);

      setHistory(historyResponse);

      setPrediction(predictionResponse);

      setAlerts(alertsResponse);

      setUser(userResponse);

      setSafeLocations(
  safeLocationResponse
);




    } catch (err) {


      console.error(
        "Dashboard data fetch failed:",
        err
      );


      setError(

        err.response?.data?.detail ||

        err.message ||

        "Failed to fetch dashboard data."

      );


    } finally {


      setLoading(false);


    }


  }, [userId]);





  useEffect(()=>{


    fetchDashboardData();


  },[fetchDashboardData]);





  return {


location,

history,

prediction,

alerts,

safeLocations,

user,

loading,

error,

refetch: fetchDashboardData,


};


};



export default useMobilityData;