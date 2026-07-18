import { motion } from "framer-motion";

export default function FeatureCard({
  icon,
  title,
  description,
}) {
  return (
    <motion.div
      whileHover={{
        y: -8,
        scale: 1.03,
      }}
      className="bg-white/5 backdrop-blur-xl border border-white/10 rounded-3xl p-8 transition-all"
    >
      <div className="text-5xl mb-6">
        {icon}
      </div>

      <h3 className="text-white text-2xl font-bold mb-4">
        {title}
      </h3>

      <p className="text-slate-400 leading-7">
        {description}
      </p>
    </motion.div>
  );
}