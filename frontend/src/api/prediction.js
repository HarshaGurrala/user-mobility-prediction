import api from "./axios";

export const getPrediction = () =>
    api.get("/prediction");

export const updateLocation = (data) =>
    api.post("/location/update", data);