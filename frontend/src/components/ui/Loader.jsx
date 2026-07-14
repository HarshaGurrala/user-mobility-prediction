import Button from "../components/ui/Button";
import GlassCard from "../components/ui/GlassCard";
import StatCard from "../components/ui/StatCard";

export default function Landing() {

  return (
    <div className="min-h-screen bg-slate-950 p-10">

      <h1 className="text-5xl text-white font-bold mb-10">
        Design System Preview
      </h1>

      <div className="flex gap-6 mb-10">
        <Button>Get Started</Button>
        <Button className="bg-purple-600 hover:bg-purple-500">
          Login
        </Button>
      </div>

      <GlassCard className="mb-10">
        <h2 className="text-2xl text-white font-semibold">
          AI User Mobility Prediction
        </h2>

        <p className="text-gray-400 mt-3">
          Predict. Protect. Prevent.
        </p>
      </GlassCard>

      <div className="grid md:grid-cols-4 gap-6">
        <StatCard title="Prediction Accuracy" value="98.4%" />
        <StatCard title="Tracked Users" value="10K+" />
        <StatCard title="Emergency Alerts" value="520" />
        <StatCard title="Safe Zones" value="150+" />
      </div>

    </div>
  );
}