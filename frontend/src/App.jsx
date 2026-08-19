import { useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import { AuthProvider } from "./context/AuthContext";

import ProtectedRoute from "./routes/ProtectedRoute";
import GuestRoute from "./routes/GuestRoute";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Landing from "./pages/Landing/Landing";

import AITAMMovingText from "./components/intro/AITAMMovingText";

import GuardianHome from "./pages/guardian/GuardianHome";
import Dashboard from "./pages/Dashboard";
import GuardianFamilyMap from "./pages/guardian/GuardianFamilyMap";

export default function App() {
  const [showIntro, setShowIntro] = useState(true);

  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>

          {/* =====================================================
              MAIN PAGE
              AITAM INTRO → 15 SECONDS → LANDING
          ====================================================== */}

          <Route
            path="/"
            element={
              showIntro ? (
                <AITAMMovingText
                  onComplete={() => setShowIntro(false)}
                />
              ) : (
                <Landing />
              )
            }
          />

          {/* =====================================================
              AITAM PAGE
          ====================================================== */}

          <Route
            path="/aitam"
            element={<AITAMMovingText />}
          />

          {/* =====================================================
              LOGIN
          ====================================================== */}

          <Route
            path="/login"
            element={
              <GuestRoute>
                <Login />
              </GuestRoute>
            }
          />

          {/* =====================================================
              REGISTER
          ====================================================== */}

          <Route
            path="/register"
            element={<Register />}
          />

          {/* =====================================================
              GUARDIAN HOME
          ====================================================== */}

          <Route
            path="/guardian"
            element={
              <ProtectedRoute role="GUARDIAN">
                <GuardianHome />
              </ProtectedRoute>
            }
          />

          {/* =====================================================
              USER DASHBOARD
          ====================================================== */}

          <Route
            path="/dashboard/:userId"
            element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            }
          />

          {/* =====================================================
              GUARDIAN FAMILY MAP
          ====================================================== */}

          <Route
            path="/guardian/family-map"
            element={
              <GuardianFamilyMap />
            }
          />

          {/* =====================================================
              UNKNOWN ROUTE
          ====================================================== */}

          <Route
            path="*"
            element={<Landing />}
          />

        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}