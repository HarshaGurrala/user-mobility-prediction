import { MapContainer, TileLayer, Marker, Popup, Circle } from "react-leaflet";
import L from "leaflet";

import "leaflet/dist/leaflet.css";

const currentPosition = [17.3850, 78.4867];
const predictedPosition = [17.4125, 78.4990];

const blueIcon = new L.Icon({
  iconUrl: "https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-blue.png",
  shadowUrl:
    "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
  iconSize: [25, 41],
  iconAnchor: [12, 41]
});

const redIcon = new L.Icon({
  iconUrl: "https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png",
  shadowUrl:
    "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
  iconSize: [25, 41],
  iconAnchor: [12, 41]
});

export default function LiveMap() {
  return (
    <div className="rounded-3xl overflow-hidden border border-white/10">

      <MapContainer
        center={currentPosition}
        zoom={13}
        style={{
          height: "500px",
          width: "100%"
        }}
      >

        <TileLayer
          attribution="OpenStreetMap"
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />

        <Marker position={currentPosition} icon={blueIcon}>
          <Popup>

            Current User Location

          </Popup>
        </Marker>

        <Marker position={predictedPosition} icon={redIcon}>
          <Popup>

            AI Predicted Location

          </Popup>
        </Marker>

        <Circle
          center={currentPosition}
          radius={500}
          pathOptions={{
            color: "green",
            fillColor: "green",
            fillOpacity: 0.2
          }}
        />

      </MapContainer>

    </div>
  );
}



