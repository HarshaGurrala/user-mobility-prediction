import { motion } from "framer-motion";

import { useEffect, useState } from "react";

import {
  getGuardianMovement
} from "../../services/guardianApi";


import GuardianTopBar from "../../components/guardian/GuardianTopBar";
import GuardianLinkedUsers from "../../components/guardian/GuardianLinkedUsers";

import GuardianLiveMapCard from "../../components/guardian/GuardianLiveMapCard";
import GuardianFamilyChart from "../../components/guardian/GuardianFamilyChart";
import GuardianAlertFeed from "../../components/guardian/GuardianAlertFeed";
import GuardianAIReport from "../../components/guardian/GuardianAIReport";
import GuardianTopStats from "../../components/guardian/GuardianTopStats";
import GuardianRequestStatusCard from "../../components/guardian/GuardianRequestStatusCard";


export default function GuardianHome() {


const [movementUsers,setMovementUsers] = useState([]);





useEffect(()=>{


const loadMovement = async()=>{


try{


const guardianId =
localStorage.getItem("userId");



const data =
await getGuardianMovement(
    guardianId
);



setMovementUsers(data);



}
catch(error){


console.log(
"Guardian Movement Error:",
error
);


}



};



loadMovement();


},[]);







return (

<motion.div

initial={{
opacity:0,
y:20
}}

animate={{
opacity:1,
y:0
}}

transition={{
duration:0.6
}}

className="
min-h-screen
bg-[#050505]
text-white
pb-10
"

>


{/* Header */}

<div className="pt-6">

<GuardianTopBar />

</div>





{/* Top Four Cards */}

<div

className="
mt-8
"

>

<GuardianTopStats />

</div>







{/* Main Content */}

<div

className="
px-6
mt-8
space-y-6
"

>

{/* Linked Users */}

<GuardianLinkedUsers />







{/* Safety + Live Map */}

<div className="lg:col-span-2">
    <GuardianLiveMapCard />
</div>








{/* Full Horizontal Connection Status */}

<div

className="
w-full
mt-8
"

>

<GuardianRequestStatusCard />


</div>








{/* Family Movement Analytics */}

<GuardianFamilyChart />







{/* Notifications */}

<GuardianAlertFeed />







{/* AI Reports */}

<GuardianAIReport />





</div>



</motion.div>

);

}