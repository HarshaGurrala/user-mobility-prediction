import { Navigate } from "react-router-dom";

export default function GuestRoute({ children }) {

    const token = localStorage.getItem("token");

    const role = localStorage.getItem("role");

    if (token) {

        if (role === "GUARDIAN") {
            return <Navigate to="/guardian/" replace />;
        }

        return <Navigate to="/" replace />;
    }

    return children;
}