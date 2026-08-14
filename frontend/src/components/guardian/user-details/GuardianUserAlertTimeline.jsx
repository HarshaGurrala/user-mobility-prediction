import { motion } from "framer-motion";

import {
  FiBell,
  FiAlertTriangle,
  FiClock,
  FiShield
} from "react-icons/fi";


export default function GuardianUserAlertTimeline({

user

}) {



const alerts = [

{
type:"Safe",
message:"Reached Home safely",
time:"Today 6:30 PM"
},

{
type:"Warning",
message:"Movement detected outside normal area",
time:"Today 2:15 PM"
},

{
type:"Safe",
message:"Entered approved safe zone",
time:"Yesterday 8:00 AM"
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


<FiBell

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
"

>

Notifications

</h2>


<p

className="
text-xs
text-gray-400
"

>

{user?.name} safety activity

</p>


</div>


</div>






<div

className="
space-y-4
"

>


{

alerts.map((alert,index)=>(


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
"

>


<div

className="
flex
items-start
gap-3
"

>


<div>


{

alert.type==="Warning"

?

<FiAlertTriangle

className="
text-yellow-400
text-xl
"

/>

:

<FiShield

className="
text-green-400
text-xl
"

/>


}


</div>





<div

className="
flex-1
"

>


<p

className="
text-white
text-sm
"

>

{alert.message}

</p>



<div

className="
flex
items-center
gap-2
mt-2
text-xs
text-gray-400
"

>

<FiClock/>

{formatAlertTime(alert.time)}{alert.time}

</div>


</div>



<span

className={`
text-xs
px-3
py-1
rounded-full

${
alert.type==="Warning"

?

"bg-yellow-500/20 text-yellow-300"

:

"bg-green-500/20 text-green-300"

}

`}

>

{alert.type}

</span>



</div>


</motion.div>


))


}


</div>






</motion.div>

);

}