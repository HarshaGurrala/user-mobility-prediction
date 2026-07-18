import { jwtDecode } from "jwt-decode";


export function getUserId(){

    const token = localStorage.getItem("token");


    if(!token){

        return null;

    }


    try{

        const decoded = jwtDecode(token);


        return decoded.user_id || decoded.sub;


    }
    catch(error){

        console.error(
            "JWT decode error",
            error
        );


        return null;

    }

}