import {
  FaPhoneAlt,
  FaLocationArrow,
  FaCommentDots,
  FaExclamationTriangle,
} from "react-icons/fa";

export default function EmergencyActions() {
  return (
    <div
      className="
      bg-slate-900/70
      backdrop-blur-xl
      border
      border-red-500/20
      rounded-3xl
      p-8
      mt-8
      "
    >
      <h2 className="text-3xl font-bold text-white mb-8">
        Emergency Actions
      </h2>

      <div className="grid md:grid-cols-2 xl:grid-cols-4 gap-6">

        <button
          className="
          bg-red-600
          hover:bg-red-700
          rounded-2xl
          p-6
          text-white
          transition-all
          duration-300
          hover:scale-105
          flex
          flex-col
          items-center
          gap-4
          "
        >
          <FaExclamationTriangle size={35} />
          <span className="font-semibold">
            Send SOS
          </span>
        </button>

        <button
          className="
          bg-slate-800
          hover:bg-cyan-600
          rounded-2xl
          p-6
          text-white
          transition-all
          duration-300
          hover:scale-105
          flex
          flex-col
          items-center
          gap-4
          "
        >
          <FaPhoneAlt size={35} />
          <span className="font-semibold">
            Call Child
          </span>
        </button>

        <button
          className="
          bg-slate-800
          hover:bg-green-600
          rounded-2xl
          p-6
          text-white
          transition-all
          duration-300
          hover:scale-105
          flex
          flex-col
          items-center
          gap-4
          "
        >
          <FaLocationArrow size={35} />
          <span className="font-semibold">
            Navigate
          </span>
        </button>

        <button
          className="
          bg-slate-800
          hover:bg-blue-600
          rounded-2xl
          p-6
          text-white
          transition-all
          duration-300
          hover:scale-105
          flex
          flex-col
          items-center
          gap-4
          "
        >
          <FaCommentDots size={35} />
          <span className="font-semibold">
            Send Message
          </span>
        </button>

      </div>
    </div>
  );
}