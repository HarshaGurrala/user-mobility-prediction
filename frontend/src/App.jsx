
import { BrowserRouter, Routes, Route } from "react-router-dom";

import { AuthProvider } from "./context/AuthContext";

import ProtectedRoute from "./routes/ProtectedRoute";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Landing from "./pages/Landing/Landing";

import GuardianHome from "./pages/guardian/GuardianHome";
import Dashboard from "./pages/Dashboard";

import GuardianFamilyMap from "./pages/guardian/GuardianFamilyMap";



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
path="/guardian/"
element={
<ProtectedRoute role="GUARDIAN">
<GuardianHome/>
</ProtectedRoute>
}
/>




<Route
path="/dashboard/:userId"
element={
<ProtectedRoute>
<Dashboard/>
</ProtectedRoute>
}
/>

onClick={() =>
 navigate(`/dashboard/${user.id}`)
}

<Route
path="*"
element={<Landing />}
/>



<Route
 path="/guardian/family-map"
 element={<GuardianFamilyMap />}
/>



</Routes>

</BrowserRouter>

</AuthProvider>

);

}