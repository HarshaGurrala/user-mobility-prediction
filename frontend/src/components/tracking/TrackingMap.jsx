import {
  FaMapMarkerAlt,
  FaHome,
  FaUniversity,
  FaRoute,
} from "react-icons/fa";

export default function TrackingMap() {
  return (
    <div
      className="
      bg-slate-900/70
      backdrop-blur-xl
      border
      border-white/10
      rounded-3xl
      p-8
      mt-8
      hover:border-cyan-400/30
      transition-all
      duration-300
      "
    >
      {/* Header */}

      <div className="flex justify-between items-center mb-6">

        <div>

          <h2 className="text-3xl font-bold text-white">
            Live Tracking
          </h2>

          <p className="text-slate-400 mt-2">
            Real-time child location
          </p>

        </div>

        <span className="px-4 py-2 rounded-full bg-green-500/20 text-green-400">
          LIVE
        </span>

      </div>

      {/* Google Map */}

      <div
        className="
        h-[500px]
        rounded-3xl
        overflow-hidden
        border
        border-white/10
        relative
        "
      >
        <iframe
          title="Google Map"
          width="100%"
          height="100%"
          style={{ border: 0 }}
          loading="lazy"
          allowFullScreen
          src="https://maps.google.com/maps?q=16.5062,80.6480&z=15&output=embed"
        />

        <div
          className="
          absolute
          top-5
          left-5
          bg-slate-900/80
          backdrop-blur-xl
          rounded-2xl
          px-5
          py-3
          border
          border-white/10
          "
        >
          <div className="flex items-center gap-2 text-cyan-400">

            <FaMapMarkerAlt />

            Current Location

          </div>

        </div>

      </div>

      {/* Bottom */}

      <div className="grid md:grid-cols-3 gap-6 mt-8">

        <div
          className="
          bg-slate-800/70
          rounded-2xl
          p-5
          flex
          items-center
          gap-4
          "
        >

          <FaHome className="text-green-400 text-2xl" />

          <div>

            <p className="text-slate-400">
              Home
            </p>

            <h3 className="text-white">
              Safe Zone
            </h3>

          </div>

        </div>

        <div
          className="
          bg-slate-800/70
          rounded-2xl
          p-5
          flex
          items-center
          gap-4
          "
        >

          <FaUniversity className="text-cyan-400 text-2xl" />

          <div>

            <p className="text-slate-400">
              College
            </p>

            <h3 className="text-white">
              Destination
            </h3>

          </div>

        </div>

        <div
          className="
          bg-slate-800/70
          rounded-2xl
          p-5
          flex
          items-center
          gap-4
          "
        >

          <FaRoute className="text-yellow-400 text-2xl" />

          <div>

            <p className="text-slate-400">
              Route
            </p>

            <h3 className="text-white">
              Tracking Enabled
            </h3>

          </div>

        </div>

      </div>

    </div>
  );
}