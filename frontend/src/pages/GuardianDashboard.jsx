// import { useEffect, useState } from "react";

// import DashboardLayout from "../components/layout/DashboardLayout";

// import { getMyChildren } from "../services/guardianService";

// export default function GuardianDashboard() {

//     const [children, setChildren] = useState([]);

//     useEffect(() => {

//         loadChildren();

//     }, []);

//     const loadChildren = async () => {

//         try {

//             const data = await getMyChildren();

//             setChildren(data);

//         } catch (error) {

//             console.log(error);

//         }

//     };

//     return (

//         <DashboardLayout>

//             <h1 className="text-5xl font-black text-white">

//                 Guardian Dashboard

//             </h1>

//             <p className="text-slate-400 mt-2">

//                 Monitor all linked children

//             </p>

//             <div className="grid md:grid-cols-2 xl:grid-cols-3 gap-6 mt-10">

//                 {children.map((child) => (

//                     <div
//                         key={child.id}
//                         className="bg-slate-900 rounded-3xl p-6 border border-cyan-500/20"
//                     >

//                         <h2 className="text-2xl font-bold text-white">

//                             {child.full_name}

//                         </h2>

//                         <p className="text-slate-400 mt-2">

//                             {child.email}

//                         </p>

//                         <p className="text-slate-400">

//                             {child.phone_number}

//                         </p>

//                         <div className="mt-6">

//                             <button
//                                 className="w-full bg-cyan-500 hover:bg-cyan-600 transition text-white py-3 rounded-xl font-semibold"
//                             >

//                                 View Live

//                             </button>

//                         </div>

//                     </div>

//                 ))}

//             </div>

//         </DashboardLayout>

//     );

// }
import DashboardHeader from "../components/guardian/DashboardHeader";
import StatsSection from "../components/guardian/StatsSection";
import ChildrenSection from "../components/guardian/ChildrenSection";
import AlertsSection from "../components/guardian/AlertsSection";

import AIPredictionCard from "../components/guardian/AIPredictionCard";
export default function GuardianDashboard() {
  return (
    <div className="min-h-screen bg-slate-950 px-10 py-10">

      <DashboardHeader />

      <StatsSection />


        <ChildrenSection />

        <AlertsSection />
        
        <AIPredictionCard />

    </div>
  );
}