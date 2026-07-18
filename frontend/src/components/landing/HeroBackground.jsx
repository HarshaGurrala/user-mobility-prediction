import { motion } from "framer-motion";

export default function HeroBackground() {
  return (
    <div className="absolute inset-0 overflow-hidden pointer-events-none">

      {/* Main Gradient */}

      <div className="absolute inset-0 bg-[#050505]" />


      {/* Apple Vision Style Glow */}

      <motion.div
        animate={{
          scale: [1, 1.2, 1],
          opacity: [0.25, 0.45, 0.25],
        }}
        transition={{
          duration: 8,
          repeat: Infinity,
        }}
        className="absolute top-[-250px] left-1/2 -translate-x-1/2 w-[900px] h-[900px] rounded-full bg-blue-500/20 blur-[180px]"
      />


      <motion.div
        animate={{
          scale: [1.2, 1, 1.2],
        }}
        transition={{
          duration: 10,
          repeat: Infinity,
        }}
        className="absolute bottom-[-200px] right-[-200px] w-[600px] h-[600px] rounded-full bg-violet-500/20 blur-[160px]"
      />


      {/* Soft Grid */}

      <div
        className="absolute inset-0 opacity-[0.03]"
        style={{
          backgroundImage:
          `
          linear-gradient(#fff 1px, transparent 1px),
          linear-gradient(90deg,#fff 1px, transparent 1px)
          `,
          backgroundSize:"80px 80px"
        }}
      />


      {/* Moving Light */}

      <motion.div
        animate={{
          x:["-100%","100%"]
        }}
        transition={{
          duration:12,
          repeat:Infinity,
          ease:"linear"
        }}
        className="
        absolute
        top-1/3
        w-[500px]
        h-[1px]
        bg-gradient-to-r
        from-transparent
        via-white/30
        to-transparent
        "
      />


    </div>
  );
}