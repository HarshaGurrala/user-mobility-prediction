import {
  MapContainer,
  TileLayer,
  Marker,
  Circle,
  useMapEvents,
} from "react-leaflet";
import { useState, useEffect } from "react";
import L from "leaflet";
import { useSafeZone } from "../../context/SafeZoneContext";
import "leaflet/dist/leaflet.css";
import { getSafeZones } from "../../services/safeZoneService";
const markerIcon = new L.Icon({
  iconUrl:
    "https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-green.png",
  shadowUrl:
    "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
  iconSize: [30, 46],
  iconAnchor: [15, 46],
});

function LocationMarker({
  position,
  setPosition,
  radius,
}) {

  useMapEvents({

    click(e) {

      setPosition([
        e.latlng.lat,
        e.latlng.lng,
      ]);

    },

  });

  return position ? (

    <>

      <Marker
        position={position}
        icon={markerIcon}
      />

      <Circle
        center={position}
        radius={radius}
        pathOptions={{
          color: "#22c55e",
          fillColor: "#22c55e",
          fillOpacity: 0.2,
        }}
      />

    </>

  ) : null;

}

export default function SafeZoneMap() {

  const { position, setPosition } = useSafeZone();

  const [safeZones, setSafeZones] = useState([]);

  useEffect(() => {

    loadSafeZones();

  }, []);

  async function loadSafeZones() {

    try {

      const data = await getSafeZones();

      setSafeZones(data);

    }

    catch (err) {

      console.log(err);

    }

  }

  return (

    <div
      className="
      bg-slate-900/70
      backdrop-blur-xl
      border
      border-white/10
      rounded-3xl
      overflow-hidden
      "
    >

      <div className="p-6 border-b border-white/10">

        <h2 className="text-2xl font-bold text-white">

          Select Safe Zone

        </h2>

        <p className="text-slate-400 mt-2">

          Click anywhere on the map.

        </p>

      </div>

      <MapContainer
        center={[16.5062, 80.6480]}
        zoom={15}
        style={{
          height: "600px",
          width: "100%",
        }}
      >

        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />

        <LocationMarker
  position={position}
  setPosition={setPosition}
  radius={200}
/>

      </MapContainer>

    </div>
   

  );

}
