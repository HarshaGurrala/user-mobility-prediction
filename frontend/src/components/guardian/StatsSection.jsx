import {
  FaUsers,
  FaWifi,
  FaUserSlash,
  FaExclamationTriangle,
} from "react-icons/fa";

import StatCard from "./StatCard";

export default function StatsSection() {
  return (
    <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-6">

      <StatCard
        icon={<FaUsers />}
        title="Total Children"
        value="3"
        color="bg-gradient-to-r from-cyan-500 to-blue-600"
      />

      <StatCard
        icon={<FaWifi />}
        title="Online"
        value="2"
        color="bg-gradient-to-r from-green-500 to-emerald-600"
      />

      <StatCard
        icon={<FaUserSlash />}
        title="Offline"
        value="1"
        color="bg-gradient-to-r from-orange-500 to-red-500"
      />

      <StatCard
        icon={<FaExclamationTriangle />}
        title="SOS Alerts"
        value="0"
        color="bg-gradient-to-r from-red-500 to-pink-600"
      />

    </div>
  );
}