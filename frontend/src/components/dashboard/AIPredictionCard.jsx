import { motion } from "framer-motion";
import {
  FiNavigation,
  FiMapPin,
  FiCpu,
  FiClock,
  FiTrendingUp,
} from "react-icons/fi";

const AIPredictionCard = ({
  prediction,
  loading = false,
}) => {
  const location = loading
    ? "Loading prediction..."
    : prediction?.location ||
      prediction?.place_name ||
      prediction?.predicted_location ||
      "No prediction available";

  const confidence = loading
    ? "--"
    : prediction?.confidence ?? 92;

  const eta = loading
    ? "--"
    : prediction?.eta ||
      prediction?.estimated_time ||
      "12 min";

  return (
    <motion.div
      initial={{ opacity: 0, y: 30 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.6 }}
      className="
      relative
      overflow-hidden
      rounded-3xl
      border border-white/10
      bg-white/5
      backdrop-blur-2xl
      p-6
      shadow-[0_0_40px_rgba(80,80,255,0.15)]
      "
    >
      {/* AI Glow */}
      <div
        className="
        absolute
        -top-20
        -right-20
        h-40
        w-40
        rounded-full
        bg-blue-500/20
        blur-3xl
        "
      />

      {/* Header */}

      <div className="flex items-center justify-between">
        <div className="flex gap-3 items-center">
          <div
            className="
          p-3
          rounded-2xl
          bg-gradient-to-br
          from-blue-500/30
          to-violet-500/30
          "
          >
            <FiCpu className="text-blue-300 text-xl" />
          </div>

          <div>
            <h2
              className="
            text-white
            font-semibold
            text-lg
            "
            >
              AI Prediction
            </h2>

            <p
              className="
            text-xs
            text-gray-400
            "
            >
              Next movement intelligence
            </p>
          </div>
        </div>

        <motion.div
          animate={{
            scale: [1, 1.15, 1],
          }}
          transition={{
            repeat: Infinity,
            duration: 2,
          }}
          className="
        h-3
        w-3
        rounded-full
        bg-blue-400
        shadow-[0_0_20px_#3b82f6]
        "
        />
      </div>

      {/* Prediction Location */}

      <div
        className="
      mt-6
      rounded-2xl
      bg-black/30
      border
      border-white/10
      p-5
      "
      >
        <div className="flex items-center gap-3">
          <FiNavigation
            className="
          text-violet-400
          text-xl
          "
          />

          <div>
            <p
              className="
            text-xs
            text-gray-400
            "
            >
              Predicted Destination
            </p>

            <h3
              className="
            text-white
            font-medium
            "
            >
              {location}
            </h3>
          </div>
        </div>
      </div>

      {/* Stats */}

      <div
        className="
      grid
      grid-cols-2
      gap-4
      mt-5
      "
      >
        <div
          className="
        rounded-2xl
        bg-white/5
        p-4
        border
        border-white/10
        "
        >
          <div
            className="
          flex
          items-center
          gap-2
          text-gray-400
          text-xs
          "
          >
            <FiTrendingUp />
            Accuracy
          </div>

          <p
            className="
          text-xl
          text-white
          mt-2
          "
          >
            {confidence}
            {!loading && "%"}
          </p>
        </div>

        <div
          className="
        rounded-2xl
        bg-white/5
        p-4
        border
        border-white/10
        "
        >
          <div
            className="
          flex
          items-center
          gap-2
          text-gray-400
          text-xs
          "
          >
            <FiClock />
            ETA
          </div>

          <p
            className="
          text-xl
          text-white
          mt-2
          "
          >
            {eta}
          </p>
        </div>
      </div>

      {/* AI Status */}

      <div
        className="
      mt-5
      flex
      items-center
      gap-2
      text-xs
      text-blue-300
      "
      >
        <FiMapPin />

        {loading
          ? "AI model is analyzing..."
          : "AI model continuously learning movement pattern"}
      </div>
    </motion.div>
  );
};

export default AIPredictionCard;