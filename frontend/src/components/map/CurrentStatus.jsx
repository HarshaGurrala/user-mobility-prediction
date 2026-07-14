import {
  MapPin,
  Navigation,
  BrainCircuit,
  Gauge
} from "lucide-react";

export default function CurrentStatus() {

  return (

    <div className="bg-slate-900 border border-white/10 rounded-3xl p-6">

      <h2 className="text-white text-2xl font-bold mb-6">

        Current Status

      </h2>

      <div className="space-y-5">

        <div className="flex justify-between">

          <span className="text-slate-400">

            Current City

          </span>

          <span className="text-cyan-400">

            Hyderabad

          </span>

        </div>

        <div className="flex justify-between">

          <span className="text-slate-400">

            Speed

          </span>

          <span className="text-white">

            42 km/h

          </span>

        </div>

        <div className="flex justify-between">

          <span className="text-slate-400">

            Direction

          </span>

          <span className="text-white">

            North-East

          </span>

        </div>

        <div className="flex justify-between">

          <span className="text-slate-400">

            AI Confidence

          </span>

          <span className="text-green-400">

            98.4%

          </span>

        </div>

      </div>

    </div>

  );

}