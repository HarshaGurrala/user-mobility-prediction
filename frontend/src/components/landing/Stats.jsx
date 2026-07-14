// export default function Stats() {
//   return (
//     <section className="py-20 text-center text-white">
//       <h2 className="text-4xl font-bold">Live Statistics</h2>
//     </section>
//   );
// }

import { motion } from "framer-motion";
import {
  Users,
  ShieldCheck,
  BrainCircuit,
  MapPinned
} from "lucide-react";

const stats = [
  {
    icon: Users,
    value: "10,000+",
    title: "Tracked Users",
    color: "text-cyan-400"
  },
  {
    icon: BrainCircuit,
    value: "98.4%",
    title: "Prediction Accuracy",
    color: "text-purple-400"
  },
  {
    icon: ShieldCheck,
    value: "520",
    title: "Emergency Alerts",
    color: "text-green-400"
  },
  {
    icon: MapPinned,
    value: "150+",
    title: "Safe Locations",
    color: "text-orange-400"
  }
];

export default function Stats() {
  return (
    <section
      id="stats"
      className="py-24 px-8 bg-slate-900"
    >
      <div className="max-w-7xl mx-auto">

        <h2 className="text-5xl font-bold text-center mb-16">
          Live Platform Statistics
        </h2>

        <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8">

          {stats.map((item, index) => {

            const Icon = item.icon;

            return (

              <motion.div
                key={index}
                whileHover={{
                  y: -8,
                  scale: 1.03
                }}
                className="rounded-3xl
                           bg-white/5
                           backdrop-blur-xl
                           border
                           border-white/10
                           p-8
                           text-center"
              >

                <Icon
                  className={`${item.color} mx-auto`}
                  size={42}
                />

                <h1 className="text-4xl font-black mt-6">

                  {item.value}

                </h1>

                <p className="text-slate-400 mt-3">

                  {item.title}

                </p>

              </motion.div>

            );

          })}

        </div>

      </div>
    </section>
  );
}