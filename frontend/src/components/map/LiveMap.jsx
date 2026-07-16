import {
  MapContainer,
  TileLayer,
  Marker,
  Popup,
  Circle,
  Polyline,
  LayersControl,
} from "react-leaflet";

import { useEffect, useState } from "react";
import { useMap } from "react-leaflet";
import L from "leaflet";

import "leaflet/dist/leaflet.css";

import useLocation from "../../hooks/useLocation";

const { BaseLayer } = LayersControl;

const blueIcon = new L.Icon({
  iconUrl:
    "https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-blue.png",
  shadowUrl:
    "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
  iconSize: [28, 45],
  iconAnchor: [14, 45],
});

function ChangeMapView({ center }) {

  const map = useMap();

  useEffect(() => {

    map.flyTo(center, 18, {
      animate: true,
      duration: 1.5,
    });

  }, [center, map]);

  return null;
}

export default function LiveMap() {

  const { location } = useLocation();

  const [path, setPath] = useState([]);

  useEffect(() => {

    if (!location) return;

    setPath((previous) => [

      ...previous,

      [location.latitude, location.longitude],

    ]);

  }, [location]);

  if (!location) {

    return (

      <div className="h-[500px] bg-slate-900 rounded-3xl flex items-center justify-center text-white">

        Waiting for GPS...

      </div>

    );

  }

  const currentPosition = [

    location.latitude,

    location.longitude,

  ];

  return (

    <div className="rounded-3xl overflow-hidden border border-white/10">

      <MapContainer
        center={currentPosition}
        zoom={18}
        style={{
          height: "550px",
          width: "100%",
        }}
      >

        <ChangeMapView center={currentPosition} />

        <LayersControl position="topright">

          <BaseLayer checked name="Satellite">

            <TileLayer
              attribution="Esri"
              url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
            />

          </BaseLayer>

          <BaseLayer name="Street">

            <TileLayer
              attribution="OpenStreetMap"
              url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />

          </BaseLayer>

        </LayersControl>

        <Marker
          position={currentPosition}
          icon={blueIcon}
        >

          <Popup>

            <b>You are here</b>

            <br />

            Lat: {location.latitude.toFixed(6)}

            <br />

            Lng: {location.longitude.toFixed(6)}

          </Popup>

        </Marker>

        <Circle
          center={currentPosition}
          radius={location.accuracy}
          pathOptions={{
            color: "#002fff",
            fillColor: "#0a53c8",
            fillOpacity: 0.18,
          }}
        />

        {/* <Polyline
          positions={path}
          pathOptions={{
            color: "#191599a9",
            weight: 5,
          }}
        /> */}

      </MapContainer>

    </div>

  );

}