import {
  FaMapMarkerAlt,
  FaHome,
  FaUniversity,
  FaWalking,
} from "react-icons/fa";

export default function TimelineSection() {
  const timeline = [
    {
      time: "09:35 AM",
      title: "Current Location Updated",
      location: "Near Benz Circle",
      icon: <FaMapMarkerAlt />,
      color: "bg-cyan-500",
    },
    {
      time: "09:10 AM",
      title: "Started Journey",
      location: "Left College",
      icon: <FaWalking />,
      color: "bg-yellow-500",
    },
    {
      time: "08:55 AM",
      title: "Exited Safe Zone",
      location: "AITAM College",
      icon: <FaUniversity />,
      color: "bg-red-500",
    },
    {
      time: "08:15 AM",
      title: "Entered Safe Zone",
      location: "Home",
      icon: <FaHome />,
      color: "bg-green-500",
    },
  ];

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
      "
    >
      <h2 className="text-3xl font-bold text-white mb-8">
        Movement Timeline
      </h2>

      <div className="relative border-l border-slate-700 ml-5">

        {timeline.map((item, index) => (

          <div
            key={index}
            className="mb-10 ml-8"
          >

            <span
              className={`
              absolute
              -left-4
              h-8
              w-8
              rounded-full
              flex
              items-center
              justify-center
              text-white
              ${item.color}
              `}
            >
              {item.icon}
            </span>

            <p className="text-cyan-400 text-sm">
              {item.time}
            </p>

            <h3 className="text-white text-xl font-semibold mt-1">
              {item.title}
            </h3>

            <p className="text-slate-400 mt-1">
              {item.location}
            </p>

          </div>

        ))}

      </div>

    </div>
  );
}