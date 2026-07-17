import {
  FaArrowLeft,
  FaCircle,
  FaBatteryThreeQuarters,
  FaSatelliteDish,
  FaMapMarkerAlt,
} from "react-icons/fa";

import { useNavigate } from "react-router-dom";

export default function TrackingHeader() {

  const navigate = useNavigate();

  return (

    <div
      className="
      bg-slate-900/70
      backdrop-blur-xl
      border
      border-white/10
      rounded-3xl
      p-8
      mb-8
      "
    >

      {/* Top */}

      <div className="flex justify-between items-center">

        <button
          onClick={() => navigate(-1)}
          className="
          h-12
          w-12
          rounded-full
          bg-slate-800
          hover:bg-cyan-600
          duration-300
          flex
          items-center
          justify-center
          text-white
          "
        >
          <FaArrowLeft />
        </button>

        <span
          className="
          bg-green-500/20
          text-green-400
          px-5
          py-2
          rounded-full
          flex
          items-center
          gap-2
          "
        >
          <FaCircle size={10} />

          Online

        </span>

      </div>

      {/* User */}

      <div className="flex items-center mt-8">

        <div
          className="
          h-24
          w-24
          rounded-full
          bg-gradient-to-r
          from-cyan-500
          to-blue-600
          flex
          items-center
          justify-center
          text-white
          text-4xl
          font-bold
          "
        >
          H
        </div>

        <div className="ml-6">

          <h1 className="text-4xl font-bold text-white">
            Harsha Gurrala
          </h1>

          <p className="text-slate-400 mt-2">
            Child Account
          </p>

        </div>

      </div>

      {/* Status */}

      <div className="grid md:grid-cols-3 gap-6 mt-10">

        <div
          className="
          bg-slate-800/60
          rounded-2xl
          p-5
          "
        >

          <div className="flex items-center gap-3">

            <FaMapMarkerAlt className="text-cyan-400" />

            <div>

              <p className="text-slate-400 text-sm">

                Current Location

              </p>

              <h3 className="text-white">

                Vijayawada

              </h3>

            </div>

          </div>

        </div>

        <div
          className="
          bg-slate-800/60
          rounded-2xl
          p-5
          "
        >

          <div className="flex items-center gap-3">

            <FaBatteryThreeQuarters
              className="text-green-400"
            />

            <div>

              <p className="text-slate-400 text-sm">

                Battery

              </p>

              <h3 className="text-white">

                82%

              </h3>

            </div>

          </div>

        </div>

        <div
          className="
          bg-slate-800/60
          rounded-2xl
          p-5
          "
        >

          <div className="flex items-center gap-3">

            <FaSatelliteDish
              className="text-cyan-400"
            />

            <div>

              <p className="text-slate-400 text-sm">

                GPS

              </p>

              <h3 className="text-green-400">

                Active

              </h3>

            </div>

          </div>

        </div>

      </div>

    </div>

  );

}