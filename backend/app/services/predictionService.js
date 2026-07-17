import API from "./locationService";

export const getPrediction = async () => {
  const response = await API.get(
    "/prediction/latest"
  );

  return response.data;
};