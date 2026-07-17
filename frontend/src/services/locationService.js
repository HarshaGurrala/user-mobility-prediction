// import api from "./api";

// export const updateLocation = async (locationData) => {
//   const response = await api.post("/location/update", locationData);
//   return response.data;
// };

// export const getLatestLocation = async () => {
//   const response = await api.get("/location/latest");
//   return response.data;
// };

// export const getLocationHistory = async () => {
//   const response = await api.get("/location/history");
//   return response.data;
// };

import axios from "axios";

const API = axios.create({
  baseURL: "http://127.0.0.1:8000",
});

API.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default API;

// Guardian
export const getChildLocation = async (childId) => {
  const response = await API.get(
    `/guardian/child/${childId}/location`
  );

  return response.data;
};

// Child
export const updateLocation = async (location) => {
  const response = await API.post(
    "/location/update",
    location
  );

  return response.data;
};

// Child
export const getMyLocation = async () => {
  const response = await API.get(
    "/location/latest"
  );

  return response.data;
};