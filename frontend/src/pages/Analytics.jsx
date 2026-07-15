// function Analytics() {
//   return <h1>Analytics</h1>;
// }

// export default Analytics;

import DashboardLayout from "../components/layout/DashboardLayout";

import AnalyticsOverview from "../components/analytics/AnalyticsOverview";
import WeeklyMovementChart from "../components/charts/WeeklyMovementChart";
import PredictionAccuracy from "../components/analytics/PredictionAccuracy";
import VisitedPlaces from "../components/analytics/VisitedPlaces";

export default function Analytics(){

return(

<DashboardLayout>

<h1 className="text-5xl font-black text-white">

Analytics Dashboard

</h1>

<p className="text-slate-400 mt-3">

AI Mobility Insights

</p>

<div className="mt-8">

<AnalyticsOverview/>

</div>

<div className="grid lg:grid-cols-3 gap-6 mt-8">

<div className="lg:col-span-2">

<WeeklyMovementChart/>

</div>

<div>

<PredictionAccuracy/>

</div>

</div>

<div className="mt-8">

<VisitedPlaces/>

</div>

</DashboardLayout>

)

}