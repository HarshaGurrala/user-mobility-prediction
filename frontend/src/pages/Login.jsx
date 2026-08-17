import { useState } from "react";
import { FiMail, FiArrowRight } from "react-icons/fi";
import { Link, useNavigate } from "react-router-dom";

import AuthBackground from "../components/auth/AuthBackground";
import AuthCard from "../components/auth/AuthCard";
import InputField from "../components/auth/InputField";
import PasswordInput from "../components/auth/PasswordInput";

import { useAuth } from "../context/AuthContext";


function Login(){

    const navigate = useNavigate();

    const { login } = useAuth();


    const [error,setError] = useState("");



    const [formData,setFormData]=useState({

        email:"",
        password:""

    });



    const handleChange=(e)=>{

        setFormData({

            ...formData,

            [e.target.name]:e.target.value

        });

    };




    const handleSubmit=async(e)=>{

        e.preventDefault();

        setError("");


        try{


            const response = await login(
                formData.email,
                formData.password
            );


            console.log(
                "LOGIN RESPONSE:",
                response
            );


            const role =
                response?.user?.role ||
                response?.role;



            if(role === "GUARDIAN"){

                navigate("/guardian/");

            }
            else{

                setError(
                    "Only guardian accounts can login here"
                );

            }


        }

                catch(err){

            console.log(
                "LOGIN ERROR:",
                err
            );

            const detail =
                err.response?.data?.detail;

            if (typeof detail === "string") {

                setError(detail);

            }
            else if (Array.isArray(detail)) {

                setError(
                    detail
                        .map(item => item.msg || "Invalid input")
                        .join(", ")
                );

            }
            else {

                setError("Login failed");

            }

        }

    };





return(

<div
className="
min-h-screen
flex
items-center
justify-center
px-6
relative
overflow-hidden
"
>


<AuthBackground/>


<AuthCard

title="Welcome Back"

subtitle="Login to your AI mobility dashboard"

>


<form

onSubmit={handleSubmit}

className="space-y-5"

>


<InputField

icon={FiMail}

label="Email"

name="email"

type="email"

placeholder="Enter your email"

value={formData.email}

onChange={handleChange}

/>



<PasswordInput

name="password"

value={formData.password}

onChange={handleChange}

/>



{
error &&

<p className="
text-red-400
text-sm
text-center
">

{error}

</p>

}



<button

type="submit"

className="
w-full
py-3
rounded-xl
bg-gradient-to-r
from-blue-500
to-violet-600
text-white
font-medium
flex
justify-center
items-center
gap-2
hover:scale-[1.02]
transition
"

>


Login

<FiArrowRight/>


</button>



</form>



<p className="
text-center
text-sm
text-gray-400
mt-6
">

Don't have an account?


<Link

to="/register"

className="
ml-2
text-blue-400
"

>

Create account

</Link>


</p>


</AuthCard>


</div>


);


}


export default Login;