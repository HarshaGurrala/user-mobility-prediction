import { motion } from "framer-motion";

import {
  FiShield,
  FiMapPin,
  FiCheckCircle
} from "react-icons/fi";



export default function GuardianUserSafeZones({

user

}) {



const safeZones = [

{
name:"Home",
radius:"300 meters",
status:"Active"
},

{
name:"College",
radius:"500 meters",
status:"Active"
},

{
name:"Office",
radius:"400 meters",
status:"Active"
}

];




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
bg-green-500/20
"

>


<FiShield

className="
text-green-400
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

Safe Zones

</h2>


<p

className="
text-xs
text-gray-400
"

>

Guardian approved locations

</p>


</div>


</div>






<div

className="
space-y-3
"

>


{

safeZones.map((zone,index)=>(


<motion.div

key={index}

whileHover={{
scale:1.02
}}

className="
rounded-2xl
bg-black/30
border
border-white/10
p-4
flex
items-center
justify-between
"

>


<div

className="
flex
items-center
gap-3
"

>


<FiMapPin

className="
text-green-400
"

/>



<div>


<p

className="
text-white
"

>

{zone.name}

</p>


<p

className="
text-xs
text-gray-400
"

>

Radius: {zone.radius}

</p>


</div>


</div>






<div

className="
flex
items-center
gap-1
text-green-300
text-xs
"

>


<FiCheckCircle/>


{zone.status}


</div>




</motion.div>


))


}


</div>





</motion.div>

);

}