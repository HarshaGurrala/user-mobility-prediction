import {
  FaCheckCircle,
  FaExclamationTriangle,
  FaInfoCircle,
} from "react-icons/fa";

export default function AlertCard({
  title,
  time,
  type,
}) {

  const styles = {
    success: {
      icon: <FaCheckCircle />,
      color: "text-green-400",
      bg: "bg-green-500/10",
    },
    warning: {
      icon: <FaExclamationTriangle />,
      color: "text-yellow-400",
      bg: "bg-yellow-500/10",
    },
    info: {
      icon: <FaInfoCircle />,
      color: "text-cyan-400",
      bg: "bg-cyan-500/10",
    },
  };

  const current = styles[type];

  return (
    <div
      className="
      flex
      items-center
      justify-between
      bg-slate-900/70
      border
      border-white/10
      rounded-2xl
      p-5
      hover:border-cyan-500/30
      transition
      duration-300
      "
    >
      <div className="flex items-center gap-4">

        <div
          className={`
          h-12
          w-12
          rounded-full
          flex
          items-center
          justify-center
          ${current.bg}
          ${current.color}
          `}
        >
          {current.icon}
        </div>

        <div>

          <h3 className="text-white font-semibold">
            {title}
          </h3>

          <p className="text-slate-400 text-sm">
            {time}
          </p>

        </div>

      </div>
    </div>
  );
}