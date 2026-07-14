import { Link } from "react-router-dom";
import { MapPinned } from "lucide-react";

export default function Navbar() {
  return (
    <nav className="sticky top-0 z-50 backdrop-blur-xl bg-slate-950/70 border-b border-white/10">

      <div className="max-w-7xl mx-auto flex items-center justify-between px-8 py-5">

        <Link
          to="/"
          className="flex items-center gap-3"
        >
          <MapPinned className="text-cyan-400" size={32} />

          <h1 className="text-2xl font-bold">
            MobilityAI
          </h1>

        </Link>

        <div className="hidden md:flex gap-10">

          <a href="#features">Features</a>

          <a href="#stats">Statistics</a>

          <a href="#tech">Technology</a>

        </div>

        <div className="flex gap-4">

          <Link
            to="/login"
            className="px-5 py-2 rounded-xl hover:bg-white/10 transition"
          >
            Login
          </Link>

          <Link
            to="/register"
            className="bg-cyan-500 px-5 py-2 rounded-xl font-semibold hover:bg-cyan-400 transition"
          >
            Register
          </Link>

        </div>

      </div>

    </nav>
  );
}