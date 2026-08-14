
import { motion } from "framer-motion";

import {
    MapContainer,
    TileLayer,
    Marker,
    Popup,
    Circle,
    useMap
} from "react-leaflet";

import { useEffect, useRef, useState } from "react";

import { getGuardianMapUsers } from "../../services/guardianApi";

import "leaflet/dist/leaflet.css";

import {
    FiArrowLeft,
    FiUsers
} from "react-icons/fi";

import { useNavigate } from "react-router-dom";

import { Fragment } from "react";


// ==========================================================
// MAP FOCUS
// ==========================================================

function MapFocus({ users }) {

    const map = useMap();

    const hasFocused = useRef(false);


    useEffect(() => {

        const locations = users
            .filter(
                (user) =>
                    user.latitude !== null &&
                    user.longitude !== null &&
                    user.latitude !== undefined &&
                    user.longitude !== undefined
            )
            .map(
                (user) => [
                    Number(user.latitude),
                    Number(user.longitude)
                ]
            );


        if (
            locations.length > 0 &&
            !hasFocused.current
        ) {

            map.fitBounds(
                locations,
                {
                    padding: [60, 60],
                    maxZoom: 16
                }
            );

            hasFocused.current = true;

        }

    }, [users, map]);


    return null;
}


// ==========================================================
// GUARDIAN FAMILY LIVE MAP
// ==========================================================

export default function GuardianFamilyMap() {


    const navigate = useNavigate();


    const [users, setUsers] = useState([]);


    // ======================================================
    // LOAD LIVE LOCATIONS
    // ======================================================

    useEffect(() => {


        let mounted = true;


        const loadLocations = async () => {

            try {

                const data =
                    await getGuardianMapUsers();


                console.log(
                    "Live Map Users:",
                    data
                );


                if (mounted) {

                    setUsers(
                        Array.isArray(data)
                            ? data
                            : []
                    );

                }

            }
            catch (error) {

                console.error(
                    "Live location API Error:",
                    error
                );

            }

        };


        // First load immediately
        loadLocations();


        // Refresh every 10 seconds
        const interval =
            setInterval(
                loadLocations,
                10000
            );


        return () => {

            mounted = false;

            clearInterval(interval);

        };


    }, []);


    // ======================================================
    // RENDER
    // ======================================================

    return (

        <motion.div

            initial={{
                opacity: 0
            }}

            animate={{
                opacity: 1
            }}

            style={{
                height: "100vh",
                width: "100vw"
            }}

            className="
            bg-black
            "
        >


            {/* ==================================================
                BACK BUTTON
            ================================================== */}

            <div

                className="
                absolute
                top-5
                left-5
                z-[1000]
                rounded-3xl
                bg-black/60
                backdrop-blur-xl
                border
                border-white/10
                px-5
                py-4
                "
            >

                <button

                    onClick={() =>
                        navigate(-1)
                    }

                    className="
                    flex
                    items-center
                    gap-3
                    text-white
                    "
                >

                    <FiArrowLeft />

                    Back

                </button>

            </div>


            {/* ==================================================
                LIVE USERS COUNT
            ================================================== */}

            <div

                className="
                absolute
                top-5
                right-5
                z-[1000]
                rounded-3xl
                bg-black/60
                backdrop-blur-xl
                border
                border-white/10
                px-5
                py-4
                "
            >

                <div

                    className="
                    flex
                    items-center
                    gap-3
                    text-white
                    "
                >

                    <div

                        className="
                        p-3
                        rounded-2xl
                        bg-blue-500/20
                        "
                    >

                        <FiUsers

                            className="
                            text-blue-400
                            text-xl
                            "
                        />

                    </div>


                    <div>

                        <p

                            className="
                            text-xs
                            text-gray-400
                            "
                        >

                            LIVE USERS

                        </p>


                        <p

                            className="
                            text-sm
                            font-medium
                            "
                        >

                            {users.length} Connected

                        </p>

                    </div>

                </div>

            </div>


            {/* ==================================================
                MAP
            ================================================== */}

            <MapContainer

                center={[
                    18.5666,
                    84.1941
                ]}

                zoom={13}

                scrollWheelZoom={true}

                style={{
                    height: "100%",
                    width: "100%"
                }}

            >

                {/* Focus only when locations are first received */}

                <MapFocus
                    users={users}
                />


                {/* Satellite map */}

                <TileLayer

                    url="
                    https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}
                    "
                />


                {/* ==================================================
                    LIVE USER MARKERS
                ================================================== */}

                {
                    users
                        .filter(
                            (user) =>
                                user.latitude !== null &&
                                user.longitude !== null &&
                                user.latitude !== undefined &&
                                user.longitude !== undefined
                        )
                        .map((user) => {


                            const latitude =
                                Number(
                                    user.latitude
                                );


                            const longitude =
                                Number(
                                    user.longitude
                                );


                            return (

                                <Fragment
                                    key={user.user_id}
                                >

                                    {/* ==========================
                                        USER MARKER
                                    ========================== */}

                                    <Marker

                                        position={[
                                            latitude,
                                            longitude
                                        ]}

                                    >

                                        <Popup>

                                            <div
                                                className="
                                                text-black
                                                min-w-[180px]
                                                "
                                            >

                                                <h3
                                                    className="
                                                    font-semibold
                                                    "
                                                >

                                                    {user.user_name}

                                                </h3>


                                                <p>

                                                    SafePath ID:
                                                    {" "}
                                                    {user.safe_path_id}

                                                </p>


                                                <p>

                                                    Status:

                                                    <span

                                                        className={`
                                                        ml-1
                                                        font-medium

                                                        ${
                                                            user.is_online
                                                                ? "text-green-600"
                                                                : "text-red-600"
                                                        }
                                                        `}
                                                    >

                                                        {
                                                            user.is_online
                                                                ? "Online"
                                                                : "Offline"
                                                        }

                                                    </span>

                                                </p>


                                                {
                                                    user.timestamp && (

                                                        <p>

                                                            Last location:

                                                            {" "}

                                                            {
                                                                new Date(
                                                                    user.timestamp
                                                                ).toLocaleString()
                                                            }

                                                        </p>

                                                    )
                                                }

                                            </div>

                                        </Popup>

                                    </Marker>


                                    {/* ==========================
                                        LIVE LOCATION AREA
                                    ========================== */}

                                    <Circle

                                        center={[
                                            latitude,
                                            longitude
                                        ]}

                                        radius={300}

                                        pathOptions={{

                                            color:
                                                user.is_online
                                                    ? "green"
                                                    : "red",

                                            fillOpacity: 0.15

                                        }}

                                    />

                                </Fragment>

                            );

                        })
                }


            </MapContainer>

        </motion.div>

    );

}

