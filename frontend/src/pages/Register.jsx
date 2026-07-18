import { useState } from "react";
import { FiMail, FiUser, FiPhone, FiArrowRight } from "react-icons/fi";
import { Link, useNavigate } from "react-router-dom";

import AuthBackground from "../components/auth/AuthBackground";
import AuthCard from "../components/auth/AuthCard";
import InputField from "../components/auth/InputField";
import PasswordInput from "../components/auth/PasswordInput";

import { useAuth } from "../context/AuthContext";


function Register() {

    const navigate = useNavigate();

    const { register } = useAuth();


    const [error, setError] = useState("");


    const [formData, setFormData] = useState({

        full_name: "",
        email: "",
        phone_number: "",
        password: ""

    });



    const handleChange = (e) => {

        setFormData({

            ...formData,

            [e.target.name]: e.target.value

        });

    };




    const handleSubmit = async (e) => {

        e.preventDefault();

        setError("");


        try {


            await register(formData);


            alert(
                "Account created successfully. Please login."
            );


            navigate("/login");


        }
        catch(err) {


            setError(

                err.response?.data?.detail ||
                "Registration failed"

            );


        }

    };




    return (

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


        <AuthBackground />



        <AuthCard

            title="Create Account"

            subtitle="Join the AI mobility prediction system"

        >



        <form

        onSubmit={handleSubmit}

        className="space-y-5"

        >



        <InputField

            icon={FiUser}

            label="Full Name"

            name="full_name"

            placeholder="Enter your full name"

            value={formData.full_name}

            onChange={handleChange}

        />




        <InputField

            icon={FiMail}

            label="Email"

            name="email"

            type="email"

            placeholder="Enter your email"

            value={formData.email}

            onChange={handleChange}

        />





        <InputField

            icon={FiPhone}

            label="Phone Number"

            name="phone_number"

            placeholder="Enter your phone number"

            value={formData.phone_number}

            onChange={handleChange}

        />





        <PasswordInput

            name="password"

            value={formData.password}

            onChange={handleChange}

        />





        {
            error &&

            <p
            className="
            text-red-400
            text-sm
            text-center
            "
            >

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
        items-center
        justify-center
        gap-2
        shadow-lg
        shadow-blue-500/20
        hover:scale-[1.02]
        transition
        "

        >


            Create Account


            <FiArrowRight/>


        </button>




        </form>





        <p
        className="
        text-center
        text-sm
        text-gray-400
        mt-6
        "
        >

        Already have an account?


        <Link

        to="/login"

        className="
        ml-2
        text-blue-400
        hover:text-blue-300
        "

        >

        Login

        </Link>


        </p>




        </AuthCard>



        </div>

    );

}


export default Register;