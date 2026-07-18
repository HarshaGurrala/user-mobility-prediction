import { motion } from "framer-motion";

const particles = Array.from({ length: 40 });

export default function FloatingParticles() {
  return (
    <div className="absolute inset-0 overflow-hidden pointer-events-none">

      {particles.map((_, i) => (

        <motion.div
          key={i}
          initial={{
            opacity: 0,
            x: Math.random() * 1800,
            y: Math.random() * 1000,
          }}
          animate={{
            opacity: [0.2, 1, 0.2],
            y: [
              Math.random() * 1000,
              Math.random() * 1000 - 200,
              Math.random() * 1000,
            ],
          }}
          transition={{
            duration: 5 + Math.random() * 10,
            repeat: Infinity,
            ease: "linear",
          }}
          className="absolute w-1 h-1 rounded-full bg-white"
        />

      ))}

    </div>
  );
}