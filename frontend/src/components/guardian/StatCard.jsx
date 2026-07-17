export default function StatCard({
  icon,
  title,
  value,
  color,
}) {
  return (
    <div
      className="
      group
      bg-slate-900/70
      backdrop-blur-xl
      border
      border-white/10
      rounded-3xl
      p-6
      transition-all
      duration-300
      hover:scale-[1.03]
      hover:border-cyan-400/30
      hover:shadow-2xl
      hover:shadow-cyan-500/10
      "
    >
      <div
        className={`
        w-14
        h-14
        rounded-2xl
        flex
        items-center
        justify-center
        text-white
        text-2xl
        ${color}
        `}
      >
        {icon}
      </div>

      <p className="text-slate-400 mt-6">
        {title}
      </p>

      <h2 className="text-white text-4xl font-bold mt-2">
        {value}
      </h2>
    </div>
  );
}