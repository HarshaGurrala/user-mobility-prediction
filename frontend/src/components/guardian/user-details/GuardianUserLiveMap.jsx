import { motion } from "framer-motion";

import {
    MapContainer,
    TileLayer,
    Marker,
    Popup,
    Circle,
    useMap
} from "react-leaflet";

import { useEffect } from "react";

import "leaflet/dist/leaflet.css";

import {
    FiMapPin,
    FiNavigation
} from "react-icons/fi";


// ==========================================================
// AUTO FOCUS MAP ON LIVE LOCATION
// ==========================================================

function MapAutoFocus({
    latitude,
    longitude
}) {

    const map = useMap();


    useEffect(() => {

        if (
            latitude == null ||
            longitude == null
        ) {
            return;
        }


        map.flyTo(
            [
                latitude,
                longitude
            ],
            17,
            {
                animate: true,
                duration: 1.5
            }
        );


    }, [
        latitude,
        longitude,
        map
    ]);


    return null;
}


// ==========================================================
// LIVE USER MAP
// ==========================================================

export default function GuardianUserLiveMap({
    user
}) {


    // ======================================================
    // GET LOCATION DIRECTLY FROM API RESPONSE
    // ======================================================

    const latitude =
        Number(user?.latitude);

    const longitude =
        Number(user?.longitude);


    const hasLocation =
        Number.isFinite(latitude) &&
        Number.isFinite(longitude);


    // ======================================================
    // NO LOCATION
    // ======================================================

    if (!hasLocation) {

        return (

            <motion.div

                initial={{
                    opacity: 0,
                    y: 30
                }}

                animate={{
                    opacity: 1,
                    y: 0
                }}

                className="
                rounded-3xl
                border
                border-white/10
                bg-white/5
                p-6
                text-gray-400
                "

            >

                Live location not available

            </motion.div>

        );

    }


    // ======================================================
    // USER NAME
    // ======================================================

    const userName =
        user?.user_name ||
        user?.full_name ||
        "User";


    // ======================================================
    // ONLINE STATUS
    // ======================================================

    const isOnline =
        user?.is_online === true ||
        user?.is_online === 1 ||
        user?.is_online === "1" ||
        user?.is_online === "true";


    return (

        <motion.div

            initial={{
                opacity: 0,
                y: 30
            }}

            animate={{
                opacity: 1,
                y: 0
            }}

            transition={{
                duration: 0.5
            }}

            className="
            rounded-3xl
            border
            border-white/10
            bg-white/5
            backdrop-blur-2xl
            p-6
            overflow-hidden
            "

        >


            {/* ==================================================
                HEADER
            ================================================== */}

            <div className="
            flex
            items-center
            justify-between
            mb-5
            ">


                <div className="
                flex
                items-center
                gap-3
                ">


                    <div className="
                    p-3
                    rounded-2xl
                    bg-blue-500/20
                    ">

                        <FiMapPin
                            className="
                            text-blue-400
                            text-xl
                            "
                        />

                    </div>


                    <div>

                        <h2 className="
                        text-white
                        font-semibold
                        ">

                            Live Location

                        </h2>


                        <p className="
                        text-xs
                        text-gray-400
                        ">

                            {userName} current position

                        </p>

                    </div>


                </div>


                {/* ONLINE STATUS */}

                <div className="
                flex
                items-center
                gap-2
                px-3
                py-2
                rounded-xl
                bg-black/30
                border
                border-white/10
                ">


                    <div className={`
                    h-2.5
                    w-2.5
                    rounded-full

                    ${
                        isOnline
                            ? "bg-green-400 shadow-[0_0_12px_#22c55e]"
                            : "bg-red-400 shadow-[0_0_12px_#ef4444]"
                    }

                    `} />


                    <span className={`
                    text-xs

                    ${
                        isOnline
                            ? "text-green-300"
                            : "text-red-300"
                    }

                    `}>

                        {
                            isOnline
                                ? "Live"
                                : "Offline"
                        }

                    </span>

                </div>


            </div>


            {/* ==================================================
                MAP
            ================================================== */}

            <div className="
            h-[350px]
            rounded-3xl
            overflow-hidden
            border
            border-white/10
            ">


                <MapContainer

                    center={[
                        latitude,
                        longitude
                    ]}

                    zoom={17}

                    scrollWheelZoom={true}

                    style={{
                        height: "100%",
                        width: "100%"
                    }}

                >


                    <TileLayer

                        url="
                        https://tile.openstreetmap.org/{z}/{x}/{y}.png
                        "

                        attribution="
                        © OpenStreetMap contributors
                        "

                    />


                    {/* Automatically move map when location changes */}

                    <MapAutoFocus

                        latitude={latitude}

                        longitude={longitude}

                    />


                    {/* ==================================================
                        LIVE MARKER
                    ================================================== */}

                    <Marker

                        position={[
                            latitude,
                            longitude
                        ]}

                    >

                        <Popup>

                            <div className="
                            text-black
                            ">

                                <strong>
                                    {userName}
                                </strong>

                                <br />

                                Live Location

                                <br />

                                Status:{" "}

                                {
                                    isOnline
                                        ? "Online"
                                        : "Offline"
                                }

                            </div>

                        </Popup>

                    </Marker>


                    {/* ==================================================
                        LOCATION RADIUS
                    ================================================== */}

                    <Circle

                        center={[
                            latitude,
                            longitude
                        ]}

                        radius={300}

                        pathOptions={{
                            color:
                                isOnline
                                    ? "green"
                                    : "red",

                            fillColor:
                                isOnline
                                    ? "green"
                                    : "red",

                            fillOpacity: 0.15
                        }}

                    />


                </MapContainer>


            </div>


            {/* ==================================================
                LOCATION INFORMATION
            ================================================== */}

            <div className="
            mt-5
            rounded-2xl
            bg-black/30
            border
            border-white/10
            p-4
            flex
            items-center
            gap-3
            ">


                <FiNavigation
                    className={`
                    ${
                        isOnline
                            ? "text-green-400"
                            : "text-red-400"
                    }
                    `}
                />


                <div>

                    <p className="
                    text-xs
                    text-gray-400
                    ">

                        Current Coordinates

                    </p>


                    <p className="
                    text-white
                    text-sm
                    ">

                        {latitude}, {longitude}

                    </p>


                    {
                        user?.timestamp && (

                            <p className="
                            text-xs
                            text-gray-500
                            mt-1
                            ">

                                Last update:{" "}
                                {new Date(
                                    user.timestamp
                                ).toLocaleString()}

                            </p>

                        )
                    }

                </div>


            </div>


        </motion.div>

    );

}