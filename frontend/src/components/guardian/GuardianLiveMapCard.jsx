import { motion } from "framer-motion";

import {
  FiMap,
  FiNavigation,
  FiUsers,
} from "react-icons/fi";
import { useEffect, useState } from "react";
import { getGuardianMapUsers } from "../../services/guardianApi";

import { useNavigate } from "react-router-dom";
export default function GuardianLiveMapCard() {


const navigate = useNavigate();



const openFamilyMap = () => {

    navigate("/guardian/family-map");

};


const [users,setUsers] = useState([]);

useEffect(()=>{


const loadUsers = async()=>{


try{


const data =
await getGuardianMapUsers();


console.log(
"Live Map Users:",
data
);


setUsers(data);


}
catch(error){

console.log(
"Live map card error:",
error
);

}


};



loadUsers();


const interval =
setInterval(
loadUsers,
10000
);


return ()=>clearInterval(interval);



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
shadow-[0_0_40px_rgba(59,130,246,.15)]
"

>



<div

className="
absolute
right-[-50px]
top-[-50px]
h-44
w-44
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

<FiMap

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
text-lg
"

>

Live Family Tracking

</h2>


<p

className="
text-xs
text-gray-400
"

>

Monitor all users on one map

</p>


</div>


</div>







{/* Map Preview */}

<div

className="
mt-6
h-40
rounded-3xl
bg-black/30
border
border-white/10
flex
items-center
justify-center
relative
overflow-hidden
"

>


<div

className="
absolute
h-24
w-24
rounded-full
bg-blue-500/20
blur-2xl
"

/>



<FiNavigation

className="
text-blue-300
text-5xl
"

/>



</div>








{/* User Count */}

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


<FiUsers

className="
text-green-400
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
Live Connected Users
</p>


<p
className="
text-white
font-medium
"
>
{users.length} Connected Users
</p>


</div>



</div>








{/* Button */}

<button

onClick={openFamilyMap}

className="
mt-5
w-full
rounded-2xl
py-3
bg-blue-500/20
border
border-blue-400/20
text-blue-300
flex
items-center
justify-center
gap-2
hover:bg-blue-500/30
transition
"

>

<FiMap />

Open Full Screen Map

</button>





</motion.div>

);

}