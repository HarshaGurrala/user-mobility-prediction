import { motion } from "framer-motion";

import {
  MapContainer,
  TileLayer,
  Marker,
  Popup,
  Circle
} from "react-leaflet";
import { useEffect, useRef } from "react";
import { useMap } from "react-leaflet";
import "leaflet/dist/leaflet.css";

import {
  FiMapPin,
  FiNavigation
} from "react-icons/fi";



import { useEffect } from "react";
import { useMap } from "react-leaflet";

function MapAutoFocus({ latitude, longitude }) {

    const map = useMap();

    useEffect(() => {

        if (
            latitude == null ||
            longitude == null
        ) return;

        setTimeout(() => {

            map.flyTo(
                [latitude, longitude],
                17,
                {
                    animate: true,
                    duration: 2
                }
            );

        }, 300);

    }, [latitude, longitude]);

    return null;
}



export default function GuardianUserLiveMap({

user

}) {


const latitude = Number(
    user?.latitude ??
    user?.location?.latitude
);


const longitude = Number(
    user?.longitude ??
    user?.location?.longitude
);


const location = {
    lat: latitude,
    lng: longitude
};

if(
    !location.lat ||
    !location.lng
){

return (

<div
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

</div>

);

}

return (

<motion.div

initial={{
opacity:0,
y:30
}}

animate={{
opacity:1,
y:0
}}

transition={{
duration:0.5
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



{/* Header */}

<div

className="
flex
items-center
gap-3
mb-5
"

>


<div

className="
p-3
rounded-2xl
bg-blue-500/20
"

>

<FiMapPin

className="
text-blue-400
text-xl
"

/>

</div>



<div>


<h2

className="
text-white
font-semibold
"

>

Live Location

</h2>


<p

className="
text-xs
text-gray-400
"

>

{user?.name || "User"} current position

</p>


</div>


</div>





{/* Map */}

<div

className="
h-[350px]
rounded-3xl
overflow-hidden
border
border-white/10
"

>


<MapContainer

center={[
location.lat,
location.lng
]}

zoom={15}

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

<MapAutoFocus

latitude={location.lat}

longitude={location.lng}

/>



<Marker

position={[
location.lat,
location.lng
]}

>


<Popup>

{user?.name}

<br/>

Live Location

</Popup>


</Marker>




<Circle

center={[
location.lat,
location.lng
]}

radius={300}

/>



</MapContainer>


</div>





{/* Location Info */}

<div

className="
mt-5
rounded-2xl
bg-black/30
border
border-white/10
p-4
flex
items-center
gap-3
"

>


<FiNavigation

className="
text-green-400
"

/>


<div>


<p

className="
text-xs
text-gray-400
"

>

Current Area

</p>


<p
className="
text-white
"

>

{user?.location || "Unknown Location"}

</p>


</div>


</div>



</motion.div>

);

}