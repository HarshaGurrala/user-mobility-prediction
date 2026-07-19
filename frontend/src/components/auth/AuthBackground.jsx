import { motion } from "framer-motion";
import { useLocation } from "react-router-dom";
import { createPortal } from "react-dom";
import { FiLock, FiUser, FiUserPlus } from "react-icons/fi";
import { FaKey, FaIdBadge, FaPassport, FaSatelliteDish } from "react-icons/fa";
import { GiArtificialIntelligence } from "react-icons/gi";
import { FaFingerprint } from "react-icons/fa";
import { MdSecurity } from "react-icons/md";
import { TbShield, TbHexagon, TbCube } from "react-icons/tb";

const loginIcons = [
  MdSecurity,
  FiLock,
  FaFingerprint,
  FaKey,
  FaIdBadge,
  FaSatelliteDish,
  TbShield,
  TbHexagon,
  TbCube,
  FaIdBadge,
];

const registerIcons = [
  FiUser,
  FiUserPlus,
  FaIdBadge,
  TbCube,
  TbHexagon,
  MdSecurity,
  GiArtificialIntelligence,
  TbShield,
  FaPassport,
  FaKey,
];

function randomBetween(min, max) {
  return Math.random() * (max - min) + min;
}

export default function AuthBackground() {
  const { pathname } = useLocation();

  const set = pathname.includes("register") || pathname.includes("signup") ? registerIcons : loginIcons;

  return (
    <div className="fixed inset-0 overflow-hidden bg-[#050505]">

      {/* Soft Glows (unchanged) */}
      <motion.div
        animate={{ scale: [1, 1.12, 1], opacity: [0.18, 0.36, 0.18] }}
        transition={{ duration: 9, repeat: Infinity }}
        className="absolute top-[-220px] left-1/2 -translate-x-1/2 w-[650px] h-[650px] rounded-full bg-blue-500/18 blur-[160px]"
      />

      <motion.div
        animate={{ scale: [1.12, 1, 1.12] }}
        transition={{ duration: 11, repeat: Infinity }}
        className="absolute bottom-[-180px] right-[-140px] w-[480px] h-[480px] rounded-full bg-violet-500/16 blur-[140px]"
      />

      {/* Subtle grid */}
      <div
        className="absolute inset-0 opacity-[0.04]"
        style={{
          backgroundImage: `
            linear-gradient(white 1px, transparent 1px),
            linear-gradient(90deg, white 1px, transparent 1px)
          `,
          backgroundSize: "70px 70px",
        }}
      />

      {/* Continuous moving icons rendered via portal so they appear above the card */}
      {createPortal(
        <div className="fixed inset-0 pointer-events-none z-[99999]">
          {new Array(12).fill(0).map((_, i) => {
            const Icon = set[i % set.length];
            const size = Math.round(randomBetween(18, 36));
            const startX = randomBetween(-20, 100);
            const startY = randomBetween(-20, 100);
            const endX = randomBetween(-120, 120);
            const endY = randomBetween(-120, 120);
            const duration = randomBetween(18, 30);
            const delay = randomBetween(0, 6);

            return (
              <motion.div
                key={i}
                initial={{ opacity: 0 }}
                animate={{
                  x: ["0%", endX + "%", "0%", (-endX) + "%"],
                  y: ["0%", endY + "%", (-endY) + "%", "0%"],
                  opacity: [0, 0.35, 0.35, 0],
                  rotate: [0, randomBetween(-20, 20), randomBetween(-20, 20), 0],
                }}
                transition={{
                  duration,
                  delay,
                  repeat: Infinity,
                  repeatType: "loop",
                  ease: "linear",
                }}
                style={{
                  position: "absolute",
                  left: startX + "%",
                  top: startY + "%",
                  transform: "translate(-50%, -50%)",
                  color: "rgba(96,165,250,0.22)",
                  filter: "blur(6px)",
                  mixBlendMode: "screen",
                }}
              >
                <div style={{ width: size, height: size, display: "grid", placeItems: "center" }}>
                  <Icon size={size} />
                </div>
              </motion.div>
            );
          })}
        </div>,
        document.body,
      )}

    </div>
  );
}