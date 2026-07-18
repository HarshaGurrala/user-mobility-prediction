import {
    MapContainer,
    TileLayer,
    Marker,
    Popup,
    Circle
} from "react-leaflet";

import "leaflet/dist/leaflet.css";

import L from "leaflet";
import { motion } from "framer-motion";


delete L.Icon.Default.prototype._getIconUrl;


L.Icon.Default.mergeOptions({

    iconRetinaUrl:
    "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png",

    iconUrl:
    "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png",

    shadowUrl:
    "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png"

});



export default function LiveMap({

    latitude,
    longitude,
    place

}){


const position=[

latitude || 16.0545,

longitude || 80.0025

];



return (

<motion.div

initial={{
opacity:0,
scale:.95
}}

animate={{
opacity:1,
scale:1
}}

transition={{
duration:.8
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

center={position}

zoom={15}

scrollWheelZoom={true}

style={{

height:"100%",

width:"100%"

}}

>


<TileLayer

url="
https://tile.openstreetmap.org/{z}/{x}/{y}.png
"

/>



<Marker

position={position}

>


<Popup>

{place}

</Popup>


</Marker>





<Circle

center={position}

radius={300}

pathOptions={{

opacity:.5

}}

/>



</MapContainer>



</motion.div>


);

}