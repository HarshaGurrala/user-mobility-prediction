import {
    MapContainer,
    TileLayer,
    Marker,
    Popup,
    Circle,
    useMap
} from "react-leaflet";

import "leaflet/dist/leaflet.css";

import L from "leaflet";
import { motion } from "framer-motion";
import { useEffect } from "react";


// ==================================================
// LEAFLET MARKER FIX
// ==================================================

delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({

    iconRetinaUrl:
        "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png",

    iconUrl:
        "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png",

    shadowUrl:
        "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png"

});


// ==================================================
// AUTO FOCUS
// ==================================================

// ==================================================
// AUTO FOCUS
// ==================================================

function MapAutoFocus({ position }) {

    const map = useMap();

    useEffect(() => {

        if (!position) return;


        // Initial focus
        const focusMap = () => {

            map.invalidateSize();

            map.flyTo(
                position,
                15,
                {
                    animate: true,
                    duration: 1
                }
            );

        };


        // Initial focus after map renders
        const initialTimer = setTimeout(
            focusMap,
            300
        );


        // Automatically refocus every 30 seconds
        const interval = setInterval(() => {

            focusMap();

        }, 30000);


        return () => {

            clearTimeout(initialTimer);

            clearInterval(interval);

        };

    }, [
        position?.[0],
        position?.[1],
        map
    ]);

    return null;
}


// ==================================================
// FIX MAP SIZE AFTER INITIAL RENDER
// ==================================================

function MapResizeHandler() {

    const map = useMap();

    useEffect(() => {

        const resizeMap = () => {
            map.invalidateSize();
        };

        const timer1 = setTimeout(
            resizeMap,
            100
        );

        const timer2 = setTimeout(
            resizeMap,
            500
        );

        const timer3 = setTimeout(
            resizeMap,
            1000
        );

        window.addEventListener(
            "resize",
            resizeMap
        );

        return () => {

            clearTimeout(timer1);
            clearTimeout(timer2);
            clearTimeout(timer3);

            window.removeEventListener(
                "resize",
                resizeMap
            );

        };

    }, [map]);

    return null;
}


// ==================================================
// LIVE MAP
// ==================================================

export default function LiveMap({
    latitude,
    longitude,
    place,
    userName
}) {

    const lat = Number(latitude);
    const lng = Number(longitude);

    const position =
        latitude !== null &&
        latitude !== undefined &&
        longitude !== null &&
        longitude !== undefined &&
        !Number.isNaN(lat) &&
        !Number.isNaN(lng)
            ? [lat, lng]
            : null;


    return (

        <motion.div

            initial={{
                opacity: 0,
                scale: 0.95
            }}

            animate={{
                opacity: 1,
                scale: 1
            }}

            transition={{
                duration: 0.8
            }}

            className="
            relative
            h-[520px]
            rounded-[35px]
            overflow-hidden
            "

        >

            {/* ==================================================
                HEADER
            ================================================== */}

            <div
                className="
                absolute
                top-6
                left-6
                z-[500]
                bg-black/60
                backdrop-blur-xl
                border
                border-white/10
                rounded-2xl
                px-5
                py-3
                "
            >

                <p
                    className="
                    text-xs
                    text-gray-400
                    "
                >
                    LIVE LOCATION
                </p>

                <h3
                    className="
                    text-white
                    font-semibold
                    "
                >
                    {userName || "User"}
                </h3>

                <p
                    className="
                    text-xs
                    text-gray-400
                    mt-1
                    "
                >
                    {place || "Current location"}
                </p>

            </div>


            {/* ==================================================
                MAP
            ================================================== */}

            <MapContainer

                center={
                    position ||
                    [16.0545, 80.0025]
                }

                zoom={15}

                scrollWheelZoom={true}

                style={{
                    height: "100%",
                    width: "100%"
                }}

            >

                <TileLayer
                    attribution="&copy; Esri"
                    url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
                    maxZoom={19}
                />


                <MapResizeHandler />


                {position && (

                    <MapAutoFocus
                        position={position}
                    />

                )}


                {position && (

                    <Marker
    position={position}
>

    <Popup>

        <div className="text-black">

            <strong>
                {userName || "User"}
            </strong>

            <br />

            Live Location

            <br />

            {lat.toFixed(6)},{" "}
            {lng.toFixed(6)}

        </div>

    </Popup>

</Marker>

                )}


                {position && (

                    <Circle

                        center={position}

                        radius={300}

                        pathOptions={{
                            opacity: 0.5
                        }}

                    />

                )}

            </MapContainer>

        </motion.div>

    );

}