import { motion } from "framer-motion";

import {
  FiActivity,
  FiBell,
  FiShield,
  FiUser,
} from "react-icons/fi";



export default function DashboardHeader({
  user,
  onlineStatus
}) {


return (

<motion.div

initial={{
opacity:0,
y:-30
}}

animate={{
opacity:1,
y:0
}}

transition={{
duration:.6
}}

className="
mx-6
mt-5
rounded-3xl
border
border-white/10
bg-white/5
backdrop-blur-2xl
px-6
py-4
shadow-[0_0_40px_rgba(59,130,246,.15)]
"

>



<div

className="
flex
items-center
justify-between
"

>





{/* Brand */}


<div

className="
flex
items-center
gap-4
"

>


<div

className="
relative
h-12
w-12
rounded-2xl
bg-gradient-to-br
from-blue-500/40
to-violet-500/40
flex
items-center
justify-center
"

>


<FiShield

className="
text-blue-300
text-2xl
"

/>



<motion.div

animate={{

scale:[1,1.4,1]

}}

transition={{

repeat:Infinity,

duration:2

}}

className="
absolute
inset-0
rounded-2xl
bg-blue-400/20
"

/>


</div>







<div>


<h1

className="
text-white
font-semibold
text-xl
tracking-wide
"

>

Guardian AI

</h1>



<p

className="
text-xs
text-gray-400
"

>

User Mobility Prediction System

</p>


</div>



</div>









{/* Center Status */}


<div

className="
hidden
md:flex
items-center
gap-3
px-5
py-3
rounded-2xl
bg-black/30
border
border-white/10
"

>



<motion.div

animate={{

scale:[1,1.3,1]

}}

transition={{

repeat:Infinity,

duration:1.5

}}

className="
h-3
w-3
rounded-full
bg-green-400
shadow-[0_0_20px_#22c55e]
"

>



</motion.div>





<div>


<p

className="
text-xs
text-gray-400
"

>

SYSTEM STATUS

</p>



<p

className="
text-sm
text-green-300
"

>

AI Tracking Active

</p>


</div>



</div>









{/* Right section */}



<div

className="
flex
items-center
gap-4
"

>


<button

className="
relative
p-3
rounded-2xl
bg-white/5
border
border-white/10
hover:bg-white/10
transition
"

>


<FiBell

className="
text-gray-300
"

/>


<span

className="
absolute
top-2
right-2
h-2
w-2
rounded-full
bg-red-400
"

/>


</button>








<div

className="
flex
items-center
gap-3
px-4
py-2
rounded-2xl
bg-white/5
border
border-white/10
"

>


<div

className="
h-9
w-9
rounded-xl
bg-gradient-to-br
from-blue-500
to-violet-500
flex
items-center
justify-center
"

>


<FiUser/>

</div>



<div className="hidden md:block">

<p
className="
text-sm
text-white
"
>
{user?.full_name || "User"}
</p>


<div className="flex items-center gap-2">


<div

className={`
h-2
w-2
rounded-full

${
onlineStatus === "online"
?
"bg-green-400"
:
"bg-red-400"
}

`}

/>


<p

className="
text-xs
text-gray-400
"

>

{
onlineStatus === "online"
?
"Online"
:
"Offline"
}

</p>


</div>



</div>



</div>




</div>






</div>



</motion.div>

);


}