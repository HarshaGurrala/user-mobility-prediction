import { motion } from "framer-motion";

import {
  MapContainer,
  TileLayer,
  Marker,
  Popup,
  Circle,
  useMap
} from "react-leaflet";

import { useEffect, useState } from "react";
import { getGuardianMapUsers } from "../../services/guardianApi";

import "leaflet/dist/leaflet.css";

import {
  FiArrowLeft,
  FiUsers
} from "react-icons/fi";

import { useNavigate } from "react-router-dom";
import { Fragment } from "react";



function MapFocus({users}) {

const map = useMap();


useEffect(()=>{


const locations = users
.filter(
(user)=>
user.latitude &&
user.longitude
)
.map(
(user)=>[
Number(user.latitude),
Number(user.longitude)
]
);


if(locations.length > 0){

map.fitBounds(
locations,
{
padding:[50,50]
}
);

}


},[users,map]);


return null;

}




export default function GuardianFamilyMap(){


const navigate = useNavigate();


const [users,setUsers] = useState([]);




useEffect(()=>{


const loadLocations = async()=>{


try{


const data = await getGuardianMapUsers();


console.log(
"Live Map Users:",
data
);


setUsers(data);


}

catch(error){


console.log(
"Live location API Error:",
error
);


}


};



loadLocations();



const interval =
setInterval(
loadLocations,
10000
);



return ()=>clearInterval(interval);



},[]);




return (

<motion.div
style={{
height:"100vh",
width:"100vw"
}}
className="
bg-black
"
>



<div

className="
absolute
top-5
left-5
z-[1000]
rounded-3xl
bg-black/60
backdrop-blur-xl
border
border-white/10
px-5
py-4
"

>


<button

onClick={()=>navigate(-1)}

className="
flex
items-center
gap-3
text-white
"

>

<FiArrowLeft />

Back

</button>


</div>






<div

className="
absolute
top-5
right-5
z-[1000]
rounded-3xl
bg-black/60
backdrop-blur-xl
border
border-white/10
px-5
py-4
"

>

<div

className="
flex
items-center
gap-3
text-white
"

>


<div

className="
p-3
rounded-2xl
bg-blue-500/20
"

>

<FiUsers
className="
text-blue-400
text-xl
"
/>

</div>


<div>

<p className="
text-xs
text-gray-400
">

LIVE USERS

</p>


<p className="
text-sm
font-medium
">

{users.length} Connected

</p>


</div>


</div>


</div>








<MapContainer

center={[
17.3850,
78.4867
]}

zoom={13}

scrollWheelZoom={true}

style={{
height:"100%",
width:"100%"
}}

>


<MapFocus users={users}/>



<TileLayer

url="
https://tile.openstreetmap.org/{z}/{x}/{y}.png
"

/>





{

users
.filter(
(user)=>
user.latitude &&
user.longitude
)
.map((user)=>(


<Fragment

key={user.user_id}

>


<Marker

position={[
Number(user.latitude),
Number(user.longitude)
]}

>


<Popup>


<div className="text-black">


<h3 className="font-semibold">

{user.user_name}

</h3>


<p>

SafePath ID:
{" "}
{user.safe_path_id}

</p>



<p>

Status:

<span

className={`
ml-1
font-medium
${
user.is_online
?
"text-green-600"
:
"text-red-600"
}
`}

>

{
user.is_online
?
"Online"
:
"Offline"
}

</span>


</p>


</div>


</Popup>


</Marker>







<Circle

center={[
Number(user.latitude),
Number(user.longitude)
]}

radius={300}

pathOptions={{

color:
user.is_online
?
"green"
:
"red",

fillOpacity:0.15

}}

/>





</Fragment>


))

}



</MapContainer>








</motion.div>

);

}