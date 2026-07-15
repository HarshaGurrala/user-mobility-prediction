import { Link, useNavigate } from "react-router-dom";
import { FaEnvelope } from "react-icons/fa";
import { useState } from "react";

import PasswordInput from "./PasswordInput";
import SocialLogin from "./SocialLogin";

import { loginUser } from "../../services/authService";


export default function LoginForm() {


  const navigate = useNavigate();


  const [formData, setFormData] = useState({
    email: "",
    password: ""
  });


  const [loading, setLoading] = useState(false);



  const handleChange = (e) => {

    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });

  };



  const handleSubmit = async (e) => {

    e.preventDefault();


    console.log("LOGIN CLICKED", formData);


    try {

      setLoading(true);


      const data = await loginUser(
        formData.email,
        formData.password
      );


      console.log(
        "LOGIN RESPONSE",
        data
      );


      localStorage.setItem(
        "token",
        data.access_token
      );


      alert("Login Successful");


      navigate("/dashboard");


    } catch(error) {


      console.log(
        "LOGIN ERROR",
        error
      );


      alert(
        error.response?.data?.detail ||
        "Login Failed"
      );


    } finally {

      setLoading(false);

    }

  };



  return (

    <form
      className="space-y-6"
      onSubmit={handleSubmit}
    >


      {/* Email */}

      <div>

        <label className="text-slate-300 mb-2 block">
          Email Address
        </label>


        <div className="relative">

          <FaEnvelope className="absolute left-4 top-5 text-slate-400" />


          <input

            type="email"

            name="email"

            value={formData.email}

            onChange={handleChange}

            placeholder="Enter your email"

            required

            className="w-full pl-12 pr-4 py-4 rounded-xl bg-slate-900/70 border border-white/10 text-white outline-none focus:border-cyan-400 transition"

          />


        </div>

      </div>



      {/* Password */}

      <PasswordInput

        name="password"

        value={formData.password}

        onChange={handleChange}

      />



      <div className="flex items-center justify-between">


        <label className="flex items-center gap-2 text-slate-400">

          <input type="checkbox" />

          Remember Me

        </label>


        <button
          type="button"
          className="text-cyan-400"
        >

          Forgot Password?

        </button>


      </div>



      <button

        type="submit"

        disabled={loading}

        className="
        w-full
        py-4
        rounded-xl
        bg-gradient-to-r
        from-cyan-500
        to-blue-600
        font-bold
        text-white
        "

      >

        {loading ? "Logging in..." : "Login"}

      </button>



      <SocialLogin />



      <p className="text-center text-slate-400">

        Don't have an account?

        <Link
          to="/register"
          className="text-cyan-400 ml-2"
        >

          Register

        </Link>


      </p>


    </form>

  );

}