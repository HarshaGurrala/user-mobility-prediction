import DashboardLayout from "../components/layout/DashboardLayout";
import LiveMap from "../components/map/LiveMap";
import SafeStats from "../components/dashboard/SafeStats";
import SafeLocationCard from "../components/cards/SafeLocationCard";

export default function SafeLocations(){

return(

<DashboardLayout>

<h1 className="text-5xl font-bold text-white">

Safe Locations

</h1>

<p className="text-slate-400 mt-2">

AI Recommended Safe Zones

</p>

<div className="mt-8">

<SafeStats/>

</div>

<div className="grid lg:grid-cols-3 gap-6 mt-8">

<div className="lg:col-span-2">

<LiveMap/>

</div>

<div className="space-y-5">

<SafeLocationCard
title="Apollo Hospital"
distance="1.2 km"
type="Hospital"
/>

<SafeLocationCard
title="Police Station"
distance="850 m"
type="Police"
/>

<SafeLocationCard
title="Home"
distance="3.4 km"
type="Personal"
/>

</div>

</div>

</DashboardLayout>

)

}