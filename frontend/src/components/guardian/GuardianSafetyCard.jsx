import { motion } from "framer-motion";

import {
  FiShield,
  FiCheckCircle,
  FiAlertTriangle,
  FiUsers,
} from "react-icons/fi";
import { useEffect, useState } from "react";
import { getGuardianSafety } from "../../services/guardianApi";

export default function GuardianSafetyCard() {


const [data,setData] = useState({

totalUsers:0,

safeUsers:0,

warningUsers:0,

alerts:0

});



useEffect(()=>{


const loadSafety = async()=>{


try{


const guardianId = localStorage.getItem("userId");


const response = await getGuardianSafety(
    guardianId
);


setData(response);


}

catch(error){


console.log(
"Safety API Error:",
error
);


}


};



loadSafety();



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
relative
overflow-hidden
rounded-3xl
border
border-white/10
bg-white/5
backdrop-blur-2xl
p-6
shadow-[0_0_40px_rgba(34,197,94,.15)]
"

>



<div

className="
absolute
right-[-60px]
top-[-60px]
h-44
w-44
rounded-full
bg-green-500/20
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
text-lg
"

>

Family Safety

</h2>


<p

className="
text-xs
text-gray-400
"

>

Guardian monitoring overview

</p>


</div>


</div>





{/* Main Status */}

<div

className="
mt-6
rounded-2xl
bg-black/30
border
border-white/10
p-5
flex
items-center
justify-between
"

>


<div>

<p

className="
text-xs
text-gray-400
"

>

Overall Status

</p>


<h3

className={`
text-2xl
font-semibold
mt-1

${
data.warningUsers > 0
?
"text-yellow-300"
:
"text-green-300"
}

`}

>

{
data.warningUsers > 0
?
"WARNING"
:
"SAFE"
}

</h3>




</div>



{
data.warningUsers > 0
?
<FiAlertTriangle
className="
text-yellow-400
text-4xl
"
/>
:
<FiCheckCircle
className="
text-green-400
text-4xl
"
/>
}


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


<FiUsers

className="
text-blue-400
mb-2
"

/>


<p

className="
text-xs
text-gray-400
"

>

Linked Users

</p>


<p

className="
text-xl
text-white
"

>

{data.totalUsers}

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


<FiCheckCircle

className="
text-green-400
mb-2
"

/>


<p

className="
text-xs
text-gray-400
"

>

Safe

</p>


<p

className="
text-xl
text-white
"

>

{data.safeUsers}

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


<FiAlertTriangle

className="
text-yellow-400
mb-2
"

/>


<p

className="
text-xs
text-gray-400
"

>

Warnings

</p>


<p

className="
text-xl
text-white
"

>

{data.warningUsers}

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


<FiShield

className="
text-red-400
mb-2
"

/>


<p

className="
text-xs
text-gray-400
"

>

Emergency

</p>


<p

className="
text-xl
text-white
"

>

{data.alerts}

</p>


</div>



</div>



</motion.div>

);

}