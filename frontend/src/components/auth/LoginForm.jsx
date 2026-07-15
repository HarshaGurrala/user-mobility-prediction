import { Link } from "react-router-dom";
import { FaEnvelope } from "react-icons/fa";
import PasswordInput from "./PasswordInput";
import SocialLogin from "./SocialLogin";

export default function LoginForm() {
  return (
    <form className="space-y-6">

      {/* Email */}

      <div>

        <label className="text-slate-300 mb-2 block">

          Email Address

        </label>

        <div className="relative">

          <FaEnvelope className="absolute left-4 top-5 text-slate-400" />

          <input
            type="email"
            placeholder="Enter your email"
            className="w-full pl-12 pr-4 py-4 rounded-xl bg-slate-900/70 border border-white/10 text-white outline-none focus:border-cyan-400 transition"
          />

        </div>

      </div>

      {/* Password */}

      <PasswordInput />

      {/* Remember */}

      <div className="flex items-center justify-between">

        <label className="flex items-center gap-2 text-slate-400">

          <input type="checkbox" />

          Remember Me

        </label>

        <button
          type="button"
          className="text-cyan-400 hover:text-cyan-300"
        >
          Forgot Password?
        </button>

      </div>

      {/* Login */}

      <button
        className="
        w-full
        py-4
        rounded-xl
        bg-gradient-to-r
        from-cyan-500
        to-blue-600
        font-bold
        text-white
        hover:scale-[1.02]
        transition-all
        duration-300
        shadow-lg
        shadow-cyan-500/30
        "
      >

        Login

      </button>

      <SocialLogin />

      <p className="text-center text-slate-400">

        Don't have an account?

        <Link
          to="/register"
          className="text-cyan-400 ml-2 hover:underline"
        >

          Register

        </Link>

      </p>

    </form>
  );
}