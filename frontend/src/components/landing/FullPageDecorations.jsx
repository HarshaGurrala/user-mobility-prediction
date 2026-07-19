import { motion, useScroll, useTransform, useSpring, useVelocity } from "framer-motion";
import { FiMapPin, FiCompass, FiGlobe } from "react-icons/fi";
import { FaMobileAlt, FaSatelliteDish } from "react-icons/fa";
import { GiArtificialIntelligence, GiRadarDish } from "react-icons/gi";
import { TbHexagon, TbCube, TbCpu, TbWorldLatitude } from "react-icons/tb";
import { MdSecurity } from "react-icons/md";

const iconItems = [
  {
    icon: <FiGlobe size={32} />,
    position: "top-8 left-8",
    startX: -220,
    startY: -36,
    endX: 320,
    endY: 24,
    startRotate: -14,
    endRotate: 14,
  },
  {
    icon: <TbWorldLatitude size={32} />,
    position: "top-[18vh] right-8",
    startX: 220,
    startY: -24,
    endX: -320,
    endY: 18,
    startRotate: 12,
    endRotate: -12,
  },
  {
    icon: <FiMapPin size={32} />,
    position: "top-[32vh] left-10",
    startX: -200,
    startY: 24,
    endX: 300,
    endY: -28,
    startRotate: -12,
    endRotate: 12,
  },
  {
    icon: <FaMobileAlt size={32} />,
    position: "top-[44vh] right-14",
    startX: 200,
    startY: 28,
    endX: -300,
    endY: 22,
    startRotate: 14,
    endRotate: -14,
  },
  {
    icon: <FaSatelliteDish size={32} />,
    position: "top-[56vh] left-14",
    startX: -220,
    startY: 22,
    endX: 320,
    endY: -20,
    startRotate: -10,
    endRotate: 10,
  },
  {
    icon: <GiRadarDish size={32} />,
    position: "top-[68vh] right-16",
    startX: 220,
    startY: -28,
    endX: -320,
    endY: 18,
    startRotate: 10,
    endRotate: -10,
  },
  {
    icon: <FiCompass size={32} />,
    position: "top-[78vh] left-[18%]",
    startX: -180,
    startY: 30,
    endX: 260,
    endY: 26,
    startRotate: -8,
    endRotate: 8,
  },
  {
    icon: <GiArtificialIntelligence size={32} />,
    position: "bottom-[24vh] right-[22%]",
    startX: 180,
    startY: 32,
    endX: -260,
    endY: -28,
    startRotate: 12,
    endRotate: -12,
  },
  {
    icon: <MdSecurity size={32} />,
    position: "bottom-12 left-[55%]",
    startX: -180,
    startY: 28,
    endX: 280,
    endY: -24,
    startRotate: -14,
    endRotate: 14,
  },
  {
    icon: <TbHexagon size={32} />,
    position: "top-[26vh] left-[48%]",
    startX: -160,
    startY: -28,
    endX: 260,
    endY: 28,
    startRotate: -8,
    endRotate: 8,
  },
  {
    icon: <TbCube size={32} />,
    position: "bottom-[18vh] left-[36%]",
    startX: -170,
    startY: 30,
    endX: 260,
    endY: -26,
    startRotate: -10,
    endRotate: 10,
  },
  {
    icon: <TbCpu size={32} />,
    position: "top-[22vh] right-[35%]",
    startX: 170,
    startY: -28,
    endX: -260,
    endY: 28,
    startRotate: 12,
    endRotate: -12,
  },
];

export default function FullPageDecorations() {
  const { scrollYProgress } = useScroll();
  const scrollVelocity = useVelocity(scrollYProgress);
  const smoothScroll = useSpring(scrollYProgress, { damping: 30, stiffness: 90 });
  const motionScroll = useSpring(smoothScroll, { damping: 36, stiffness: 70 });
  const velSpring = useSpring(scrollVelocity, { damping: 40, stiffness: 50 });
  const showDuringScroll = useTransform(velSpring, (latest) => {
    const v = Math.min(Math.abs(latest) * 1.2 + 0.06, 1);
    return v;
  });

  return (
    <div className="pointer-events-none fixed inset-0 z-[9999] overflow-hidden">
      {iconItems.map((item, index) => {
        const x = useTransform(motionScroll, [0, 1], [item.startX, item.endX]);
        const y = useTransform(motionScroll, [0, 1], [item.startY, item.endY]);
        const rotate = useTransform(motionScroll, [0, 1], [item.startRotate, item.endRotate]);
        const opacity = useTransform(
          motionScroll,
          [0, 0.08, 0.22, 0.92, 1],
          [0, 0.25, 0.35, 0.35, 0],
        );
        const activeOpacity = useTransform([opacity, showDuringScroll], ([latestOpacity, latestScroll]) => latestOpacity * Math.max(latestScroll, 0.12));

        return (
          <motion.div key={index} style={{ x, y, rotate, opacity: activeOpacity }} className={`absolute z-[9999] ${item.position}`}>
            <div className="relative flex items-center justify-center w-16 h-16 rounded-full bg-gradient-to-br from-slate-950/40 via-slate-900/40 to-slate-800/40 shadow-[0_28px_80px_rgba(14,165,233,0.22)]">
              <div className="absolute inset-0 rounded-full bg-gradient-to-br from-cyan-400/20 via-transparent to-blue-300/10 blur-xl" />
              <div className="relative z-10 text-cyan-200">{item.icon}</div>
            </div>
          </motion.div>
        );
      })}
    </div>
  );
}
