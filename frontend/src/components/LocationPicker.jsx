import React, { useEffect, useState } from "react";
import {
    MapContainer,
    TileLayer,
    Marker,
    useMapEvents,
    useMap
} from "react-leaflet";

import L from "leaflet";
import "leaflet/dist/leaflet.css";

// Fix Leaflet marker icons in Vite
delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({
    iconRetinaUrl:
        "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/images/marker-icon-2x.png",

    iconUrl:
        "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/images/marker-icon.png",

    shadowUrl:
        "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/images/marker-shadow.png"
});


// =====================================================
// MAP CLICK HANDLER
// =====================================================

const MapClickHandler = ({ onLocationSelect }) => {

    useMapEvents({
        click(event) {

            const latitude =
                event.latlng.lat;

            const longitude =
                event.latlng.lng;

            onLocationSelect(
                latitude,
                longitude
            );
        }
    });

    return null;
};


// =====================================================
// MOVE MAP TO LOCATION
// =====================================================

const MapCenter = ({
    latitude,
    longitude
}) => {

    const map = useMap();

    useEffect(() => {

        if (
            latitude &&
            longitude
        ) {

            map.setView(
                [
                    latitude,
                    longitude
                ],
                17
            );
        }

    }, [
        latitude,
        longitude,
        map
    ]);

    return null;
};


// =====================================================
// LOCATION PICKER
// =====================================================

const LocationPicker = ({
    initialLatitude,
    initialLongitude,
    onConfirm,
    onClose
}) => {

    const defaultLatitude =
        Number(initialLatitude) || 17.6868;

    const defaultLongitude =
        Number(initialLongitude) || 83.2185;

    const [selectedLocation, setSelectedLocation] =
        useState({

            latitude:
                defaultLatitude,

            longitude:
                defaultLongitude
        });

        const [mapType, setMapType] =
    useState("satellite");


    const handleLocationSelect = (
        latitude,
        longitude
    ) => {

        setSelectedLocation({

            latitude,
            longitude

        });

    };


    const handleConfirm = () => {

        onConfirm(
            selectedLocation.latitude,
            selectedLocation.longitude
        );

    };


    return (

        <div className="
            fixed
            inset-0
            z-[9999]
            flex
            items-center
            justify-center
            bg-black/70
            p-4
        ">

            <div className="
                flex
                h-[90vh]
                w-full
                max-w-5xl
                flex-col
                overflow-hidden
                rounded-2xl
                border
                border-white/10
                bg-[#111111]
                shadow-2xl
            ">

                {/* HEADER */}

                <div className="
                    flex
                    items-center
                    justify-between
                    border-b
                    border-white/10
                    px-5
                    py-4
                ">

                    <div>

                        <h2 className="
                            text-lg
                            font-semibold
                            text-white
                        ">
                            Select Exact Location
                        </h2>

                        <p className="
                            mt-1
                            text-xs
                            text-gray-400
                        ">
                            Click the exact house, school,
                            office or other location on the map.
                        </p>

                    </div>


                    <button
                        type="button"
                        onClick={onClose}
                        className="
                            rounded-lg
                            px-3
                            py-2
                            text-gray-400
                            hover:bg-white/10
                            hover:text-white
                        "
                    >
                        ✕
                    </button>

                </div>


                {/* MAP */}

                <div className="
                    min-h-0
                    flex-1
                ">

                   <div className="relative h-full w-full">

    <MapContainer
        center={[
            defaultLatitude,
            defaultLongitude
        ]}
        zoom={17}
        scrollWheelZoom={true}
        className="h-full w-full"
    >

        {/* ============================= */}
        {/* SATELLITE VIEW */}
        {/* ============================= */}

        {mapType === "satellite" && (
            <TileLayer
                attribution="&copy; Esri"
                url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
                maxZoom={19}
            />
        )}


        {/* ============================= */}
        {/* STREET VIEW */}
        {/* ============================= */}

        {mapType === "streets" && (
            <TileLayer
                attribution="&copy; OpenStreetMap contributors"
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                maxZoom={19}
            />
        )}


        <MapCenter
            latitude={
                selectedLocation.latitude
            }
            longitude={
                selectedLocation.longitude
            }
        />


        <MapClickHandler
            onLocationSelect={
                handleLocationSelect
            }
        />


        <Marker
            position={[
                selectedLocation.latitude,
                selectedLocation.longitude
            ]}
        />

    </MapContainer>


    {/* ============================= */}
    {/* MAP TYPE TOGGLE */}
    {/* ============================= */}

    <div className="
        absolute
        right-4
        top-4
        z-[1000]
        flex
        overflow-hidden
        rounded-xl
        border
        border-white/20
        bg-black/80
        shadow-xl
        backdrop-blur-md
    ">

        <button
            type="button"
            onClick={() =>
                setMapType("satellite")
            }
            className={`
                px-4
                py-2.5
                text-xs
                font-medium
                transition
                ${
                    mapType === "satellite"
                        ? "bg-violet-600 text-white"
                        : "text-gray-300 hover:bg-white/10"
                }
            `}
        >
            🛰️ Satellite
        </button>


        <button
            type="button"
            onClick={() =>
                setMapType("streets")
            }
            className={`
                px-4
                py-2.5
                text-xs
                font-medium
                transition
                ${
                    mapType === "streets"
                        ? "bg-violet-600 text-white"
                        : "text-gray-300 hover:bg-white/10"
                }
            `}
        >
            🛣️ Streets
        </button>

    </div>

</div>

                </div>


                {/* FOOTER */}

                <div className="
                    border-t
                    border-white/10
                    bg-black/30
                    px-5
                    py-4
                ">

                    <div className="
                        mb-4
                        grid
                        grid-cols-1
                        gap-3
                        md:grid-cols-2
                    ">

                        <div>
                            <label className="
                                mb-1
                                block
                                text-xs
                                text-gray-400
                            ">
                                Latitude
                            </label>

                            <div className="
                                rounded-lg
                                border
                                border-white/10
                                bg-black/30
                                px-3
                                py-2
                                text-sm
                                text-white
                            ">
                                {
                                    selectedLocation.latitude
                                }
                            </div>
                        </div>


                        <div>
                            <label className="
                                mb-1
                                block
                                text-xs
                                text-gray-400
                            ">
                                Longitude
                            </label>

                            <div className="
                                rounded-lg
                                border
                                border-white/10
                                bg-black/30
                                px-3
                                py-2
                                text-sm
                                text-white
                            ">
                                {
                                    selectedLocation.longitude
                                }
                            </div>
                        </div>

                    </div>


                    <div className="
                        flex
                        justify-end
                        gap-3
                    ">

                        <button
                            type="button"
                            onClick={onClose}
                            className="
                                rounded-xl
                                border
                                border-white/10
                                px-5
                                py-2.5
                                text-sm
                                text-gray-300
                                hover:bg-white/10
                            "
                        >
                            Cancel
                        </button>


                        <button
                            type="button"
                            onClick={handleConfirm}
                            className="
                                rounded-xl
                                bg-violet-600
                                px-5
                                py-2.5
                                text-sm
                                font-medium
                                text-white
                                hover:bg-violet-500
                            "
                        >
                            Confirm Location
                        </button>

                    </div>

                </div>

            </div>

        </div>
    );
};

export default LocationPicker;