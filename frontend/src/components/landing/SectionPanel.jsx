import { motion } from "framer-motion";

export default function SectionPanel({ id, title, eyebrow, description, children }) {
  return (
    <section id={id} className="bg-[#050505] py-24">
      <div className="max-w-7xl mx-auto px-8">
        <motion.div
          initial={{ opacity: 0, y: 40 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          className="rounded-[36px] border border-white/10 bg-white/[0.04] backdrop-blur-2xl p-10 lg:p-16 shadow-[0_0_40px_rgba(0,0,0,0.35)]"
        >
          <p className="text-blue-400 uppercase tracking-[6px] text-sm">{eyebrow}</p>
          <h2 className="mt-6 text-4xl lg:text-5xl font-bold text-white">{title}</h2>
          <p className="mt-6 max-w-3xl text-lg leading-8 text-gray-400">{description}</p>
          {children}
        </motion.div>
      </div>
    </section>
  );
}
