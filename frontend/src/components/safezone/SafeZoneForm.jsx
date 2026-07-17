import { useState } from "react";
import { useSafeZone } from "../../context/SafeZoneContext";
import { createSafeZone } from "../../services/safeZoneService";
// export default function SafeZoneForm() {

//   const { position } = useSafeZone();

//   const [zoneName, setZoneName] = useState("");

//   const [radius, setRadius] = useState(200);

export default function SafeZoneForm() {

  const { position } = useSafeZone();

  const [zoneName, setZoneName] = useState("");
  const [address, setAddress] = useState("");

  const [radius, setRadius] = useState(200);

  async function handleSave() {

    if (!position) {

      alert("Select a location on the map.");

      return;

    }

    try {

      await createSafeZone({

        name: zoneName,

        address: address,

        radius: radius,

        });
      alert("Safe Zone Created");

      setZoneName("");

    }

    catch (err) {

      console.log(err);

      alert("Failed to create Safe Zone");

    }

  }
  return (

    <div className="bg-slate-900/70 backdrop-blur-xl border border-white/10 rounded-3xl p-8">

      <h2 className="text-2xl font-bold text-white">
        New Safe Zone
      </h2>

      <div className="mt-6 space-y-5">

        <input
          type="text"
          placeholder="Zone Name"
          value={zoneName}
          onChange={(e)=>setZoneName(e.target.value)}
          className="w-full bg-slate-800 rounded-xl px-4 py-3 text-white"
        />

        <input
  type="text"
  placeholder="Address"
  value={address}
  onChange={(e) => setAddress(e.target.value)}
  className="w-full bg-slate-800 rounded-xl px-4 py-3 text-white"
/>

        <select
          value={radius}
          onChange={(e)=>setRadius(Number(e.target.value))}
          className="w-full bg-slate-800 rounded-xl px-4 py-3 text-white"
        >
          <option value={100}>100 m</option>
          <option value={200}>200 m</option>
          <option value={300}>300 m</option>
          <option value={500}>500 m</option>
        </select>

       <button
  onClick={handleSave}
  className="w-full bg-cyan-500 hover:bg-cyan-600 rounded-xl py-3 text-white font-bold"
>
  Save Safe Zone
</button>

      </div>

    </div>

  );
  
}
  

  



// import { useState } from "react";


// export default function SafeZoneForm() {

//   const [zoneName, setZoneName] = useState("");

//   const [radius, setRadius] = useState(200);

//   return (

//     <div
//       className="
//       bg-slate-900/70
//       backdrop-blur-xl
//       border
//       border-white/10
//       rounded-3xl
//       p-8
//       "
//     >

//       <h2 className="text-2xl font-bold text-white">

//         New Safe Zone

//       </h2>

//       <p className="text-slate-400 mt-2">

//         Fill the details below.

//       </p>

//       <div className="mt-8 space-y-6">

//         <div>

//           <label className="text-slate-300 block mb-2">

//             Zone Name

//           </label>

//           <input
//             type="text"
//             placeholder="Home / School / Hostel"
//             value={zoneName}
//             onChange={(e) =>
//               setZoneName(e.target.value)
//             }
//             className="
//             w-full
//             bg-slate-800
//             border
//             border-white/10
//             rounded-xl
//             px-4
//             py-3
//             text-white
//             outline-none
//             focus:border-cyan-500
//             "
//           />

//         </div>

//         <div>

//           <label className="text-slate-300 block mb-2">

//             Radius

//           </label>

//           <select
//             value={radius}
//             onChange={(e)=>
//               setRadius(Number(e.target.value))
//             }
//             className="
//             w-full
//             bg-slate-800
//             border
//             border-white/10
//             rounded-xl
//             px-4
//             py-3
//             text-white
//             outline-none
//             "
//           >

//             <option value={100}>
//               100 meters
//             </option>

//             <option value={200}>
//               200 meters
//             </option>

//             <option value={300}>
//               300 meters
//             </option>

//             <option value={500}>
//               500 meters
//             </option>

//             <option value={1000}>
//               1000 meters
//             </option>

//           </select>

//         </div>

//         <button
//           className="
//           w-full
//           bg-gradient-to-r
//           from-cyan-500
//           to-blue-600
//           py-4
//           rounded-xl
//           text-white
//           font-semibold
//           hover:scale-105
//           duration-300
//           "
//         >

//           Save Safe Zone

//         </button>

//       </div>

//     </div>

//   );

// }