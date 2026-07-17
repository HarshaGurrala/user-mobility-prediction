import { BrowserRouter, Routes, Route } from "react-router-dom";

import Landing from "../pages/Landing";
import Login from "../pages/Login";
import Register from "../pages/Register";
import Dashboard from "../pages/Dashboard";
import LiveTracking from "../pages/LiveTracking";
import SafeLocations from "../pages/SafeLocations";
import EmergencyContacts from "../pages/EmergencyContacts";
import Analytics from "../pages/Analytics";
import Profile from "../pages/Profile";
import Settings from "../pages/Settings";
import NotFound from "../pages/NotFound";
import ProtectedRoute from "./ProtectedRoute";

import SafeZones from "../pages/SafeZones";
import GuardianDashboard from "../pages/GuardianDashboard";

import ChildLiveTracking from "../pages/ChildLiveTracking";


function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>

        <Route path="/" element={<Landing />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

       

        <Route
  path="/dashboard"
  element={
    <ProtectedRoute>
      <Dashboard />
    </ProtectedRoute>
  }
/>


<Route
  path="/live-tracking"
  element={
    <ProtectedRoute>
      <LiveTracking />
    </ProtectedRoute>
  }
/>


<Route
  path="/safe-locations"
  element={
    <ProtectedRoute>
      <SafeLocations />
    </ProtectedRoute>
  }
/>


<Route
  path="/analytics"
  element={
    <ProtectedRoute>
      <Analytics />
    </ProtectedRoute>
  }
/>


<Route
  path="/emergency"
  element={
    <ProtectedRoute>
      <EmergencyContacts />
    </ProtectedRoute>
  }
/>


<Route
  path="/profile"
  element={
    <ProtectedRoute>
      <Profile />
    </ProtectedRoute>
  }
/>

<Route
  path="/guardian-dashboard"
  element={
    <ProtectedRoute>
      <GuardianDashboard />
    </ProtectedRoute>
  }
/>

<Route
  path="/settings"
  element={
    <ProtectedRoute>
      <Settings />
    </ProtectedRoute>
  }
/>

      <Route
    path="/child/:id"
    element={
        <ProtectedRoute>
            <ChildLiveTracking />
        </ProtectedRoute>
    }
/>

<Route
    path="/guardian/safe-zones"
    element={<SafeZones />}
/>

        <Route path="*" element={<NotFound />} />

      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;