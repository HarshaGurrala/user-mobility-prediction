import { motion } from "framer-motion";

import DashboardHeader from "../components/dashboard/DashboardHeader";

import LocationCard from "../components/dashboard/LocationCard";
import AIPredictionCard from "../components/dashboard/AIPredictionCard";
import AlertsCard from "../components/dashboard/AlertsCard";
import SafeLocationCard from "../components/dashboard/SafeLocationCard";
import LiveMap from "../components/dashboard/LiveMap";
import LocationAnalysisCard from "../components/dashboard/LocationAnalysisCard";

import useMobilityData from "../hooks/useMobilityData";

export default function Dashboard() {
  // Replace this with logged-in user id if stored elsewhere
  const userId = localStorage.getItem("userId");

  const {
    location,
    history,
    prediction,
    alerts,
    loading,
    error,
  } = useMobilityData(userId);

  const safeLocations = [
    "Home",
    "College",
    "Office",
  ];

  return (
    <div
      className="
relative
min-h-screen
w-full
overflow-hidden
bg-[#050505]
text-white
"
    >
      {/* permanent background */}

      <div
        className="
fixed
inset-0
bg-[#050505]
-z-10
"
      />

      {/* AI glow */}

      <div
        className="
fixed
top-[-300px]
left-1/2
-translate-x-1/2
w-[900px]
h-[900px]
bg-blue-600/10
blur-[180px]
rounded-full
"
      />

      <div
        className="
relative
max-w-[1600px]
mx-auto
px-6
py-8
space-y-10
"
      >
        <DashboardHeader />

        {error && (
          <div
            className="
rounded-2xl
border
border-red-500/30
bg-red-500/10
px-4
py-3
text-red-300
"
          >
            {error}
          </div>
        )}

        <motion.div
          initial={{
            opacity: 0,
            scale: 0.95,
          }}
          animate={{
            opacity: 1,
            scale: 1,
          }}
          transition={{
            duration: 0.8,
          }}
          className="
rounded-[40px]
p-3
bg-white/[0.04]
border
border-white/10
backdrop-blur-3xl
"
        >
          <LiveMap
            latitude={location?.latitude || 16.0545}
            longitude={location?.longitude || 80.0025}
            place={location?.place_name || "Unknown Location"}
          />
        </motion.div>

        <div
          className="
grid
grid-cols-1
xl:grid-cols-3
gap-6
"
        >
          <div
            className="
xl:col-span-2
grid
grid-cols-1
lg:grid-cols-2
gap-6
"
          >
            <LocationCard
              location={location}
              loading={loading}
            />

            <AIPredictionCard
              prediction={prediction}
              loading={loading}
            />
          </div>

          <AlertsCard
            alerts={alerts}
            loading={loading}
          />
        </div>

        <div
          className="
grid
grid-cols-1
lg:grid-cols-2
gap-6
"
        >
          <SafeLocationCard
            locations={safeLocations}
          />

          <LocationAnalysisCard
            history={history}
            loading={loading}
          />
        </div>
      </div>
    </div>
  );
}