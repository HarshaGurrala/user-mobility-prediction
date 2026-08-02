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

delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({

    iconRetinaUrl:
        "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png",

    iconUrl:
        "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png",

    shadowUrl:
        "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png"

});


function MapAutoFocus({ position }) {

    const map = useMap();

    useEffect(() => {

        if (!position) return;

        const timer = setTimeout(() => {

            map.invalidateSize();

            map.flyTo(
                position,
                15,
                {
                    animate: true,
                    duration: 1.5
                }
            );

        }, 300);

        return () => clearTimeout(timer);

    }, [position, map]);

    return null;
}



export default function LiveMap({

    latitude,
    longitude,
    place

}) {

    const position =
        latitude != null &&
        longitude != null
            ? [
                  Number(latitude),
                  Number(longitude)
              ]
            : null;

    return (

        <motion.div

            initial={{
                opacity: 0,
                scale: .95
            }}

            animate={{
                opacity: 1,
                scale: 1
            }}

            transition={{
                duration: .8
            }}

            className="
relative
h-[520px]
rounded-[35px]
overflow-hidden
"

        >

            <div

                className="
absolute
top-6
left-6
z-[500]
bg-black/50
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
font-semibold
"
                >

                    {place || "Unknown Location"}

                </h3>

            </div>



            <MapContainer

                center={position || [16.0545, 80.0025]}

                zoom={15}

                scrollWheelZoom={true}

                style={{

                    height: "100%",

                    width: "100%"

                }}

            >

                <TileLayer

                    url="https://tile.openstreetmap.org/{z}/{x}/{y}.png"

                />

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

                            {place}

                        </Popup>

                    </Marker>

                )}

                {position && (

                    <Circle

                        center={position}

                        radius={300}

                        pathOptions={{

                            opacity: .5

                        }}

                    />

                )}

            </MapContainer>

        </motion.div>

    );

}