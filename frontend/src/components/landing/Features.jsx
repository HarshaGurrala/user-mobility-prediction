import { motion } from "framer-motion";
import {
  FiMap,
  FiActivity,
  FiShield,
  FiBell,
} from "react-icons/fi";

const features = [
  {
    icon: <FiMap size={32} />,
    title: "Live Location",
    desc: "Monitor user location with real-time updates and intelligent mapping.",
  },
  {
    icon: <FiActivity size={32} />,
    title: "AI Prediction",
    desc: "Predict the user's next destination using movement history.",
  },
  {
    icon: <FiShield size={32} />,
    title: "Safe Zones",
    desc: "Automatically detect safe and unsafe locations with AI.",
  },
  {
    icon: <FiBell size={32} />,
    title: "Guardian Alerts",
    desc: "Notify guardians instantly when unusual movement is detected.",
  },
];

export default function Features() {
  return (
    <section className="bg-[#050505] py-32">

      <div className="max-w-7xl mx-auto px-10">

        <motion.div
          initial={{ opacity: 0, y: 40 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          className="text-center"
        >

          <p className="text-blue-400 uppercase tracking-[6px]">
            Features
          </p>

          <h2 className="text-6xl font-bold text-white mt-6">
            Built for Safety
          </h2>

          <p className="text-gray-400 mt-6 max-w-2xl mx-auto text-lg">
            Experience next-generation mobility intelligence powered by AI,
            real-time analytics, and predictive safety monitoring.
          </p>

        </motion.div>

        <div className="grid lg:grid-cols-2 gap-8 mt-24">

          {features.map((item, index) => (

            <motion.div
              key={index}
              initial={{ opacity: 0, y: 50 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ delay: index * .15 }}
              className="rounded-[35px] border border-white/10 bg-white/5 backdrop-blur-2xl p-10 hover:bg-white/10 transition"
            >

              <div className="w-16 h-16 rounded-2xl bg-white/10 flex items-center justify-center text-blue-400">

                {item.icon}

              </div>

              <h3 className="text-3xl text-white mt-8 font-semibold">

                {item.title}

              </h3>

              <p className="text-gray-400 mt-5 leading-8">

                {item.desc}

              </p>

            </motion.div>

          ))}

        </div>

      </div>

    </section>
  );
}