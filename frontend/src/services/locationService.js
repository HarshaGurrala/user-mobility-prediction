import api from "./api";

export const updateLocation = async (locationData) => {
  const response = await api.post("/location/update", locationData);
  return response.data;
};

export const getLatestLocation = async () => {
  const response = await api.get("/location/latest");
  return response.data;
};

export const getLocationHistory = async () => {
  const response = await api.get("/location/history");
  return response.data;
};