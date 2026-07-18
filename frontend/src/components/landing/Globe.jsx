import { motion } from "framer-motion";

export default function Globe() {
  return (
    <div className="relative flex items-center justify-center w-full h-full">

      {/* Outer Glow */}

      <div className="absolute w-72 h-72 rounded-full bg-blue-500/20 blur-[90px]" />

      {/* Globe */}

      <motion.div
        animate={{
          rotate: 360,
        }}
        transition={{
          duration: 60,
          repeat: Infinity,
          ease: "linear",
        }}
        className="relative w-72 h-72 rounded-full overflow-hidden border border-white/10 bg-gradient-to-br from-[#111827] via-[#172554] to-[#020617]"
      >

        {/* Latitude */}

        {[...Array(8)].map((_, i) => (

          <div
            key={i}
            className="absolute left-0 right-0 border-t border-cyan-400/10"
            style={{
              top: `${12 + i * 11}%`,
            }}
          />

        ))}

        {/* Longitude */}

        {[...Array(10)].map((_, i) => (

          <div
            key={i}
            className="absolute top-0 bottom-0 border-l border-cyan-400/10"
            style={{
              left: `${10 + i * 8}%`,
            }}
          />

        ))}

      </motion.div>

      {/* Orbit */}

      <motion.div
        animate={{
          rotate: -360,
        }}
        transition={{
          duration: 25,
          repeat: Infinity,
          ease: "linear",
        }}
        className="absolute w-[360px] h-[360px] rounded-full border border-white/10"
      >

        <div className="absolute left-1/2 -translate-x-1/2 -top-2">

          <div className="w-4 h-4 rounded-full bg-cyan-400 shadow-[0_0_25px_#22d3ee]" />

        </div>

      </motion.div>

    </div>
  );
}