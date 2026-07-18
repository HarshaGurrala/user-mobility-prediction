import { motion } from "framer-motion";

export default function AuthBackground() {
  return (
    <div className="fixed inset-0 overflow-hidden bg-[#050505]">

      {/* Blue Glow */}

      <motion.div
        animate={{
          scale:[1,1.2,1],
          opacity:[0.2,0.4,0.2]
        }}
        transition={{
          duration:8,
          repeat:Infinity
        }}
        className="
        absolute
        top-[-250px]
        left-1/2
        -translate-x-1/2
        w-[700px]
        h-[700px]
        rounded-full
        bg-blue-500/20
        blur-[180px]
        "
      />


      {/* Violet Glow */}

      <motion.div
        animate={{
          scale:[1.2,1,1.2]
        }}
        transition={{
          duration:10,
          repeat:Infinity
        }}
        className="
        absolute
        bottom-[-200px]
        right-[-150px]
        w-[500px]
        h-[500px]
        rounded-full
        bg-violet-500/20
        blur-[160px]
        "
      />


      {/* Grid */}

      <div
        className="
        absolute
        inset-0
        opacity-[0.04]
        "
        style={{
          backgroundImage:`
          linear-gradient(
          white 1px,
          transparent 1px
          ),
          linear-gradient(
          90deg,
          white 1px,
          transparent 1px
          )
          `,
          backgroundSize:"70px 70px"
        }}
      />


    </div>
  );
}