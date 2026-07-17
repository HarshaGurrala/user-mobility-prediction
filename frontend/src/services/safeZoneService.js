import API from "./api";

export const createSafeZone = async (zone) => {

  const response = await API.post(
    "/safe-zone/",
    zone
  );

  return response.data;

};

export const getSafeZones = async () => {

  const response = await API.get(
    "/safe-zone/"
  );

  return response.data;

};