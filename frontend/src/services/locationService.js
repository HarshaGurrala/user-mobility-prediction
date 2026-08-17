
import axios from "axios";


const API_URL = "https://safepath-guardian.onrender.com";


const api = axios.create({

    baseURL: API_URL,

    headers:{
        "Content-Type":"application/json"
    }

});




// Attach JWT token automatically

api.interceptors.request.use(

(config)=>{


const token = localStorage.getItem("token");


if(token){

config.headers.Authorization = 
`Bearer ${token}`;

}


return config;


},


(error)=>{

return Promise.reject(error);

}


);







// Current user location

export const getCurrentLocation = async(userId)=>{


const response = await api.get(

`/location/current/${userId}`

);


return response.data;


};








// Location history

export const getLocationHistory = async(userId)=>{


const response = await api.get(

`/location/history/${userId}`

);


return response.data;


};








// AI next location prediction

export const getPrediction = async(userId)=>{


const response = await api.get(

`/prediction/next/${userId}`

);


return response.data;


};








// Alerts

export const getAlerts = async(userId)=>{

const response = await api.get(
`/alert/user/${userId}`
);

return response.data;

};




export const getSafeLocations = async(userId)=>{

const response = await api.get(
`/safe-location/${userId}`
);

return response.data;

};




// User profile + online status



export const getUserProfile = async () => {

    const response = await api.get(
        "/users/me"
    );

    return response.data;
};












export default api;

export const getMovementAnalytics = async (userId) => {

    const response = await api.get(
        `/analytics/movement/${userId}`
    );

    return response.data;

};

