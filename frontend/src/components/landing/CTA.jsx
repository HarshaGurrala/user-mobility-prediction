import { motion } from "framer-motion";
import { useNavigate } from "react-router-dom";

export default function CTA() {
  const navigate = useNavigate();
  return (
    <section className="bg-[#050505] py-36">

      <div className="max-w-6xl mx-auto px-8">

        <motion.div
          initial={{
            opacity: 0,
            y: 50,
          }}
          whileInView={{
            opacity: 1,
            y: 0,
          }}
          viewport={{
            once: true,
          }}
          className="relative overflow-hidden rounded-[40px] border border-white/10 bg-gradient-to-br from-[#0d1117] via-[#090909] to-[#151515] p-20"
        >

          <div className="absolute -top-40 left-1/2 -translate-x-1/2 h-[400px] w-[400px] rounded-full bg-blue-500/10 blur-[120px]" />

          <div className="relative z-10 text-center">

            <p className="tracking-[8px] uppercase text-blue-400">

              Get Started

            </p>

            <h2 className="mt-6 text-6xl font-bold text-white">

              Experience AI Mobility
              <br />
              Prediction

            </h2>

            <p className="mx-auto mt-8 max-w-3xl text-lg leading-8 text-gray-400">

              Real-time tracking, intelligent prediction,
              guardian monitoring and AI-powered safety
              in one premium platform.

            </p>

            <div className="mt-14 flex justify-center gap-6">

              <button
                className="rounded-full bg-white px-10 py-4 text-lg font-semibold text-black transition hover:scale-105"
                onClick={() => navigate("/register")}
              >
                Create Account
              </button>

              <button
                className="rounded-full border border-white/10 bg-white/5 px-10 py-4 text-lg text-white backdrop-blur-xl transition hover:bg-white/10"
                onClick={() => navigate("/login")}
              >
                Login
              </button>

            </div>

          </div>

        </motion.div>

      </div>

    </section>
  );
}