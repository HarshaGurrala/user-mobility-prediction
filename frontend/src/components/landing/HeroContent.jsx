import { motion } from "framer-motion";
import { useNavigate } from "react-router-dom";

export default function HeroContent() {
  const navigate = useNavigate();

  return (
    <div className="relative z-20">

      <motion.h1
        initial={{ y: 60, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: .3 }}
        className="mt-10 text-6xl lg:text-8xl font-bold leading-none tracking-tight text-white"
      >
        Predict
        <br />

        <span className="bg-gradient-to-r from-white via-gray-300 to-blue-400 bg-clip-text text-transparent">
          Every Journey
        </span>
      </motion.h1>

      <motion.p
        initial={{ y: 40, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: .5 }}
        className="mt-8 max-w-xl text-lg leading-8 text-gray-400"
      >
        Intelligent real-time mobility prediction using AI,
        location analytics and guardian safety monitoring
        for smarter travel decisions.
      </motion.p>

      <motion.div
        initial={{ y: 30, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: .7 }}
        className="mt-12 flex gap-5"
      >

        <button
          className="rounded-full bg-white px-8 py-4 font-semibold text-black hover:scale-105 transition"
          onClick={() => navigate("/register")}
        >
          Create Account
        </button>

        <button
          className="rounded-full border border-white/10 bg-white/5 px-8 py-4 text-white backdrop-blur-xl hover:bg-white/10 transition"
          onClick={() => navigate("/login")}
        >
          Login
        </button>

      </motion.div>

      <div className="mt-20 flex gap-16">

        <div>

          <h2 className="text-4xl font-bold text-white">

            97%

          </h2>

          <p className="text-gray-500 mt-2">

            Prediction Accuracy

          </p>

        </div>

        <div>

          <h2 className="text-4xl font-bold text-white">

            24/7

          </h2>

          <p className="text-gray-500 mt-2">

            Guardian Monitoring

          </p>

        </div>

      </div>

    </div>
  );
}