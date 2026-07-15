// function Register() {
//   return <h1>Register Page</h1>;
// }

// export default Register;

// import { Link } from "react-router-dom";

// export default function Register() {
//   return (
//     <div className="min-h-screen bg-slate-950 flex items-center justify-center px-6">

//       <div className="w-full max-w-md bg-slate-900 border border-slate-800 rounded-3xl p-8 shadow-2xl">

//         <h1 className="text-4xl font-bold text-white text-center">
//           Create Account
//         </h1>

//         <p className="text-slate-400 text-center mt-2">
//           Join AI User Mobility Prediction
//         </p>

//         <form className="mt-8 space-y-5">

//           <input
//             type="text"
//             placeholder="Full Name"
//             className="w-full bg-slate-800 border border-slate-700 rounded-xl px-4 py-3 text-white"
//           />

//           <input
//             type="email"
//             placeholder="Email"
//             className="w-full bg-slate-800 border border-slate-700 rounded-xl px-4 py-3 text-white"
//           />

//           <input
//             type="password"
//             placeholder="Password"
//             className="w-full bg-slate-800 border border-slate-700 rounded-xl px-4 py-3 text-white"
//           />

//           <button
//             className="w-full bg-cyan-500 hover:bg-cyan-400 text-black font-bold py-3 rounded-xl"
//           >
//             Create Account
//           </button>

//         </form>

//         <p className="text-center text-slate-400 mt-6">
//           Already have an account?{" "}
//           <Link
//             to="/login"
//             className="text-cyan-400 hover:underline"
//           >
//             Login
//           </Link>
//         </p>

//       </div>

//     </div>
//   );
// }

import AuthLayout from "../components/auth/AuthLayout";

import RegisterForm from "../components/auth/RegisterForm";
import { useState } from "react";
export default function Register(){
  
  const [role, setRole] = useState("USER");

  const [guardianCode, setGuardianCode] = useState("");

return(

<AuthLayout

title="Create Account"

subtitle="Start your AI journey"

>

<RegisterForm/>

</AuthLayout>

)

}