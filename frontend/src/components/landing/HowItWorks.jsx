// export default function HowItWorks() {
//   return (
//     <section className="py-20 text-center text-white">
//       <h2 className="text-4xl font-bold">How It Works</h2>
//     </section>
//   );
// }
import { UserPlus, MapPinned, BrainCircuit, ShieldCheck, Bell } from "lucide-react";
import { motion } from "framer-motion";

const steps = [
  {
    icon: UserPlus,
    title: "Create Account",
    description:
      "Register securely and set up your profile along with emergency contacts."
  },
  {
    icon: MapPinned,
    title: "Location Tracking",
    description:
      "The application continuously collects GPS coordinates with user permission."
  },
  {
    icon: BrainCircuit,
    title: "AI Prediction",
    description:
      "Machine Learning predicts the user's next likely destination using historical mobility patterns."
  },
  {
    icon: ShieldCheck,
    title: "Safety Analysis",
    description:
      "The predicted route is compared against safe zones and potential risk areas."
  },
  {
    icon: Bell,
    title: "Emergency Alert",
    description:
      "If abnormal behavior or an emergency is detected, instant alerts are sent to emergency contacts."
  }
];

export default function HowItWorks() {
  return (
    <section className="py-28 px-8 bg-slate-950">

      <div className="max-w-7xl mx-auto">

        <h2 className="text-5xl font-bold text-center text-white">
          How It Works
        </h2>

        <p className="text-center text-slate-400 mt-5 max-w-3xl mx-auto">
          Our AI-powered mobility prediction platform continuously monitors,
          analyzes, predicts, and protects users using real-time location data
          and machine learning.
        </p>

        <div className="mt-20 grid lg:grid-cols-5 gap-8">

          {steps.map((step, index) => {

            const Icon = step.icon;

            return (

              <motion.div
                key={index}
                initial={{ opacity: 0, y: 40 }}
                whileInView={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.6, delay: index * 0.15 }}
                viewport={{ once: true }}
                className="relative"
              >

                <div className="bg-white/5 backdrop-blur-xl border border-white/10 rounded-3xl p-8 h-full hover:border-cyan-400 transition duration-300">

                  <div className="w-16 h-16 rounded-2xl bg-cyan-500/20 flex items-center justify-center">

                    <Icon
                      size={32}
                      className="text-cyan-400"
                    />

                  </div>

                  <h3 className="text-2xl font-bold text-white mt-6">

                    {step.title}

                  </h3>

                  <p className="text-slate-400 mt-4">

                    {step.description}

                  </p>

                </div>

                {index !== steps.length - 1 && (
                  <div className="hidden lg:block absolute top-20 left-full w-8 h-[2px] bg-cyan-400"></div>
                )}

              </motion.div>

            );

          })}

        </div>

      </div>

    </section>
  );
}