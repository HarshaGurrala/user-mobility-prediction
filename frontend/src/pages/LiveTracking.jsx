import { useEffect } from "react";

import DashboardLayout from "../components/layout/DashboardLayout";
import LiveMap from "../components/map/LiveMap";
import CurrentStatus from "../components/map/CurrentStatus";
import TrackingStats from "../components/map/TrackingStats";

import useLocation from "../hooks/useLocation";

export default function LiveTracking() {
  const { location, error } = useLocation();

  useEffect(() => {
    if (location) {
      console.log("Live Location:", location);
    }
  }, [location]);

  return (
    <DashboardLayout>
      <h1 className="text-5xl font-black text-white">
        Live Tracking
      </h1>

      <p className="text-slate-400 mt-2">
        Your live GPS is being shared with your guardian.
      </p>

      <div className="mt-6 bg-slate-900/70 border border-cyan-500/20 rounded-2xl p-5">
        <h2 className="text-xl font-bold text-cyan-400 mb-4">
          Current GPS Status
        </h2>

        {error && (
          <p className="text-red-400">
            {error}
          </p>
        )}

        {!location ? (
          <p className="text-yellow-400">
            Waiting for GPS...
          </p>
        ) : (
          <div className="space-y-2 text-white">
            <p>
              <strong>Latitude:</strong> {location.latitude}
            </p>

            <p>
              <strong>Longitude:</strong> {location.longitude}
            </p>

            <p>
              <strong>Accuracy:</strong> {location.accuracy.toFixed(2)} m
            </p>

            <p>
              <strong>Speed:</strong> {location.speed.toFixed(2)} m/s
            </p>

            <p className="text-green-400 font-semibold mt-3">
              ✓ Live location is updating automatically.
            </p>
          </div>
        )}
      </div>

      <div className="grid lg:grid-cols-4 gap-6 mt-8">
        <div className="lg:col-span-3">
          <LiveMap />
        </div>

        <div>
          <CurrentStatus />
        </div>
      </div>

      <TrackingStats />
    </DashboardLayout>
  );
}