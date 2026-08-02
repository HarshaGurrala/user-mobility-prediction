import { motion } from "framer-motion";

import {
  FiShield,
  FiBell,
  FiUser,
} from "react-icons/fi";

import { useEffect, useState } from "react";

import { getUserProfile } from "../../services/locationService";


export default function GuardianTopBar() {


const [guardian,setGuardian] = useState(null);



useEffect(()=>{


const loadGuardian = async()=>{


try{


const guardianId =
localStorage.getItem("userId");



if(!guardianId){

return;

}



const data =
await getUserProfile(
    guardianId
);



setGuardian(data);



}

catch(error){


console.log(
"Guardian profile error:",
error
);


}


};




// first load

loadGuardian();




// refresh every 10 seconds

const interval =
setInterval(()=>{

loadGuardian();

},10000);



return ()=>clearInterval(interval);



},[]);





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
duration:0.6
}}

className="
mx-6
mt-6
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


<div className="
flex
items-center
justify-between
">


<div className="
flex
items-center
gap-4
">


<div className="
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
">


<FiShield

className="
text-blue-300
text-2xl
"

/>



<motion.div

animate={{
scale:[1,1.3,1]
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


<h1 className="
text-white
font-semibold
text-xl
">

Guardian AI

</h1>



<p className="
text-xs
text-gray-400
">

Family Safety Monitoring

</p>



</div>



</div>






<div className="
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
">


<div

className={`
h-3
w-3
rounded-full

${
guardian?.is_online
?
"bg-green-400 shadow-[0_0_20px_#22c55e]"
:
"bg-red-400 shadow-[0_0_20px_#ef4444]"
}

`}

/>



<div>


<p className="
text-xs
text-gray-400
">

SYSTEM STATUS

</p>



<p

className={`
text-sm

${
guardian?.is_online
?
"text-green-300"
:
"text-red-300"
}

`}

>


{
guardian?.is_online
?
"AI Tracking Active"
:
"Guardian Offline"
}



</p>



</div>



</div>






<div className="
flex
items-center
gap-4
">



<button

className="
relative
p-3
rounded-2xl
bg-white/5
border
border-white/10
"

>


<FiBell

className="
text-gray-300
text-xl
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







<div className="
flex
items-center
gap-3
px-4
py-2
rounded-2xl
bg-white/5
border
border-white/10
">



<div className="
h-9
w-9
rounded-xl
bg-gradient-to-br
from-blue-500
to-violet-500
flex
items-center
justify-center
">


<FiUser/>

</div>





<div className="hidden md:block">



<p className="
text-sm
text-white
">


{
guardian?.full_name || "Guardian"
}


</p>





<div className="
flex
items-center
gap-2
">



<div

className={`
h-2
w-2
rounded-full

${
guardian?.is_online
?
"bg-green-400"
:
"bg-red-400"
}

`}

/>




<p className="
text-xs
text-gray-400
">


{
guardian?.is_online
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