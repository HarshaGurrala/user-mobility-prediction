import { FaBell, FaUserCircle, FaShieldAlt } from "react-icons/fa";

export default function DashboardHeader() {
  return (
    <div className="mb-10">

      {/* Top Row */}

      <div className="flex justify-between items-center">

        <div>

          <h1 className="text-4xl font-bold text-white">
            Guardian Dashboard
          </h1>

          <p className="text-slate-400 mt-2">
            AI Powered User Mobility Prediction System
          </p>

        </div>

        <div className="flex items-center gap-5">

          <button
            className="
            h-12
            w-12
            rounded-full
            bg-slate-900/70
            border
            border-white/10
            flex
            items-center
            justify-center
            text-cyan-400
            hover:scale-105
            duration-300
            "
          >
            <FaBell size={18} />
          </button>

          <button
            className="
            h-12
            w-12
            rounded-full
            bg-slate-900/70
            border
            border-white/10
            flex
            items-center
            justify-center
            text-cyan-400
            hover:scale-105
            duration-300
            "
          >
            <FaUserCircle size={22} />
          </button>

        </div>

      </div>

      {/* Welcome Card */}

      <div
        className="
        mt-8
        bg-slate-900/70
        border
        border-white/10
        rounded-3xl
        p-8
        backdrop-blur-xl
        "
      >

        <div className="flex justify-between items-center">

          <div>

            <h2 className="text-white text-3xl font-bold">
              Welcome Back 👋
            </h2>

            <p className="text-slate-400 mt-2">
              Harsha
            </p>

          </div>

          <div
            className="
            bg-cyan-500/10
            border
            border-cyan-500/20
            rounded-2xl
            px-6
            py-4
            "
          >

            <div className="flex items-center gap-3">

              <FaShieldAlt className="text-cyan-400" />

              <div>

                <p className="text-slate-400 text-sm">
                  Guardian Code
                </p>

                <h3 className="text-cyan-400 text-xl font-bold">
                  GUARD688183
                </h3>

              </div>

            </div>

          </div>

        </div>

      </div>

    </div>
  );
}