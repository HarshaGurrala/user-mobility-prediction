// function LiveTracking() {
//   return <h1>Live Tracking</h1>;
// }

// export default LiveTracking;
import DashboardLayout from "../components/layout/DashboardLayout";
import LiveMap from "../components/map/LiveMap";
import CurrentStatus from "../components/map/CurrentStatus";
import TrackingStats from "../components/map/TrackingStats";

export default function LiveTracking(){

return(

<DashboardLayout>

<h1 className="text-5xl font-black text-white">

Live Tracking

</h1>

<p className="text-slate-400 mt-2">

Real-Time AI Location Monitoring

</p>

<div className="grid lg:grid-cols-4 gap-6 mt-8">

<div className="lg:col-span-3">

<LiveMap/>

</div>

<div>

<CurrentStatus/>

</div>

</div>

<TrackingStats/>

</DashboardLayout>

)

}