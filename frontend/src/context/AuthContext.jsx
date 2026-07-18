import { createContext, useContext, useState } from "react";

import api from "../api/axios";


const AuthContext = createContext();



export function AuthProvider({children}){


const [token,setToken] = useState(

    localStorage.getItem("token")

);





const register = async(data)=>{


const response = await api.post(

    "/auth/register",

    data

);


return response.data;


};






const login = async(email,password)=>{


const response = await api.post(

    "/auth/login",

    {

        email,

        password

    }

);




const accessToken =

response.data.access_token;




localStorage.setItem(

"token",

accessToken

);




setToken(accessToken);




return response.data;


};






const logout=()=>{


localStorage.removeItem(
    "token"
);


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