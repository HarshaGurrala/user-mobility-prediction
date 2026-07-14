import {
  MapPin,
  BrainCircuit,
  ShieldCheck,
  Phone,
  Cpu
} from "lucide-react";

export default function RecentActivity() {

  const activity = [
    {
      title: "Location Updated",
      time: "2 min ago",
      icon: <MapPin className="text-cyan-400" size={20} />
    },
    {
      title: "Prediction Generated",
      time: "3 min ago",
      icon: <BrainCircuit className="text-purple-400" size={20} />
    },
    {
      title: "Safe Zone Entered",
      time: "5 min ago",
      icon: <ShieldCheck className="text-green-400" size={20} />
    },
    {
      title: "Emergency Contact Synced",
      time: "8 min ago",
      icon: <Phone className="text-red-400" size={20} />
    },
    {
      title: "AI Model Executed",
      time: "10 min ago",
      icon: <Cpu className="text-yellow-400" size={20} />
    }
  ];

  return (
    <div className="bg-slate-900 border border-white/10 rounded-3xl p-6">

      <h2 className="text-2xl font-bold text-white mb-6">
        Recent Activity
      </h2>

      <div className="space-y-4">

        {activity.map((item, index) => (

          <div
            key={index}
            className="flex items-center justify-between bg-slate-800 rounded-2xl p-4 hover:bg-slate-700 transition"
          >

            <div className="flex items-center gap-4">

              {item.icon}

              <span className="text-white">
                {item.title}
              </span>

            </div>

            <span className="text-slate-400 text-sm">
              {item.time}
            </span>

          </div>

        ))}

      </div>

    </div>
  );
}