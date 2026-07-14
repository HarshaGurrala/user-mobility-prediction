import { Link } from "react-router-dom";
import { motion } from "framer-motion";
import { MapPinned, ShieldCheck, BrainCircuit } from "lucide-react";

export default function Hero() {
  return (
    <section className="min-h-screen flex items-center justify-center px-8">

      <div className="max-w-7xl mx-auto grid lg:grid-cols-2 gap-16 items-center">

        {/* Left Content */}

        <motion.div
          initial={{ opacity: 0, y: 60 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.8 }}
        >

          <span className="bg-cyan-500/20 text-cyan-400 px-4 py-2 rounded-full text-sm font-semibold">
            AI Powered User Safety Platform
          </span>

          <h1 className="text-6xl lg:text-7xl font-black leading-tight mt-8">

            User Mobility

            <span className="block text-cyan-400">

              Prediction

            </span>

          </h1>

          <p className="text-xl text-slate-400 mt-8 leading-8">

            Predict user movement using Artificial Intelligence,
            monitor live locations, detect emergencies,
            and guide users towards safe destinations.

          </p>

          <div className="flex gap-5 mt-10">

            <Link
              to="/register"
              className="bg-cyan-500 hover:bg-cyan-400 transition px-8 py-4 rounded-2xl font-semibold"
            >
              Get Started
            </Link>

            <Link
              to="/login"
              className="border border-white/20 hover:bg-white/10 transition px-8 py-4 rounded-2xl"
            >
              Login
            </Link>

          </div>

        </motion.div>

        {/* Right Side */}

        <motion.div
          initial={{ opacity: 0, x: 80 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ duration: 1 }}
        >

          <div className="bg-white/5 backdrop-blur-2xl border border-white/10 rounded-3xl p-10">

            <div className="space-y-6">

              <div className="flex items-center gap-5">

                <MapPinned
                  className="text-cyan-400"
                  size={42}
                />

                <div>

                  <h3 className="font-bold text-xl">

                    Live Tracking

                  </h3>

                  <p className="text-slate-400">

                    Real-time GPS Monitoring

                  </p>

                </div>

              </div>

              <div className="flex items-center gap-5">

                <BrainCircuit
                  className="text-purple-400"
                  size={42}
                />

                <div>

                  <h3 className="font-bold text-xl">

                    AI Prediction

                  </h3>

                  <p className="text-slate-400">

                    Predict Next Location

                  </p>

                </div>

              </div>

              <div className="flex items-center gap-5">

                <ShieldCheck
                  className="text-green-400"
                  size={42}
                />

                <div>

                  <h3 className="font-bold text-xl">

                    Emergency Response

                  </h3>

                  <p className="text-slate-400">

                    Instant Safety Alerts

                  </p>

                </div>

              </div>

            </div>

          </div>

        </motion.div>

      </div>

    </section>
  );
}