import { motion } from "framer-motion";

import {
  FiUsers,
  FiShield,
  FiAlertTriangle,
  FiCpu
} from "react-icons/fi";

import { useEffect, useState } from "react";

import { getGuardianStats } from "../../services/guardianApi";



export default function GuardianTopStats(){

const [stats, setStats] = useState({
    linked_users: 0,
    safety_status: "Loading...",
    total_alerts: 0,
    guardian_status: "Loading..."
});

useEffect(() => {

    const loadStats = async () => {

        try {

            const data = await getGuardianStats();

            console.log("Guardian Stats:", data);

            setStats(data);   // <-- This line is required

        } catch (error) {

            console.log(error);

        }

    };

    loadStats();

    const interval = setInterval(loadStats, 10000);

    return () => clearInterval(interval);

}, []);






const cards = [


{
title:"Linked Users",

value:stats.linked_users,

icon:FiUsers,

color:"blue"

},


{
title:"Safety Status",

value:stats.safety_status,

icon:FiShield,

color:"green"

},


{
title:"Alerts",

value:stats.total_alerts,

icon:FiAlertTriangle,

color:"yellow"

},


{
title:"Guardian Status",

value:stats.guardian_status,

icon:FiCpu,

color:"purple"

}



];







return (


<motion.div


initial={{
opacity:0,
y:-20
}}


animate={{
opacity:1,
y:0
}}


transition={{
duration:0.5
}}



className="
grid
grid-cols-2
lg:grid-cols-4
gap-5
mx-6
mt-6
"

>



{

cards.map((item,index)=>{


const Icon = item.icon;



return (


<motion.div


key={index}


whileHover={{
y:-5
}}



className="
rounded-3xl
border
border-white/10
bg-white/5
backdrop-blur-2xl
p-5
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


className={`

p-3

rounded-2xl


${
item.color==="blue"
?
"bg-blue-500/20"
:
item.color==="green"
?
"bg-green-500/20"
:
item.color==="yellow"
?
"bg-yellow-500/20"
:
"bg-purple-500/20"

}

`}

>


<Icon


className={`

text-xl


${
item.color==="blue"
?
"text-blue-400"
:
item.color==="green"
?
"text-green-400"
:
item.color==="yellow"
?
"text-yellow-400"
:
"text-purple-400"

}

`}

/>


</div>


</div>







<p

className="
text-xs
text-gray-400
mt-5
"

>

{item.title}

</p>





<h2

className="
text-3xl
font-semibold
text-white
mt-1
"

>

{item.value}

</h2>





</motion.div>



)


})


}



</motion.div>


);



}