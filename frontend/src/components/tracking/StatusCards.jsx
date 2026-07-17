import {
  FaBatteryThreeQuarters,
  FaWifi,
  FaSatelliteDish,
  FaTachometerAlt,
} from "react-icons/fa";

export default function StatusCards() {
  const cards = [
    {
      title: "Battery",
      value: "82%",
      color: "text-green-400",
      icon: <FaBatteryThreeQuarters size={28} />,
    },
    {
      title: "Internet",
      value: "Excellent",
      color: "text-cyan-400",
      icon: <FaWifi size={28} />,
    },
    {
      title: "GPS",
      value: "Active",
      color: "text-yellow-400",
      icon: <FaSatelliteDish size={28} />,
    },
    {
      title: "Speed",
      value: "34 km/h",
      color: "text-pink-400",
      icon: <FaTachometerAlt size={28} />,
    },
  ];

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-6 mt-8">

      {cards.map((card, index) => (

        <div
          key={index}
          className="
          bg-slate-900/70
          backdrop-blur-xl
          border
          border-white/10
          rounded-3xl
          p-6
          hover:border-cyan-400/30
          hover:scale-105
          transition-all
          duration-300
          "
        >

          <div className={card.color}>
            {card.icon}
          </div>

          <p className="text-slate-400 mt-5">
            {card.title}
          </p>

          <h2 className="text-white text-3xl font-bold mt-2">
            {card.value}
          </h2>

        </div>

      ))}

    </div>
  );
}