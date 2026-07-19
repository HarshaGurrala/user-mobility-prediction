// import { BrowserRouter, Routes, Route } from "react-router-dom";

// import Landing from "./pages/Landing/Landing";

// function App() {
//   return (
//     <BrowserRouter>
//       <Routes>
//         <Route path="/" element={<Landing />} />
//       </Routes>
//     </BrowserRouter>
//   );
// }

// export default App;
import { BrowserRouter, Routes, Route } from "react-router-dom";


import { AuthProvider } from "./context/AuthContext";


import ProtectedRoute from "./routes/ProtectedRoute";


import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Landing from "./pages/Landing/Landing";


// Temporary dashboard
// Replace with actual dashboard later

// function Dashboard(){

//     return (

//         <div className="
//         min-h-screen
//         bg-[#050505]
//         text-white
//         flex
//         items-center
//         justify-center
//         text-3xl
//         ">

//             AI Mobility Dashboard

//         </div>

//     );

// }



export default function App(){


return (

<AuthProvider>


<BrowserRouter>


<Routes>


<Route

path="/"

element={<Landing />}

/>


<Route

path="/login"

element={<Login />}

/>



<Route

path="/register"

element={<Register />}

/>




<Route
path="/dashboard"
element={
<ProtectedRoute>
<Dashboard/>
</ProtectedRoute>
}
/>



<Route

path="*"

element={<Landing />}

/>



</Routes>


</BrowserRouter>


</AuthProvider>

);


}