import axios from "axios";


const API = axios.create({

baseURL:"http://127.0.0.1:8000"

});


API.interceptors.request.use(

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





export const getGuardianUsers = async ()=>{

const response = await API.get(
"/guardian/connected-users"
);

return response.data;

};






export const getGuardianAlerts = async (guardianId)=>{

const response = await API.get(
`/guardian/${guardianId}/alerts`
);

return response.data;

};






export const getGuardianSafety = async (guardianId)=>{

const response = await API.get(
`/guardian/${guardianId}/safety`
);

return response.data;

};





export const getGuardianMovement = async (filter)=>{

const response = await API.get(
`/guardian/movement-analytics?filter=${filter}`
);

return response.data;

};


// export const getGuardianMovement = async (filter) => {
//     return API.get(`/guardian/movement-analytics?filter=${filter}`);
// };







// ==================================================
// SINGLE USER LIVE LOCATION MAP
// ==================================================

export const getGuardianUserLiveLocation = async (userId)=>{

const response = await API.get(
`/guardian/user/${userId}/live-location`
);

return response.data;

};








export const getGuardianUserDetails = async (userId)=>{

const response = await API.get(
`/guardian/user/${userId}`
);

return response.data;

};








export const getGuardianAIReport = async (guardianId)=>{

const response = await API.get(
`/guardian/${guardianId}/ai-report`
);

return response.data;

};








export const getGuardianRequestStatus = async ()=>{

const response = await API.get(
"/guardian/request-status"
);

return response.data;

};








export const getGuardianStats = async () => {

const response = await API.get(
"/guardian/stats"
);

return response.data;

};








// ==================================================
// ALL CONNECTED USERS LIVE MAP
// ==================================================

export const getGuardianMapUsers = async ()=>{

const response =
await API.get(
"/guardian/live-map"
);

return response.data;

};



