import { createContext, useContext, useState } from "react";

import api from "../api/axios";


const AuthContext = createContext();



export function AuthProvider({children}){


const [token,setToken] = useState(

    localStorage.getItem("token")

);





const register = async(data) => {

    const response = await api.post(
        "/auth/register",
        {
            ...data,
            role: "GUARDIAN"
        }
    );

    return response.data;
};







const login = async(email, password) => {

    const response = await api.post(
        "/auth/login",
        {
            email,
            password,
            device_id: "guardian-web"
        }
    );

    const accessToken =
        response.data.access_token;

    const user =
        response.data.user;

    localStorage.setItem(
        "token",
        accessToken
    );

    localStorage.setItem(
        "user",
        JSON.stringify(user)
    );

    localStorage.setItem(
        "userId",
        user.id
    );

    localStorage.setItem(
        "role",
        user.role
    );

    setToken(accessToken);

    return response.data;
};






const logout=()=>{


localStorage.removeItem("token");

localStorage.removeItem("user");

localStorage.removeItem("userId");

localStorage.removeItem("role");


setToken(null);


};





return (

<AuthContext.Provider

value={{

token,

register,

login,

logout,

isAuthenticated:!!token

}}

>

{children}


</AuthContext.Provider>


);


}




export function useAuth(){

return useContext(AuthContext);

}