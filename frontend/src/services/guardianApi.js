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


// ==================================================
// GUARDIAN USERS
// ==================================================

export const getGuardianUsers = async () => {

    const response = await API.get(
        "/guardian/connected-users"
    );

    return response.data;
};


// ==================================================
// GUARDIAN ALERTS
// ==================================================
export const getGuardianAlerts = async (guardianId) => {

    const response = await API.get(
        `/alert/guardian/${guardianId}/alerts`
    );

    return response.data;
};

// ==================================================
// GUARDIAN SAFETY
// ==================================================

export const getGuardianSafety = async (guardianId) => {

    const response = await API.get(
        `/guardian/${guardianId}/safety`
    );

    return response.data;
};


// ==================================================
// GUARDIAN MOVEMENT ANALYTICS
// ==================================================

export const getGuardianMovement = async (filter) => {

    const response = await API.get(
        `/guardian/movement-analytics?filter=${filter}`
    );

    return response.data;
};


// ==================================================
// SINGLE USER LIVE LOCATION
// ==================================================

export const getGuardianUserLiveLocation = async (userId) => {

    const response = await API.get(
        `/guardian/user/${userId}/live-location`
    );

    return response.data;
};


// ==================================================
// SINGLE USER DETAILS
// ==================================================

export const getGuardianUserDetails = async (userId) => {

    const response = await API.get(
        `/guardian/user/${userId}`
    );

    return response.data;
};



// ==================================================
// AI REPORT
// ==================================================

export const getGuardianAIReport = async (guardianId) => {

    const response = await API.get(
        `/guardian/${guardianId}/ai-report`
    );

    return response.data;
};


// ==================================================
// GUARDIAN REQUEST STATUS
// ==================================================

export const getGuardianRequestStatus = async () => {

    const response = await API.get(
        "/guardian/request-status"
    );

    return response.data;
};


// ==================================================
// GUARDIAN STATS
// ==================================================

export const getGuardianStats = async () => {

    const response = await API.get(
        "/guardian/stats"
    );

    return response.data;
};


// ==================================================
// ALL CONNECTED USERS LIVE MAP
// ==================================================

export const getGuardianMapUsers = async () => {

    const response = await API.get(
        "/guardian/live-map"
    );

    return response.data;
};


// ==================================================
// EMERGENCY CONTACTS
// ==================================================

export const addEmergencyContact = async (
    userId,
    contact
) => {

    const response = await API.post(
        `/emergency/guardian/${userId}`,
        contact
    );

    return response.data;
};


export const getEmergencyContacts = async (
    userId
) => {

    const response = await API.get(
        `/emergency/${userId}`
    );

    return response.data;
};


export const updateEmergencyContact = async (
    contactId,
    contact
) => {

    const response = await API.put(
        `/emergency/${contactId}`,
        contact
    );

    return response.data;
};


export const deleteEmergencyContact = async (
    contactId
) => {

    const response = await API.delete(
        `/emergency/${contactId}`
    );

    return response.data;
};


// ==================================================
// SELECTED USER MOVEMENT ANALYTICS
// ==================================================

export const getMovementAnalytics = async (
    userId,
    filter = "weekly"
) => {

    const response = await API.get(
        `/guardian/user/${userId}/movement-analytics`,
        {
            params: {
                filter: filter
            }
        }
    );

    return response.data;
};

// ==================================================
// SELECTED USER AI PREDICTION
// ==================================================

export const getNextPrediction = async (userId) => {
    const response = await API.get(
        `/prediction/next/${userId}`
    );

    return response.data;
};