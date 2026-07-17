import TrackingHeader from "../components/tracking/TrackingHeader";
import PredictionCard from "../components/tracking/PredictionCard";
// import LiveMap from "../components/tracking/LiveMap";
import GuardianLiveMap from "../components/tracking/GuardianLiveMap";
import StatusCards from "../components/tracking/StatusCards";
import TimelineSection from "../components/tracking/TimelineSection";
import EmergencyActions from "../components/tracking/EmergencyActions";

export default function ChildLiveTracking() {
  return (
    <div className="min-h-screen bg-slate-950 px-10 py-8">

      <TrackingHeader />

         <PredictionCard />

   {/* <LiveMap /> */}
        <GuardianLiveMap/>
         <StatusCards />

            <TimelineSection />

            <EmergencyActions />
    </div>
  );
}