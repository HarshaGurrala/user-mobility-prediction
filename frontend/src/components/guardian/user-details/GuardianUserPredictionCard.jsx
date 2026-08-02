import { motion } from "framer-motion";

import {
  FiCpu,
  FiNavigation,
  FiClock,
  FiTrendingUp
} from "react-icons/fi";


export default function GuardianUserPredictionCard({

user

}) {



const prediction = {

destination:"Home",

confidence:94,

eta:"15 min"

};




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
relative
overflow-hidden
rounded-3xl
border
border-white/10
bg-white/5
backdrop-blur-2xl
p-6
shadow-[0_0_40px_rgba(80,80,255,.15)]
"

>



<div

className="
absolute
right-[-50px]
top-[-50px]
h-40
w-40
rounded-full
bg-blue-500/20
blur-3xl
"

/>





{/* Header */}

<div

className="
flex
items-center
gap-3
relative
"

>


<div

className="
p-3
rounded-2xl
bg-blue-500/20
"

>

<FiCpu

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

AI Prediction

</h2>


<p

className="
text-xs
text-gray-400
"

>

Future movement analysis

</p>


</div>


</div>







{/* Destination */}

<div

className="
mt-6
rounded-2xl
bg-black/30
border
border-white/10
p-5
"

>


<div

className="
flex
items-center
gap-3
"

>


<FiNavigation

className="
text-violet-400
text-xl
"

/>



<div>


<p

className="
text-xs
text-gray-400
"

>

Predicted Destination

</p>


<p

className="
text-white
font-medium
"

>

{prediction.destination}

</p>


</div>


</div>


</div>







{/* Stats */}

<div

className="
grid
grid-cols-2
gap-4
mt-5
"

>


<div

className="
rounded-2xl
bg-white/5
border
border-white/10
p-4
"

>


<FiTrendingUp

className="
text-green-400
"

/>


<p

className="
text-xs
text-gray-400
mt-2
"

>

Confidence

</p>


<p

className="
text-white
text-xl
"

>

{prediction.confidence}%

</p>


</div>






<div

className="
rounded-2xl
bg-white/5
border
border-white/10
p-4
"

>


<FiClock

className="
text-purple-400
"

/>


<p

className="
text-xs
text-gray-400
mt-2
"

>

ETA

</p>


<p

className="
text-white
text-xl
"

>

{prediction.eta}

</p>


</div>



</div>







<div

className="
mt-5
text-xs
text-blue-300
"

>

AI continuously learning {user?.name}'s movement pattern

</div>




</motion.div>

);

}