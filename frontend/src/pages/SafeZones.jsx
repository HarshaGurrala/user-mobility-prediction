// import DashboardLayout from "../components/layout/DashboardLayout";
// import SafeZoneMap from "../components/safezone/SafeZoneMap";
// import SafeZoneForm from "../components/safezone/SafeZoneForm";

// export default function SafeZones() {
//   return (
//     <DashboardLayout>

//       <div className="mb-8">

//         <h1 className="text-5xl font-black text-white">
//           Safe Zones
//         </h1>

//         <p className="text-slate-400 mt-2">
//           Create locations where your child is considered safe.
//         </p>

//       </div>

//       <div className="grid lg:grid-cols-3 gap-8">

//         <div className="lg:col-span-2">

//           <SafeZoneMap />

//         </div>

//         <div>

//           <SafeZoneForm />

//         </div>

//       </div>

//     </DashboardLayout>
//   );
// }

import DashboardLayout from "../components/layout/DashboardLayout";
import SafeZoneMap from "../components/safezone/SafeZoneMap";
import SafeZoneForm from "../components/safezone/SafeZoneForm";

import { SafeZoneProvider } from "../context/SafeZoneContext";

export default function SafeZones() {

  return (

    <DashboardLayout>

      <SafeZoneProvider>

        <div className="mb-8">

          <h1 className="text-5xl font-black text-white">

            Safe Zones

          </h1>

        </div>

        <div className="grid lg:grid-cols-3 gap-8">

          <div className="lg:col-span-2">

            <SafeZoneMap />

          </div>

          <div>

            <SafeZoneForm />

          </div>

        </div>

      </SafeZoneProvider>

    </DashboardLayout>

  );

}