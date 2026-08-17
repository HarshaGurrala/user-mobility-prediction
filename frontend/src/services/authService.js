import axios from "axios";


const API = axios.create({

    baseURL:"https://safepath-guardian.onrender.com"

});


export const loginUser = async(data)=>{

    const response = await API.post(
        "/auth/login",
        data
    );

    return response.data;

};



export const registerUser = async(data)=>{

    const response = await API.post(
        "/auth/register",
        data
    );

    return response.data;

};