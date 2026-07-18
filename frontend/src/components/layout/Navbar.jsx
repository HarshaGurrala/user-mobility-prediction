// import { Link } from "react-router-dom";
// import { FaMapMarkedAlt } from "react-icons/fa";

// export default function Navbar() {
//   return (
//     <nav className="fixed top-0 left-0 w-full z-50 bg-[#060816]/70 backdrop-blur-xl border-b border-white/10">
//       <div className="max-w-7xl mx-auto flex items-center justify-between px-8 py-5">

//         <Link
//           to="/"
//           className="flex items-center gap-3"
//         >
//           <div className="w-11 h-11 rounded-xl bg-gradient-to-r from-violet-600 to-blue-500 flex items-center justify-center">

//             <FaMapMarkedAlt className="text-white text-xl" />

//           </div>

//           <div>
//             <h1 className="text-xl font-bold text-white">
//               MobilityAI
//             </h1>

//             <p className="text-xs text-slate-400">
//               Guardian Safety
//             </p>
//           </div>

//         </Link>

//         <div className="hidden lg:flex items-center gap-10 text-slate-300">

//           <a href="#">Home</a>

//           <a href="#">Features</a>

//           <a href="#">About</a>

//           <a href="#">Contact</a>

//         </div>

//         <div className="flex gap-4">

//           <Link
//             to="/login"
//             className="px-5 py-2 rounded-lg border border-violet-500 text-violet-300 hover:bg-violet-600 hover:text-white transition"
//           >
//             Login
//           </Link>

//           <Link
//             to="/register"
//             className="px-5 py-2 rounded-lg bg-gradient-to-r from-violet-600 to-blue-500 text-white hover:scale-105 transition"
//           >
//             Register
//           </Link>

//         </div>

//       </div>
//     </nav>
//   );
// }