import { motion } from "framer-motion";
import {
  FiMap,
  FiActivity,
  FiShield,
} from "react-icons/fi";

export default function DashboardPreview() {
  return (
    <section className="bg-[#050505] py-36">

      <div className="max-w-7xl mx-auto px-10">

        <motion.div
          initial={{ opacity: 0 }}
          whileInView={{ opacity: 1 }}
          viewport={{ once: true }}
        >

          <p className="text-center text-blue-400 tracking-[6px] uppercase">
            Dashboard
          </p>

          <h2 className="text-center text-6xl font-bold text-white mt-5">
            Everything In One Place
          </h2>

        </motion.div>

        <div className="mt-24 rounded-[40px] border border-white/10 bg-white/5 backdrop-blur-3xl p-10">

          <div className="grid lg:grid-cols-3 gap-8">

            <div className="rounded-3xl bg-[#111111] p-8">

              <FiMap className="text-blue-400 text-4xl" />

              <h3 className="text-white text-2xl mt-6">
                Live Map
              </h3>

              <div className="mt-8 h-72 rounded-2xl bg-gradient-to-br from-slate-900 to-slate-800 flex items-center justify-center">

                <span className="text-gray-500">
                  Interactive Map
                </span>

              </div>

            </div>

            <div className="space-y-8">

              <div className="rounded-3xl bg-[#111111] p-8">

                <FiActivity className="text-green-400 text-4xl" />

                <h3 className="text-white text-2xl mt-6">
                  AI Prediction
                </h3>

                <h1 className="text-5xl font-bold text-blue-400 mt-6">

                  97%

                </h1>

                <p className="text-gray-400 mt-4">
                  Confidence Score
                </p>

              </div>

              <div className="rounded-3xl bg-[#111111] p-8">

                <h3 className="text-white text-xl">
                  Predicted Destination
                </h3>

                <p className="text-blue-400 text-3xl mt-6">
                  Hyderabad
                </p>

              </div>

            </div>

            <div className="rounded-3xl bg-[#111111] p-8">

              <FiShield className="text-violet-400 text-4xl" />

              <h3 className="text-white text-2xl mt-6">
                Guardian Monitor
              </h3>

              <div className="mt-10 space-y-6">

                <div className="flex justify-between">

                  <span className="text-gray-400">
                    Status
                  </span>

                  <span className="text-green-400">
                    Safe
                  </span>

                </div>

                <div className="flex justify-between">

                  <span className="text-gray-400">
                    Alerts
                  </span>

                  <span className="text-white">
                    02
                  </span>

                </div>

                <div className="flex justify-between">

                  <span className="text-gray-400">
                    Accuracy
                  </span>

                  <span className="text-blue-400">
                    97%
                  </span>

                </div>

              </div>

            </div>

          </div>

        </div>

      </div>

    </section>
  );
}