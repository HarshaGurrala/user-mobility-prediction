import {
  FaBrain,
  FaMapMarkerAlt,
  FaBullseye,
  FaClock,
  FaRobot,
} from "react-icons/fa";

import usePrediction from "../../hooks/usePrediction";

export default function AIPredictionCard() {

  const prediction = usePrediction();

  if (!prediction) {

    return (

      <div
        className="
        bg-slate-900/70
        backdrop-blur-xl
        border
        border-white/10
        rounded-3xl
        p-8
        text-center
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
      p-8
      shadow-xl
      shadow-cyan-500/10
      "
    >

      <div className="flex items-center gap-4">

        <div
          className="
          h-16
          w-16
          rounded-2xl
          bg-cyan-500/20
          flex
          items-center
          justify-center
          "
        >

          <FaBrain
            className="text-cyan-400 text-3xl"
          />

        </div>

        <div>

          <h2 className="text-2xl font-bold text-white">

            AI Prediction

          </h2>

          <p className="text-slate-400">

            Machine Learning Analysis

          </p>

        </div>

      </div>

      <div className="space-y-6 mt-8">

        <div className="flex justify-between">

          <div className="flex items-center gap-3">

            <FaRobot className="text-cyan-400" />

            <span className="text-slate-300">

              Destination

            </span>

          </div>

          <span className="text-white font-semibold">

            {prediction.predicted_place}

          </span>

        </div>

        <div className="flex justify-between">

          <div className="flex items-center gap-3">

            <FaBullseye className="text-green-400" />

            <span className="text-slate-300">

              Confidence

            </span>

          </div>

          <span className="text-green-400 font-bold">

            {prediction.confidence}%

          </span>

        </div>

        <div className="flex justify-between">

          <div className="flex items-center gap-3">

            <FaClock className="text-yellow-400" />

            <span className="text-slate-300">

              ETA

            </span>

          </div>

          <span className="text-white">

            {prediction.eta} mins

          </span>

        </div>

        <div className="flex justify-between">

          <div className="flex items-center gap-3">

            <FaMapMarkerAlt className="text-red-400" />

            <span className="text-slate-300">

              Coordinates

            </span>

          </div>

          <span className="text-white text-sm">

            {prediction.latitude},{" "}
            {prediction.longitude}

          </span>

        </div>

      </div>

    </div>

  );

}