import DashboardLayout from "../components/layout/DashboardLayout";
import LiveMap from "../components/map/LiveMap";
import CurrentStatus from "../components/map/CurrentStatus";
import TrackingStats from "../components/map/TrackingStats";

import useLocation from "../hooks/useLocation";

import { useEffect } from "react";
import { updateLocation } from "../services/locationService";

export default function LiveTracking() {

  const { location, error } = useLocation();

     useEffect(() => {

  if (!location) return;

  const sendLocation = async () => {

    try {

      await updateLocation(location);

      console.log("Location saved");

    } catch (err) {

      console.error(err);

    }

  };

  sendLocation();

}, [location]);

  return (

    <DashboardLayout>

      <h1 className="text-5xl font-black text-white">

        Live Tracking

      </h1>

      <p className="text-slate-400 mt-2">

        Real-Time AI Location Monitoring

      </p>

      {/* Temporary GPS Information */}

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

            Waiting for GPS location...

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

              <strong>Accuracy:</strong> {location.accuracy} meters

            </p>

            <p>

              <strong>Speed:</strong> {location.speed} m/s

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