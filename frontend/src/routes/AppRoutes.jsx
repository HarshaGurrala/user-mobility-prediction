import { BrowserRouter, Routes, Route } from "react-router-dom";

import Landing from "../pages/Landing/Landing";

import GuardianFamilyMap from "../pages/guardian/GuardianFamilyMap";
import GuardianUserDetails from "../pages/guardian/GuardianUserDetails";
import GuardianHome from "../pages/guardian/GuardianHome";

export default function AppRoutes() {

  return (

    <BrowserRouter>

      <Routes>

        <Route
          path="/"
          element={<Landing />}
        />


        <Route
          path="/guardian/family-map"
          element={<GuardianFamilyMap />}
        />


       


      </Routes>

    </BrowserRouter>

  );

}