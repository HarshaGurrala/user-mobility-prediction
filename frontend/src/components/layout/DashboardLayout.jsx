// import Sidebar from "../sidebar/Sidebar";
// import Topbar from "../topbar/Topbar";

// export default function DashboardLayout({ children }) {
//   return (
//     <div className="flex bg-slate-950 min-h-screen">

//       <Sidebar />

//       <div className="flex-1">

//         <Topbar />

//         <main className="p-8">

//           {children}

//         </main>

//       </div>

//     </div>
//   );
// }


import Sidebar from "./Sidebar";
import Topbar from "./Topbar";

export default function DashboardLayout({ children }) {
  return (
    <div className="flex min-h-screen bg-slate-950">

      <Sidebar />

      <div className="flex-1">

        <Topbar />

        <main className="p-8">
          {children}
        </main>

      </div>

    </div>
  );
}