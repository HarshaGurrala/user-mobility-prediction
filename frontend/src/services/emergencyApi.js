import axios from "axios";

const API = axios.create({
    baseURL: "https://safepath-guardian.onrender.com"
});

API.interceptors.request.use(
    (config) => {

        const token = localStorage.getItem("token");

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    },
    (error) => Promise.reject(error)
);


// =====================================================
// GET CONTACTS FOR SELECTED USER
// =====================================================

export const getEmergencyContacts = async (userId) => {

    const response = await API.get(
        `/emergency/${userId}`
    );

    return response.data;
};


// =====================================================
// GUARDIAN ADD CONTACT FOR USER
// =====================================================

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


// =====================================================
// GUARDIAN EDIT CONTACT
// =====================================================

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


// =====================================================
// GUARDIAN DELETE CONTACT
// =====================================================

export const deleteEmergencyContact = async (
    contactId
) => {

    const response = await API.delete(
        `/emergency/${contactId}`
    );

    return response.data;
};