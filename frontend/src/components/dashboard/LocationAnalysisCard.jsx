import { motion } from "framer-motion";

import {
  FiMapPin,
  FiTrendingUp,
  FiClock,
  FiNavigation,
} from "react-icons/fi";

import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  ResponsiveContainer,
  Tooltip,
} from "recharts";

export default function LocationAnalysisCard({
  history = [],
  loading = false,
}) {
  // Convert backend response to chart data
  const chartData = history.map((item, index) => ({
    time:
      item.time ||
      item.timestamp ||
      item.created_at ||
      `${index + 1}`,
    distance: Number(item.distance ?? item.speed ?? 0),
  }));

  const totalDistance = history.reduce(
    (sum, item) => sum + Number(item.distance ?? 0),
    0
  );

  const activeHours = history.length;

  const predictionAccuracy = 94;

  return (
    <motion.div
      initial={{
        opacity: 0,
        y: 30,
      }}
      animate={{
        opacity: 1,
        y: 0,
      }}
      transition={{
        duration: 0.5,
      }}
      className="
relative
overflow-hidden
rounded-3xl
border
border-white/10
bg-white/5
backdrop-blur-2xl
p-6
"
    >
      {/* Glow */}

      <div
        className="
absolute
right-[-60px]
top-[-60px]
h-52
w-52
rounded-full
bg-blue-500/20
blur-3xl
"
      />

      {/* Header */}

      <div
        className="
flex
items-center
gap-3
relative
"
      >
        <div
          className="
p-3
rounded-2xl
bg-blue-500/20
"
        >
          <FiMapPin
            className="
text-blue-400
text-xl
"
          />
        </div>

        <div>
          <h2
            className="
text-white
font-semibold
text-lg
"
          >
            Location Intelligence
          </h2>

          <p
            className="
text-gray-400
text-xs
"
          >
            AI analysis of user movement patterns
          </p>
        </div>
      </div>

      {/* Graph */}

      <div
        className="
h-[200px]
mt-6
"
      >
        {loading ? (
          <div className="h-full flex items-center justify-center text-gray-400">
            Loading...
          </div>
        ) : (
          <ResponsiveContainer width="100%" height="100%">
            <LineChart data={chartData}>
              <XAxis dataKey="time" stroke="#777" />

              <YAxis stroke="#777" />

              <Tooltip />

              <Line
                type="monotone"
                dataKey="distance"
                stroke="#38bdf8"
                strokeWidth={3}
              />
            </LineChart>
          </ResponsiveContainer>
        )}
      </div>

      {/* Analysis Stats */}

      <div
        className="
grid
grid-cols-3
gap-3
mt-5
"
      >
        <div
          className="
bg-black/30
rounded-2xl
border
border-white/10
p-4
"
        >
          <FiTrendingUp
            className="
text-green-400
mb-2
"
          />

          <p
            className="
text-xs
text-gray-400
"
          >
            Distance
          </p>

          <p
            className="
text-white
"
          >
            {totalDistance.toFixed(1)} KM
          </p>
        </div>

        <div
          className="
bg-black/30
rounded-2xl
border
border-white/10
p-4
"
        >
          <FiClock
            className="
text-violet-400
mb-2
"
          />

          <p
            className="
text-xs
text-gray-400
"
          >
            Active Time
          </p>

          <p
            className="
text-white
"
          >
            {activeHours} Stops
          </p>
        </div>

        <div
          className="
bg-black/30
rounded-2xl
border
border-white/10
p-4
"
        >
          <FiNavigation
            className="
text-blue-400
mb-2
"
          />

          <p
            className="
text-xs
text-gray-400
"
          >
            Prediction
          </p>

          <p
            className="
text-white
"
          >
            {predictionAccuracy}%
          </p>
        </div>
      </div>

      {/* AI Insight */}

      <div
        className="
mt-5
rounded-2xl
bg-blue-500/10
border
border-blue-400/20
p-4
"
      >
        <p
          className="
text-xs
text-blue-300
"
        >
          AI INSIGHT
        </p>

        <p
          className="
text-sm
text-white
mt-1
"
        >
          {history.length > 0
            ? "Movement analysis generated from recent location history."
            : "No location history available yet."}
        </p>
      </div>
    </motion.div>
  );
}