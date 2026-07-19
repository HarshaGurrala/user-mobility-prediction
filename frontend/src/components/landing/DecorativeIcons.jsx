import { motion } from "framer-motion";
import { FiMapPin, FiCompass, FiGlobe, FiTrendingUp } from "react-icons/fi";

const iconCards = [
  {
    icon: <FiGlobe size={28} />,
    className: "top-24 left-10",
    delay: 0,
    rotate: "rotate-[12deg]",
  },
  {
    icon: <FiCompass size={28} />,
    className: "top-72 right-24",
    delay: 0.4,
    rotate: "-rotate-[8deg]",
  },
  {
    icon: <FiMapPin size={28} />,
    className: "top-44 left-[34rem]",
    delay: 0.2,
    rotate: "rotate-[8deg]",
  },
  {
    icon: <FiTrendingUp size={28} />,
    className: "bottom-40 right-12",
    delay: 0.6,
    rotate: "-rotate-[12deg]",
  },
];

export default function DecorativeIcons() {
  return (
    <div className="pointer-events-none absolute inset-0 overflow-hidden">
      {iconCards.map((item, index) => (
        <motion.div
          key={index}
          initial={{ opacity: 0, y: 30, scale: 0.95 }}
          animate={{ opacity: 1, y: [0, -18, 0], scale: [0.95, 1, 0.95] }}
          transition={{
            delay: item.delay,
            duration: 6,
            repeat: Infinity,
            ease: "easeInOut",
          }}
          className={`${item.className} absolute z-10`}
        >
          <div
            className={`w-24 h-24 rounded-[36px] border border-white/10 bg-white/5 backdrop-blur-3xl shadow-[0_24px_80px_rgba(0,0,0,0.35)] flex items-center justify-center text-blue-400 ${item.rotate}`}
          >
            {item.icon}
          </div>
        </motion.div>
      ))}
    </div>
  );
}
