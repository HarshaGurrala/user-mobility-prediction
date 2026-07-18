import { motion } from "framer-motion";

export default function Navbar() {
  return (
    <motion.nav
      initial={{ y: -80, opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      transition={{ duration: .8 }}
      className="absolute top-8 left-1/2 -translate-x-1/2 z-50 w-[94%] max-w-7xl"
    >
      <div className="h-20 rounded-full border border-white/10 bg-black/40 backdrop-blur-3xl flex items-center justify-between px-10">

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

          <button className="hover:text-white transition">
            Home
          </button>

          <button className="hover:text-white transition">
            Platform
          </button>

          <button className="hover:text-white transition">
            Technology
          </button>

          <button className="hover:text-white transition">
            About
          </button>

          <button className="hover:text-white transition">
            Contact
          </button>

        </div>

        {/* Right */}

        <div className="flex gap-4">

          <button className="text-gray-300">

            Login

          </button>

          <button className="rounded-full px-6 py-3 bg-white text-black font-semibold hover:scale-105 transition">

            Get Started

          </button>

        </div>

      </div>

    </motion.nav>
  );
}