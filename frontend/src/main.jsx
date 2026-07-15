

// import React from "react";
// import ReactDOM from "react-dom/client";

// import App from "./App";

// import "./styles/globals.css";

// ReactDOM.createRoot(document.getElementById("root")).render(
//   <React.StrictMode>
//       <App/>
//   </React.StrictMode>
// );
// import React from "react";
// import ReactDOM from "react-dom/client";
// import App from "./App";
// // import "./index.css";
// import "./styles/globals.css"
// import "leaflet/dist/leaflet.css";

// ReactDOM.createRoot(document.getElementById("root")).render(
//   <React.StrictMode>
//     <App />
//   </React.StrictMode>
// );


import React from "react";
import ReactDOM from "react-dom/client";

import App from "./App";
import "./styles/globals.css"
//import "./index.css";

import AuthProvider from "./context/AuthContext";

ReactDOM.createRoot(document.getElementById("root")).render(

<AuthProvider>

<App/>

</AuthProvider>

);