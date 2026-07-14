import { ShieldCheck } from "lucide-react";

export default function EmergencyStatus() {

  return (

    <div className="bg-slate-900 border border-white/10 rounded-3xl p-6">

      <ShieldCheck
        size={42}
        className="text-green-400"
      />

      <h2 className="text-2xl text-white font-bold mt-5">

        Emergency Status

      </h2>

      <h1 className="text-green-400 text-4xl font-bold mt-6">

        SAFE

      </h1>

      <p className="text-slate-400 mt-4">

        No emergency detected.

      </p>

    </div>

  );

}