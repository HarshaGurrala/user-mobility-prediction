// export default function Landing() {
//   return (
//     <div className="min-h-screen bg-slate-950 text-white p-10">
//       <h1 className="text-5xl font-bold mb-6">
//         Landing Page Working ✅
//       </h1>

//       <button className="bg-blue-600 px-6 py-3 rounded-xl">
//         Get Started
//       </button>

//       <div className="bg-white/10 p-6 rounded-2xl mt-8">
//         <h2 className="text-3xl font-bold">
//           AI User Mobility Prediction
//         </h2>

//         <p className="mt-2 text-gray-300">
//           Predict • Protect • Prevent
//         </p>
//       </div>

//       <div className="grid grid-cols-4 gap-5 mt-8">
//         <div className="bg-white/10 rounded-2xl p-6">
//           <h3>Accuracy</h3>
//           <h1 className="text-3xl font-bold">98.4%</h1>
//         </div>

//         <div className="bg-white/10 rounded-2xl p-6">
//           <h3>Users</h3>
//           <h1 className="text-3xl font-bold">10K+</h1>
//         </div>

//         <div className="bg-white/10 rounded-2xl p-6">
//           <h3>Alerts</h3>
//           <h1 className="text-3xl font-bold">520</h1>
//         </div>

//         <div className="bg-white/10 rounded-2xl p-6">
//           <h3>Safe Zones</h3>
//           <h1 className="text-3xl font-bold">150+</h1>
//         </div>
//       </div>
//     </div>
//   );
// }
import Navbar from "../components/layout/Navbar";
import Hero from "../components/landing/Hero";
import Stats from "../components/landing/Stats";
import Features from "../components/landing/Features";
import HowItWorks from "../components/landing/HowItWorks";
import DashboardPreview from "../components/landing/DashboardPreview";
import TechStack from "../components/landing/TechStack";
import Footer from "../components/layout/Footer";

export default function Landing() {
  return (
    <div className="bg-slate-950 text-white min-h-screen">
      <Navbar />
      <Hero />
      <Stats />
      <Features />
      <HowItWorks />
      <DashboardPreview />
      <TechStack />
      <Footer />
    </div>
  );
}