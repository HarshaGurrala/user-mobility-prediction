import { motion } from "framer-motion";

import {
  FiAlertTriangle,
  FiShield,
  FiClock,
} from "react-icons/fi";
import { useEffect, useState } from "react";
import { getGuardianAlerts } from "../../services/guardianApi";

export default function GuardianAlertFeed() {


const [alerts,setAlerts] = useState([]);



useEffect(()=>{


const loadAlerts = async()=>{


try{


const guardianId = localStorage.getItem("userId");


const data = await getGuardianAlerts(
    guardianId
);


setAlerts(data);


}

catch(error){


console.log(
"Alert API Error:",
error
);


}


};


loadAlerts();


},[]);



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
duration:0.6
}}

className="
rounded-3xl
border
border-white/10
bg-white/5
backdrop-blur-2xl
p-6
shadow-[0_0_40px_rgba(255,80,80,.12)]
"

>



{/* Header */}

<div

className="
flex
items-center
gap-3
mb-6
"

>


<div

className="
p-3
rounded-2xl
bg-red-500/20
"

>

<FiAlertTriangle

className="
text-red-400
text-xl
"

/>


</div>



<div>

<h2

className="
text-white
font-semibold
text-lg
"

>

Guardian Notifications

</h2>


<p

className="
text-xs
text-gray-400
"

>

All user safety events

</p>


</div>


</div>







<div

className="
space-y-3
"

>


{
alerts.map((alert)=>(


<motion.div

key={alert.id}

whileHover={{

scale:1.02

}}

className="
rounded-2xl
bg-black/30
border
border-white/10
p-4
"

>


<div

className="
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


{

alert.type==="SAFE"

?

<FiShield

className="
text-green-400
"

/>

:

<FiAlertTriangle

className="
text-yellow-400
"

/>

}



<div>


<p

className="
text-white
text-sm
font-medium
"

>

{alert.user}

</p>



<p

className="
text-gray-300
text-sm
"

>

{alert.message}

</p>


</div>


</div>





<span

className={`

text-xs
px-3
py-1
rounded-full

${
alert.type==="SAFE"

?

"bg-green-500/20 text-green-300"

:

"bg-yellow-500/20 text-yellow-300"

}

`}

>

{alert.type}

</span>


</div>







<div

className="
flex
items-center
gap-2
mt-3
text-xs
text-gray-400
"

>


<FiClock/>


{alert.time}


</div>



</motion.div>



))

}


</div>



</motion.div>


);

}