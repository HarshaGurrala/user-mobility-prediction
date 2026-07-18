import { useCallback, useEffect, useState } from "react";

import {
  getCurrentLocation,
  getLocationHistory,
  getPrediction,
  getAlerts,
} from "../services/locationService";

const useMobilityData = (userId) => {
  const [location, setLocation] = useState(null);
  const [history, setHistory] = useState([]);
  const [prediction, setPrediction] = useState(null);
  const [alerts, setAlerts] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

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
      ] = await Promise.all([
        getCurrentLocation(userId),
        getLocationHistory(userId),
        getPrediction(userId),
        getAlerts(userId),
      ]);

      setLocation(locationResponse);
      setHistory(historyResponse);
      setPrediction(predictionResponse);
      setAlerts(alertsResponse);
    } catch (err) {
      console.error("Dashboard data fetch failed:", err);

      setError(
        err.response?.data?.detail ||
          err.message ||
          "Failed to fetch dashboard data."
      );
    } finally {
      setLoading(false);
    }
  }, [userId]);

  useEffect(() => {
    fetchDashboardData();
  }, [fetchDashboardData]);

  return {
    location,
    history,
    prediction,
    alerts,
    loading,
    error,
    refetch: fetchDashboardData,
  };
};

export default useMobilityData;