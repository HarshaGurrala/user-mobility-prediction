import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FaUser, FaEnvelope, FaPhone } from "react-icons/fa";

import PasswordInput from "./PasswordInput";
import SocialLogin from "./SocialLogin";
import { registerUser } from "../../services/authService";

export default function RegisterForm() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    full_name: "",
    email: "",
    phone_number: "",
    password: "",
    confirmPassword: "",
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      alert("Passwords do not match.");
      return;
    }

    try {
      setLoading(true);

      await registerUser({
        full_name: formData.full_name,
        email: formData.email,
        phone_number: formData.phone_number,
        password: formData.password,
      });

      alert("Registration Successful!");

      navigate("/login");
    } catch (error) {
  console.error("Registration Error:", error);

  if (error.response) {
    console.log("Status:", error.response.status);
    console.log("Data:", error.response.data);

    alert(JSON.stringify(error.response.data));
  } else {
    alert(error.message);
  }
}finally {
      setLoading(false);
    }
  };

  return (
    <form className="space-y-6" onSubmit={handleSubmit}>

      {/* Full Name */}

      <div>
        <label className="text-slate-300 mb-2 block">
          Full Name
        </label>

        <div className="relative">
          <FaUser className="absolute left-4 top-5 text-slate-400" />

          <input
            type="text"
            name="full_name"
            value={formData.full_name}
            onChange={handleChange}
            placeholder="Enter your full name"
            required
            className="w-full pl-12 pr-4 py-4 rounded-xl bg-slate-900/70 border border-white/10 text-white outline-none focus:border-cyan-400"
          />
        </div>
      </div>

      {/* Email */}

      <div>
        <label className="text-slate-300 mb-2 block">
          Email
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
            className="w-full pl-12 pr-4 py-4 rounded-xl bg-slate-900/70 border border-white/10 text-white outline-none focus:border-cyan-400"
          />
        </div>
      </div>

      {/* Phone */}

      <div>
        <label className="text-slate-300 mb-2 block">
          Phone Number
        </label>

        <div className="relative">
          <FaPhone className="absolute left-4 top-5 text-slate-400" />

          <input
            type="tel"
            name="phone_number"
            value={formData.phone_number}
            onChange={handleChange}
            placeholder="Enter your phone number"
            required
            className="w-full pl-12 pr-4 py-4 rounded-xl bg-slate-900/70 border border-white/10 text-white outline-none focus:border-cyan-400"
          />
        </div>
      </div>

      {/* Password */}

      <PasswordInput
        placeholder="Password"
        value={formData.password}
        onChange={handleChange}
        name="password"
      />

      {/* Confirm Password */}

      <PasswordInput
        placeholder="Confirm Password"
        value={formData.confirmPassword}
        onChange={handleChange}
        name="confirmPassword"
      />

      <button
        type="submit"
        disabled={loading}
        className="w-full py-4 rounded-xl bg-gradient-to-r from-cyan-500 to-blue-600 text-white font-bold hover:scale-[1.02] transition"
      >
        {loading ? "Creating Account..." : "Create Account"}
      </button>

      <SocialLogin />

      <p className="text-center text-slate-400">
        Already have an account?

        <Link
          to="/login"
          className="text-cyan-400 ml-2 hover:underline"
        >
          Login
        </Link>
      </p>

    </form>
  );
}