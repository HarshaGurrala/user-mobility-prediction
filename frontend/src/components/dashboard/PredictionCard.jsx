import { BrainCircuit, MapPin, Clock3, Shield } from "lucide-react";

export default function PredictionCard() {
  return (
    <div className="bg-slate-900 border border-white/10 rounded-3xl p-6">

      <div className="flex items-center gap-3 mb-6">

        <BrainCircuit className="text-purple-400" />

        <h2 className="text-2xl text-white font-bold">
          AI Prediction
        </h2>

      </div>

      <div className="space-y-5">

        <div>

          <p className="text-slate-400">
            Next Location
          </p>

          <h2 className="text-cyan-400 text-3xl font-bold">
            Madhapur
          </h2>

        </div>

        <div className="flex justify-between">

          <div>

            <p className="text-slate-400 text-sm">
              Confidence
            </p>

            <h3 className="text-green-400 font-bold">
              98.4%
            </h3>

          </div>

          <div>

            <p className="text-slate-400 text-sm">
              ETA
            </p>

            <h3 className="text-white">
              12 min
            </h3>

          </div>

        </div>

        <div className="w-full h-3 rounded-full bg-slate-700">

          <div className="w-[98%] h-full rounded-full bg-gradient-to-r from-cyan-400 to-purple-500"></div>

        </div>

        <div className="flex items-center justify-between mt-4">

          <MapPin className="text-cyan-400" />

          <Clock3 className="text-yellow-400" />

          <Shield className="text-green-400" />

        </div>

      </div>

    </div>
  );
}