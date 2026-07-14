// export default function DashboardPreview() {
//   return (
//     <section className="py-20 text-center text-white">
//       <h2 className="text-4xl font-bold">Dashboard Preview</h2>
//     </section>
//   );
// }

import {
  MapPinned,
  BrainCircuit,
  Bell,
  ShieldCheck
} from "lucide-react";

export default function DashboardPreview() {

  return (

    <section className="py-28 px-8">

      <div className="max-w-7xl mx-auto">

        <h2 className="text-5xl font-bold text-center">

          Dashboard Preview

        </h2>

        <p className="text-center text-slate-400 mt-5">

          A glimpse of your intelligent mobility dashboard.

        </p>

        <div className="mt-20 rounded-[35px]
                        bg-white/5
                        backdrop-blur-xl
                        border
                        border-white/10
                        p-10">

          <div className="grid lg:grid-cols-3 gap-8">

            {/* Left */}

            <div className="space-y-6">

              <div className="bg-slate-900 rounded-3xl p-6">

                <h3 className="text-slate-400">

                  Prediction Accuracy

                </h3>

                <h1 className="text-5xl font-black mt-3">

                  98.4%

                </h1>

              </div>

              <div className="bg-slate-900 rounded-3xl p-6">

                <h3 className="text-slate-400">

                  Active Users

                </h3>

                <h1 className="text-5xl font-black mt-3">

                  10,248

                </h1>

              </div>

            </div>

            {/* Center */}

            <div className="bg-slate-900 rounded-3xl h-[350px] flex flex-col items-center justify-center">

              <MapPinned
                className="text-cyan-400"
                size={70}
              />

              <h2 className="text-3xl font-bold mt-6">

                Interactive Map

              </h2>

              <p className="text-slate-400 mt-4">

                Live Location Tracking

              </p>

            </div>

            {/* Right */}

            <div className="space-y-6">

              <div className="bg-slate-900 rounded-3xl p-6">

                <Bell
                  className="text-orange-400"
                />

                <h3 className="font-bold mt-4">

                  Recent Alerts

                </h3>

                <ul className="mt-4 space-y-3 text-slate-400">

                  <li>✓ Safe Zone Entered</li>

                  <li>✓ Prediction Generated</li>

                  <li>✓ Emergency Updated</li>

                </ul>

              </div>

              <div className="bg-slate-900 rounded-3xl p-6">

                <BrainCircuit
                  className="text-purple-400"
                />

                <h3 className="font-bold mt-5">

                  AI Confidence

                </h3>

                <div className="w-full h-3 bg-slate-700 rounded-full mt-5">

                  <div className="w-[98%] h-3 bg-cyan-400 rounded-full">

                  </div>

                </div>

                <p className="mt-3 text-cyan-400">

                  98%

                </p>

              </div>

            </div>

          </div>

        </div>

      </div>

    </section>

  );

}