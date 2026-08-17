import axios from "axios";

const API = axios.create({
    baseURL: "https://safepath-guardian.onrender.com"
});

API.interceptors.request.use(
    (config) => {

        const token = localStorage.getItem("token");

        if (token) {
            config.headers.Authorization =
                `Bearer ${token}`;
        }

        return config;
    },

    (error) => {
        return Promise.reject(error);
    }
);


// =====================================================
// GET SAFE LOCATIONS
// =====================================================

export const getSafeLocations = async (userId) => {

    const response = await API.get(
        `/safe-location/${userId}`
    );

    return response.data;
};


// =====================================================
// ADD SAFE LOCATION
// =====================================================

export const addSafeLocation = async (
    userId,
    location
) => {

    const response = await API.post(
        `/safe-location/guardian/${userId}`,
        location
    );

    return response.data;
};


// =====================================================
// UPDATE SAFE LOCATION
// =====================================================

export const updateSafeLocation = async (
    locationId,
    location
) => {

    const response = await API.put(
        `/safe-location/${locationId}`,
        location
    );

    return response.data;
};


// =====================================================
// DELETE SAFE LOCATION
// =====================================================

export const deleteSafeLocation = async (
    locationId
) => {

    const response = await API.delete(
        `/safe-location/${locationId}`
    );

    return response.data;
};


// =====================================================
// SEARCH SAFE LOCATION
// =====================================================

export const searchSafeLocation = async (
    query
) => {

    const response = await API.get(
        "/safe-location/search",
        {
            params: {
                q: query
            }
        }
    );

    return response.data;
};


// =====================================================
// SEARCH SAFE LOCATION
// =====================================================


export const searchSafeLocationApi = async (
    query
) => {

    const response = await API.get(
        "/safe-location/search",
        {
            params: {
                q: query
            }
        }
    );

    return response.data;
};