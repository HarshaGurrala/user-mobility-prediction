// function Login() {
//   return <h1>Login Page</h1>;
// }

// export default Login;
// // const response = await login(formData);

// // localStorage.setItem(
// //     "token",
// //     response.data.access_token
// // );

// // navigate("/dashboard");

// import { Link } from "react-router-dom";

// export default function Login() {
//   return (
//     <div className="min-h-screen bg-slate-950 flex items-center justify-center px-6">

//       <div className="w-full max-w-md bg-slate-900 border border-slate-800 rounded-3xl p-8 shadow-2xl">

//         <h1 className="text-4xl font-bold text-white text-center">
//           Welcome Back
//         </h1>

//         <p className="text-slate-400 text-center mt-2">
//           Sign in to your account
//         </p>

//         <form className="mt-8 space-y-5">

//           <div>
//             <label className="text-slate-300 block mb-2">
//               Email
//             </label>

//             <input
//               type="email"
//               placeholder="Enter your email"
//               className="w-full bg-slate-800 border border-slate-700 rounded-xl px-4 py-3 text-white outline-none focus:border-cyan-400"
//             />
//           </div>

//           <div>
//             <label className="text-slate-300 block mb-2">
//               Password
//             </label>

//             <input
//               type="password"
//               placeholder="Enter your password"
//               className="w-full bg-slate-800 border border-slate-700 rounded-xl px-4 py-3 text-white outline-none focus:border-cyan-400"
//             />
//           </div>

//           <button
//             type="submit"
//             className="w-full bg-cyan-500 hover:bg-cyan-400 text-black font-bold py-3 rounded-xl transition"
//           >
//             Login
//           </button>

//         </form>

//         <p className="text-slate-400 text-center mt-6">
//           Don't have an account?{" "}
//           <Link
//             to="/register"
//             className="text-cyan-400 hover:underline"
//           >
//             Register
//           </Link>
//         </p>

//       </div>

//     </div>
//   );
// }

import AuthLayout from "../components/auth/AuthLayout";
import LoginForm from "../components/auth/LoginForm";



export default function Login(){

return(

<AuthLayout

title="Welcome Back"

subtitle="Sign in to continue"

>

<LoginForm/>

</AuthLayout>

)

}