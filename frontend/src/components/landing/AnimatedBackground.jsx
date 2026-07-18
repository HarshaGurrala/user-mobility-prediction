import { motion } from "framer-motion";

export default function AnimatedBackground() {
  return (
    <>
      {/* Main Background */}

      <div className="absolute inset-0 bg-[#050505]" />

      {/* Top Glow */}

      <div className="absolute -top-60 left-1/2 -translate-x-1/2 w-[900px] h-[900px] rounded-full bg-blue-500/10 blur-[180px]" />

      {/* Left Glow */}

      <div className="absolute top-60 -left-40 w-[500px] h-[500px] rounded-full bg-violet-500/10 blur-[150px]" />

      {/* Right Glow */}

      <div className="absolute bottom-20 -right-32 w-[450px] h-[450px] rounded-full bg-cyan-500/10 blur-[150px]" />

      {/* Grid */}

      <div
        className="absolute inset-0 opacity-[0.04]"
        style={{
          backgroundImage: `
            linear-gradient(to right, white 1px, transparent 1px),
            linear-gradient(to bottom, white 1px, transparent 1px)
          `,
          backgroundSize: "60px 60px",
        }}
      />

      {/* Animated Lines */}

      <motion.div
        animate={{
          x: [-300, 1200],
        }}
        transition={{
          duration: 10,
          repeat: Infinity,
          ease: "linear",
        }}
        className="absolute top-40 w-80 h-[1px] bg-gradient-to-r from-transparent via-cyan-400 to-transparent"
      />

      <motion.div
        animate={{
          x: [1200, -300],
        }}
        transition={{
          duration: 14,
          repeat: Infinity,
          ease: "linear",
        }}
        className="absolute bottom-56 w-72 h-[1px] bg-gradient-to-r from-transparent via-violet-400 to-transparent"
      />
    </>
  );
}