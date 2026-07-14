import { ShieldCheck, Navigation } from "lucide-react";

export default function SafeLocationCard({
  title,
  distance,
  type
}) {
  return (
    <div className="bg-slate-900 border border-white/10 rounded-3xl p-5 hover:border-cyan-400 transition">

      <div className="flex justify-between">

        <ShieldCheck
          className="text-green-400"
          size={34}
        />

        <span className="text-cyan-400">
          {distance}
        </span>

      </div>

      <h2 className="text-white text-xl font-bold mt-5">
        {title}
      </h2>

      <p className="text-slate-400 mt-2">
        {type}
      </p>

      <button className="mt-6 w-full bg-cyan-500 hover:bg-cyan-400 rounded-xl py-3 font-semibold text-black flex items-center justify-center gap-2">

        <Navigation size={18}/>

        Navigate

      </button>

    </div>
  );
}