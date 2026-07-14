import { CloudSun } from "lucide-react";

export default function WeatherCard() {
  return (
    <div className="bg-slate-900 border border-white/10 rounded-3xl p-6">

      <CloudSun
        size={40}
        className="text-yellow-400"
      />

      <h2 className="text-white text-2xl font-bold mt-4">

        Weather

      </h2>

      <h1 className="text-5xl text-cyan-400 font-bold mt-6">

        30°C

      </h1>

      <p className="text-slate-400 mt-2">

        Hyderabad

      </p>

      <p className="text-green-400 mt-5">

        Clear Sky

      </p>

    </div>
  );
}