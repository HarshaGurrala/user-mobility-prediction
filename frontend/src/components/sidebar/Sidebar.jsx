import { NavLink } from "react-router-dom";
import {
  LayoutDashboard,
  MapPinned,
  ShieldCheck,
  Users,
  BarChart3,
  User,
  Settings,
  BrainCircuit
} from "lucide-react";

const menu = [
  {
    title: "Dashboard",
    path: "/dashboard",
    icon: LayoutDashboard
  },
  {
    title: "Live Tracking",
    path: "/live-tracking",
    icon: MapPinned
  },
  {
    title: "Safe Locations",
    path: "/safe-locations",
    icon: ShieldCheck
  },
  {
    title: "Emergency",
    path: "/emergency",
    icon: Users
  },
  {
    title: "Analytics",
    path: "/analytics",
    icon: BarChart3
  },
  {
    title: "Profile",
    path: "/profile",
    icon: User
  },
  {
    title: "Settings",
    path: "/settings",
    icon: Settings
  }
];

export default function Sidebar() {
  return (
    <aside className="w-72 min-h-screen bg-slate-900 border-r border-white/10 p-6">

      <div className="flex items-center gap-3 mb-10">

        <BrainCircuit
          className="text-cyan-400"
          size={34}
        />

        <div>

          <h1 className="text-white text-2xl font-bold">

            MobilityAI

          </h1>

          <p className="text-slate-400 text-sm">

            AI Prediction System

          </p>

        </div>

      </div>

      <nav className="space-y-3">

        {menu.map((item) => {

          const Icon = item.icon;

          return (

            <NavLink
              key={item.title}
              to={item.path}
              className={({ isActive }) =>
                `flex items-center gap-4 px-5 py-4 rounded-2xl transition ${
                  isActive
                    ? "bg-cyan-500 text-black font-semibold"
                    : "text-slate-300 hover:bg-white/10"
                }`
              }
            >

              <Icon size={22} />

              {item.title}

            </NavLink>

          );

        })}

      </nav>

    </aside>
  );
}