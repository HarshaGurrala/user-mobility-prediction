// import { motion } from "framer-motion";
// import {
//   FiMapPin,
//   FiNavigation,
// } from "react-icons/fi";

// export default function LocationCard({
//   location,
//   loading,
// }) {
//   return (
//     <motion.div
//       whileHover={{
//         y: -8,
//       }}
//       transition={{
//         duration: 0.3,
//       }}
//       className="
// relative
// overflow-hidden
// rounded-[32px]
// bg-white/[0.05]
// border
// border-white/10
// backdrop-blur-3xl
// p-7
// "
//     >
//       <div
//         className="
// absolute
// right-[-50px]
// top-[-50px]
// w-40
// h-40
// bg-blue-500/20
// blur-[80px]
// rounded-full
// "
//       />

//       <div
//         className="
// flex
// items-center
// gap-4
// "
//       >
//         <div
//           className="
// w-14
// h-14
// rounded-2xl
// bg-blue-500/20
// flex
// items-center
// justify-center
// "
//         >
//           <FiMapPin
//             className="
// text-blue-400
// text-2xl
// "
//           />
//         </div>

//         <div>
//           <p
//             className="
// text-gray-400
// text-sm
// "
//           >
//             Current Location
//           </p>

//           <h2
//             className="
// text-xl
// font-semibold
// "
//           >
//             Live Tracking
//           </h2>
//         </div>
//       </div>

//       <div
//         className="
// mt-8
// "
//       >
//         <p
//           className="
// text-gray-400
// text-sm
// "
//         >
//           Detected Place
//         </p>

//         <h3
//           className="
// text-lg
// font-medium
// mt-2
// "
//         >
//           {loading
//             ? "Loading..."
//             : location?.place_name || "Unknown Location"}
//         </h3>

//         <div
//           className="
// flex
// gap-5
// mt-5
// text-sm
// text-gray-400
// "
//         >
//           <span>
//             LAT
//             <br />
//             {loading ? "--" : location?.latitude ?? "--"}
//           </span>

//           <span>
//             LNG
//             <br />
//             {loading ? "--" : location?.longitude ?? "--"}
//           </span>
//         </div>
//       </div>

//       <button
//         className="
// mt-7
// w-full
// rounded-2xl
// py-3
// bg-blue-500/20
// border
// border-blue-400/20
// text-blue-300
// flex
// items-center
// justify-center
// gap-2
// "
//       >
//         <FiNavigation />
//         Open Navigation
//       </button>
//     </motion.div>
//   );
// }