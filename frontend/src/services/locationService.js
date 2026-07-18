// import api from "../api/axios";
// import { getUserId } from "../utils/auth";



// export const getCurrentLocation = async () => {


//     const userId = getUserId();


//     if (!userId) {

//         throw new Error(
//             "User not authenticated"
//         );

//     }



//     const response = await api.get(

//         `/location/current/${userId}`

//     );



//     return response.data;


// };





// export const saveLocation = async (locationData) => {


//     const userId = getUserId();



//     if (!userId) {

//         throw new Error(
//             "User not authenticated"
//         );

//     }



//     const response = await api.post(

//         `/location/update/${userId}`,

//         locationData

//     );



//     return response.data;


// };




import axios from "axios";



const API_URL = "http://127.0.0.1:8000";



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

`/alert/${userId}`

);


return response.data;


};

export default api;