import {
  FaMapMarkerAlt,
  FaBatteryThreeQuarters,
  FaSatelliteDish,
  FaArrowRight,
} from "react-icons/fa";
import { useNavigate } from "react-router-dom";


export default function ChildCard({
  name,
  status,
  battery,
  lastSeen,
}) {
  const online = status === "Online";
  const navigate = useNavigate();
  return (
    <div
      className="
      bg-slate-900/70
      backdrop-blur-xl
      border
      border-white/10
      rounded-3xl
      p-6
      transition-all
      duration-300
      hover:scale-[1.02]
      hover:border-cyan-400/30
      hover:shadow-xl
      hover:shadow-cyan-500/10
      "
    >
      {/* Top */}

      <div className="flex justify-between items-center">

        <div>

          <h2 className="text-2xl font-bold text-white">
            {name}
          </h2>

          <div className="flex items-center mt-2">

            <span
              className={`
              h-3
              w-3
              rounded-full
              mr-2
              ${online ? "bg-green-400" : "bg-red-500"}
              `}
            />

            <span
              className={
                online
                  ? "text-green-400"
                  : "text-red-400"
              }
            >
              {status}
            </span>

          </div>

        </div>

        <button
  onClick={() => navigate("/child/1")}
  className="
  bg-gradient-to-r
  from-cyan-500
  to-blue-600
  px-5
  py-3
  rounded-xl
  text-white
  font-semibold
  hover:scale-105
  duration-300
  flex
  items-center
  gap-2
  "
>
  View Live

  <FaArrowRight />
</button>

      </div>

      {/* Info */}

      <div className="grid md:grid-cols-3 gap-5 mt-8">

        <div className="flex items-center gap-3">

          <FaMapMarkerAlt
            className="text-cyan-400"
          />

          <div>

            <p className="text-slate-400 text-sm">
              Last Seen
            </p>

            <p className="text-white">
              {lastSeen}
            </p>

          </div>

        </div>

        <div className="flex items-center gap-3">

          <FaBatteryThreeQuarters
            className="text-green-400"
          />

          <div>

            <p className="text-slate-400 text-sm">
              Battery
            </p>

            <p className="text-white">
              {battery}
            </p>

          </div>

        </div>

        <div className="flex items-center gap-3">

          <FaSatelliteDish
            className="text-cyan-400"
          />

          <div>

            <p className="text-slate-400 text-sm">
              GPS
            </p>

            <p className="text-green-400">
              Active
            </p>

          </div>

        </div>

      </div>

    </div>
  );
}