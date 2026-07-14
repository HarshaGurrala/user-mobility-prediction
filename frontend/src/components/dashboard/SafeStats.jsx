import {
  BrainCircuit,
  Users,
  Bell,
  Route
} from "lucide-react";

const stats = [
  {
    title: "Prediction Accuracy",
    value: "98.4%",
    icon: BrainCircuit,
    color: "text-purple-400"
  },
  {
    title: "Tracked Users",
    value: "10,248",
    icon: Users,
    color: "text-cyan-400"
  },
  {
    title: "Emergency Alerts",
    value: "520",
    icon: Bell,
    color: "text-red-400"
  },
  {
    title: "Trips Today",
    value: "1,275",
    icon: Route,
    color: "text-green-400"
  }
];

export default function DashboardStats() {

  return (

    <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-6">

      {stats.map((item, index) => {

        const Icon = item.icon;

        return (

          <div
            key={index}
            className="bg-slate-900 border border-white/10 rounded-3xl p-6 hover:border-cyan-400 transition"
          >

            <Icon
              size={40}
              className={item.color}
            />

            <h3 className="text-slate-400 mt-5">

              {item.title}

            </h3>

            <h1 className="text-white text-4xl font-bold mt-2">

              {item.value}

            </h1>

          </div>

        );

      })}

    </div>

  );

}