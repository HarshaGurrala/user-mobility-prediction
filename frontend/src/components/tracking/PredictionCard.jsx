import {
  FaBrain,
  FaMapMarkerAlt,
  FaBullseye,
  FaClock,
} from "react-icons/fa";

import usePrediction from "../../hooks/usePrediction";

export default function PredictionCard() {

  const prediction = usePrediction();

  if (!prediction) {

    return (

      <div
        className="
        bg-slate-900/70
        border
        border-white/10
        rounded-3xl
        p-6
        text-white
        "
      >

        Loading AI Prediction...

      </div>

    );

  }

  return (

    <div
      className="
      bg-slate-900/70
      backdrop-blur-xl
      border
      border-cyan-500/20
      rounded-3xl
      p-6
      shadow-xl
      shadow-cyan-500/10
      "
    >

      <div className="flex items-center gap-3 mb-6">

        <FaBrain className="text-cyan-400 text-3xl"/>

        <div >

          <h2 className="text-2xl font-bold text-white">
            AI Prediction
          </h2>

          <p className="text-slate-400">
            Next Destination Analysis
          </p>

        </div>

      </div>

      <div className="space-y-8">

        <div className="flex justify-between">

          <div className="flex items-center gap-3">

            <FaMapMarkerAlt className="text-red-400"/>

            <span className="text-slate-300">
              Destination
            </span>

          </div>

          <span className="text-white font-semibold">

            {prediction.predicted_place}

          </span>

        </div>

        <div className="flex justify-between">

          <div className="flex items-center gap-2">

            <FaBullseye className="text-green-400"/>

            <span className="text-slate-300">
              Confidence
            </span>

          </div>

          <span className="text-green-400 font-bold">

            {prediction.confidence}%

          </span>

        </div>

        <div className="flex justify-between">

          <div className="flex items-center gap-2">

            <FaClock className="text-yellow-400"/>

            <span className="text-slate-300">
              ETA
            </span>

          </div>

          <span className="text-white">

            {prediction.eta} mins

          </span>

        </div>

      </div>

    </div>

  );

}