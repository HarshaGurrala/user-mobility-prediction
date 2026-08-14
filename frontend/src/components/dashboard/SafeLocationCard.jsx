// import { motion } from "framer-motion";

// import {
//   FiShield,
//   FiMapPin,
//   FiCheckCircle,
//   FiNavigation,
// } from "react-icons/fi";

// export default function SafeLocationCard({
//   locations = [],
//   loading = false,
// }) {
//   return (
//     <motion.div
//       initial={{
//         opacity: 0,
//         y: 30,
//       }}
//       animate={{
//         opacity: 1,
//         y: 0,
//       }}
//       transition={{
//         duration: 0.6,
//       }}
//       className="
// relative
// overflow-hidden
// rounded-3xl
// border
// border-white/10
// bg-white/5
// backdrop-blur-2xl
// p-6
// shadow-[0_0_40px_rgba(34,197,94,.15)]
// "
//     >
//       {/* Green glow */}

//       <div
//         className="
// absolute
// -right-20
// -top-20
// h-44
// w-44
// rounded-full
// bg-green-500/20
// blur-3xl
// "
//       />

//       {/* Header */}

//       <div
//         className="
// relative
// flex
// items-center
// justify-between
// "
//       >
//         <div
//           className="
// flex
// items-center
// gap-3
// "
//         >
//           <div
//             className="
// p-3
// rounded-2xl
// bg-green-500/20
// "
//           >
//             <FiShield
//               className="
// text-green-400
// text-xl
// "
//             />
//           </div>

//           <div>
//             <h2
//               className="
// text-white
// font-semibold
// text-lg
// "
//             >
//               Safe Zones
//             </h2>

//             <p
//               className="
// text-xs
// text-gray-400
// "
//             >
//               Trusted locations
//             </p>
//           </div>
//         </div>

//         <div
//           className="
// flex
// items-center
// gap-1
// px-3
// py-1
// rounded-full
// bg-green-500/20
// text-green-300
// text-xs
// "
//         >
//           <FiCheckCircle />
//           ACTIVE
//         </div>
//       </div>

//       {/* Content */}

//       <div
//         className="
// mt-6
// space-y-3
// "
//       >
//         {loading ? (
//           <div
//             className="
// rounded-2xl
// bg-black/30
// border
// border-white/10
// p-5
// text-center
// text-gray-400
// "
//           >
//             Loading safe locations...
//           </div>
//         ) : locations.length === 0 ? (
//           <div
//             className="
// rounded-2xl
// bg-black/30
// border
// border-white/10
// p-5
// text-center
// "
//           >
//             <FiNavigation
//               className="
// mx-auto
// text-green-400
// text-3xl
// mb-3
// "
//             />

//             <p
//               className="
// text-white
// "
//             >
//               No safe locations added
//             </p>

//             <p
//               className="
// text-gray-400
// text-sm
// mt-1
// "
//             >
//               AI will learn frequently visited places
//             </p>
//           </div>
//         ) : (
//           locations.map((location, index) => (
//             <motion.div
//               key={location.id || index}
//               whileHover={{
//                 scale: 1.03,
//               }}
//               className="
// flex
// items-center
// justify-between
// rounded-2xl
// bg-black/30
// border
// border-white/10
// p-4
// "
//             >
//               <div
//                 className="
// flex
// items-center
// gap-3
// "
//               >
//                 <FiMapPin className="text-green-400" />

//                 <div>
//                   <p
//                     className="
// text-white
// text-sm
// "
//                   >
//                     {location.name ||
//                       location.place_name ||
//                       location.location_name ||
//                       "Safe Location"}
//                   </p>

//                   <p
//                     className="
// text-xs
// text-gray-400
// "
//                   >
//                     Protected zone
//                   </p>
//                 </div>
//               </div>

//               <p
//                 className="
// text-green-300
// text-xs
// "
//               >
//                 {location.distance || "Near"}
//               </p>
//             </motion.div>
//           ))
//         )}
//       </div>

//       {/* Footer */}

//       <div
//         className="
// mt-5
// flex
// items-center
// gap-2
// text-xs
// text-green-300
// "
//       >
//         <FiShield />
//         AI Safety Monitoring Enabled
//       </div>
//     </motion.div>
//   );
// }