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
import usePrediction from "../../hooks/usePrediction";
import "leaflet/dist/leaflet.css";

import { getChildLocation } from "../../services/locationService";

const { BaseLayer } = LayersControl;
//const prediction = usePrediction();
const prediction = null;


const predictionIcon = new L.Icon({
  iconUrl:
    "https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-blue.png",
  shadowUrl:
    "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
  iconSize: [30, 46],
  iconAnchor: [15, 46],
});


const childIcon = new L.Icon({
  iconUrl:
    "https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png",
  shadowUrl:
    "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
  iconSize: [30, 46],
  iconAnchor: [15, 46],
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

export default function GuardianLiveMap({ childId = 7 }) {

  const prediction = usePrediction();

  const [location, setLocation] = useState(null);

  const [path, setPath] = useState([]);


  useEffect(() => {
    loadLocation();

    const interval = setInterval(() => {
      loadLocation();
    }, 5000);

    return () => clearInterval(interval);
  }, []);

  async function loadLocation() {
    try {
      const data = await getChildLocation(childId);

      if (data.message) return;

      setLocation(data);

      setPath((old) => [
        ...old,
        [data.latitude, data.longitude],
      ]);
    } catch (err) {
      console.log(err);
    }
  }

  if (!location) {
    return (
      <div className="h-[600px] flex items-center justify-center bg-slate-900 rounded-3xl text-white">
        Loading Child Location...
      </div>
    );
  }

  const currentPosition = [
    location.latitude,
    location.longitude,
  ];

  return (
    <div
      className="
      bg-slate-900/70
      backdrop-blur-xl
      border
      border-white/10
      rounded-3xl
      overflow-hidden
      mt-8
      "
    >
      <div className="p-6 border-b border-white/10">
        <h2 className="text-3xl font-bold text-white">
          Live Child Tracking
        </h2>

        <p className="text-slate-400 mt-2">
          Guardian Monitoring Dashboard
        </p>
      </div>

      <MapContainer
        center={currentPosition}
        zoom={18}
        style={{
          height: "600px",
          width: "100%",
        }}
      >
        <ChangeMapView center={currentPosition} />

        <LayersControl position="topright">
          <BaseLayer checked name="Satellite">
          <TileLayer
  attribution="Google Satellite"
  url="https://mt1.google.com/vt/lyrs=s&x={x}&y={y}&z={z}"
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
          icon={childIcon}
        >
          <Popup>
            <h3>{location.child}</h3>

            <hr />

            <p>🟢 Online</p>

            <p>🔋 Battery : {location.battery}%</p>

            <p>🚗 Speed : {location.speed} km/h</p>

            <p>📍 Live Tracking Enabled</p>
          </Popup>
        </Marker>

        {prediction && (

  <Marker
    position={[
      prediction.latitude,
      prediction.longitude,
    ]}
    icon={predictionIcon}
  >

    <Popup>

      <h3>AI Predicted Destination</h3>

      <hr />

      <p>
        Confidence : {prediction.confidence}%
      </p>

      <p>
        ETA : {prediction.eta} mins
      </p>

      <p>
        Next Stop
      </p>

    </Popup>

  </Marker>

)}

        <Circle
          center={currentPosition}
          radius={location.accuracy}
          pathOptions={{
            color: "#06b6d4",
            fillColor: "#06b6d4",
            fillOpacity: 0.2,
          }}
        />

        <Polyline
          positions={path}
          pathOptions={{
            color: "#06b6d4",
            weight: 6,
          }}
        />

          {prediction && (

  <Polyline
    positions={[
      currentPosition,
      [
        prediction.latitude,
        prediction.longitude,
      ],
    ]}
    pathOptions={{
      color: "#22c55e",
      weight: 4,
      dashArray: "10,10",
    }}
  />

)}

      </MapContainer>
    </div>
  );
}