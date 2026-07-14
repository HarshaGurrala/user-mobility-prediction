// export default function Features() {
//   return (
//     <section className="py-20 text-center text-white">
//       <h2 className="text-4xl font-bold">Features</h2>
//     </section>
//   );
// }
import {
  BrainCircuit,
  MapPinned,
  Shield,
  Bell,
  BarChart3,
  History
} from "lucide-react";

const features = [
  {
    icon: BrainCircuit,
    title: "AI Prediction",
    desc: "Predict the user's next destination using Machine Learning."
  },
  {
    icon: MapPinned,
    title: "Live Tracking",
    desc: "Monitor user movement in real time."
  },
  {
    icon: Shield,
    title: "Safe Route",
    desc: "Guide users through secure routes."
  },
  {
    icon: Bell,
    title: "Emergency Alerts",
    desc: "Instant notifications to emergency contacts."
  },
  {
    icon: BarChart3,
    title: "Analytics",
    desc: "Visualize travel behavior using interactive charts."
  },
  {
    icon: History,
    title: "Location History",
    desc: "Review previous trips and predictions."
  }
];

export default function Features() {

  return (

    <section
      id="features"
      className="py-24 px-8"
    >

      <div className="max-w-7xl mx-auto">

        <h2 className="text-5xl font-bold text-center mb-16">

          Powerful Features

        </h2>

        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">

          {features.map((feature, index) => {

            const Icon = feature.icon;

            return (

              <div
                key={index}
                className="bg-white/5
                           border
                           border-white/10
                           rounded-3xl
                           p-8
                           hover:border-cyan-400
                           transition-all
                           duration-300
                           hover:-translate-y-2"
              >

                <Icon
                  size={45}
                  className="text-cyan-400"
                />

                <h3 className="text-2xl font-bold mt-6">

                  {feature.title}

                </h3>

                <p className="text-slate-400 mt-4">

                  {feature.desc}

                </p>

              </div>

            );

          })}

        </div>

      </div>

    </section>

  );

}