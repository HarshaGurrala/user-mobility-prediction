import { BrainCircuit } from "lucide-react";

export default function PredictionCard() {

  return (

    <div className="bg-slate-900 border border-white/10 rounded-3xl p-6">

      <div className="flex items-center gap-3">

        <BrainCircuit
          className="text-purple-400"
        />

        <h2 className="text-2xl text-white font-bold">

          AI Prediction

        </h2>

      </div>

      <div className="mt-8">

        <p className="text-slate-400">

          Next Predicted Location

        </p>

        <h1 className="text-3xl text-cyan-400 font-bold mt-2">

          Madhapur

        </h1>

      </div>

      <div className="mt-8">

        <p className="text-slate-400">

          Confidence

        </p>

        <div className="w-full h-3 bg-slate-700 rounded-full mt-3">

          <div className="w-[98%] h-3 rounded-full bg-cyan-400"></div>

        </div>

        <p className="mt-3 text-cyan-400">

          98.4%

        </p>

      </div>

    </div>

  );

}