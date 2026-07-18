import { motion } from "framer-motion";

const stats = [
  {
    number: "98%",
    title: "Prediction Accuracy",
  },
  {
    number: "1M+",
    title: "Locations Processed",
  },
  {
    number: "24/7",
    title: "Guardian Monitoring",
  },
  {
    number: "100K+",
    title: "AI Predictions",
  },
];

export default function Stats() {
  return (
    <section className="bg-[#050505] py-32">

      <div className="max-w-7xl mx-auto px-8">

        <motion.div
          initial={{ opacity: 0 }}
          whileInView={{ opacity: 1 }}
          viewport={{ once: true }}
          className="grid lg:grid-cols-4 gap-8"
        >

          {stats.map((item, index) => (

            <motion.div
              key={index}
              whileHover={{
                y: -8,
              }}
              className="rounded-[32px] border border-white/10 bg-white/[0.04] backdrop-blur-2xl p-10 text-center"
            >

              <h2 className="text-6xl font-bold text-white">

                {item.number}

              </h2>

              <p className="mt-5 text-gray-400 text-lg">

                {item.title}

              </p>

            </motion.div>

          ))}

        </motion.div>

      </div>

    </section>
  );
}