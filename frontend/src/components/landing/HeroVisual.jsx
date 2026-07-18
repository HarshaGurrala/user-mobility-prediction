import { motion } from "framer-motion";
import {
  FiMapPin,
  FiActivity,
  FiShield,
} from "react-icons/fi";

export default function HeroVisual() {
  return (
    <div className="relative flex items-center justify-center h-[700px]">

      <div className="absolute w-[620px] h-[620px] rounded-full bg-blue-600/10 blur-[180px]" />

      <div className="absolute w-[500px] h-[500px] rounded-full border border-white/10" />

      <motion.div
        animate={{ rotate: 360 }}
        transition={{
          duration: 45,
          repeat: Infinity,
          ease: "linear",
        }}
        className="absolute w-[420px] h-[420px] rounded-full border border-white/5"
      />

      <motion.div
        animate={{
          scale: [1, 1.04, 1],
        }}
        transition={{
          duration: 5,
          repeat: Infinity,
        }}
        className="relative w-[320px] h-[320px] rounded-full bg-gradient-to-br from-[#111827] via-[#1f2937] to-[#111827] border border-white/10 flex items-center justify-center shadow-[0_0_120px_rgba(59,130,246,.15)]"
      >

        <div className="text-center">

          <h1 className="text-7xl">

            🌍

          </h1>

          <p className="mt-5 text-gray-400">

            AI Location Engine

          </p>

        </div>

      </motion.div>

      <motion.div
        animate={{ y: [0, -12, 0] }}
        transition={{
          repeat: Infinity,
          duration: 4,
        }}
        className="absolute left-0 top-24 rounded-3xl border border-white/10 bg-white/5 backdrop-blur-xl p-5 w-56"
      >

        <FiMapPin className="text-blue-400 text-3xl" />

        <h3 className="text-white mt-4">

          Live Tracking

        </h3>

        <p className="text-gray-500 mt-2">

          Real-Time Location

        </p>

      </motion.div>

      <motion.div
        animate={{ y: [0, 12, 0] }}
        transition={{
          repeat: Infinity,
          duration: 5,
        }}
        className="absolute right-0 top-40 rounded-3xl border border-white/10 bg-white/5 backdrop-blur-xl p-5 w-56"
      >

        <FiActivity className="text-green-400 text-3xl" />

        <h3 className="text-white mt-4">

          AI Prediction

        </h3>

        <p className="text-gray-500 mt-2">

          97% Confidence

        </p>

      </motion.div>

      <motion.div
        animate={{ y: [0, -12, 0] }}
        transition={{
          repeat: Infinity,
          duration: 4,
        }}
        className="absolute bottom-16 left-10 rounded-3xl border border-white/10 bg-white/5 backdrop-blur-xl p-5 w-60"
      >

        <FiShield className="text-violet-400 text-3xl" />

        <h3 className="text-white mt-4">

          Guardian Alert

        </h3>

        <p className="text-gray-500 mt-2">

          Active Protection

        </p>

      </motion.div>

    </div>
  );
}
