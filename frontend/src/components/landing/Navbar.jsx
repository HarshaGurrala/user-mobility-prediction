import { motion } from "framer-motion";
import { useNavigate } from "react-router-dom";

const menuItems = [
  { label: "Home", target: "home" },
  { label: "Platform", target: "platform" },
  { label: "Technology", target: "technology" },
  { label: "About", target: "about" },
  { label: "Contact", target: "contact" },
];

export default function Navbar() {
  const navigate = useNavigate();
  return (
    <motion.nav
      initial={{ y: -80, opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      transition={{ duration: .8 }}
      className="fixed top-6 left-1/2 -translate-x-1/2 z-50 w-[96%] max-w-7xl"
    >
      <div className="h-20 rounded-full border border-white/10 bg-black/15 backdrop-blur-3xl flex items-center justify-between px-10">

        {/* Logo */}

        <div className="flex items-center gap-4">

          <div className="w-11 h-11 rounded-full bg-white flex items-center justify-center font-bold text-black">

            M

          </div>

          <div>

            <h2 className="text-white font-semibold text-lg">

              MobilityAI

            </h2>

            <p className="text-gray-500 text-xs">

              AI Mobility Prediction

            </p>

          </div>

        </div>

        {/* Menu */}

        <div className="hidden lg:flex gap-12 text-gray-400">
          {menuItems.map((item) => (
            <button
              key={item.target}
              className="hover:text-white transition"
              onClick={() => {
                const el = document.getElementById(item.target);
                if (el) {
                  el.scrollIntoView({ behavior: "smooth", block: "start" });
                }
              }}
            >
              {item.label}
            </button>
          ))}
        </div>

        {/* Right */}

        <div className="flex gap-4">

          <button
            className="text-gray-300"
            onClick={() => navigate("/login")}
          >
            Login
          </button>

          <button
            className="rounded-full px-6 py-3 bg-white text-black font-semibold hover:scale-105 transition"
            onClick={() => navigate("/register")}
          >
            Get Started
          </button>

        </div>

      </div>

    </motion.nav>
  );
}