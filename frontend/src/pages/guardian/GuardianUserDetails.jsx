import { motion } from "framer-motion";
import { useParams, useNavigate } from "react-router-dom";

import {
  FiArrowLeft,
  FiMapPin,
  FiShield,
  FiActivity,
  FiBell
} from "react-icons/fi";
import { useEffect, useState } from "react";
import { getGuardianUserDetails } from "../../services/guardianApi";

import GuardianUserLiveMap from "../../components/guardian/user-details/GuardianUserLiveMap";
import GuardianUserMovementChart from "../../components/guardian/user-details/GuardianUserMovementChart";
import GuardianUserSafeZones from "../../components/guardian/user-details/GuardianUserSafeZones";
import GuardianUserPredictionCard from "../../components/guardian/user-details/GuardianUserPredictionCard";
import GuardianUserAlertTimeline from "../../components/guardian/user-details/GuardianUserAlertTimeline";

import { getConnectedUsers } from "../../services/guardianApi";

export default function GuardianUserDetails(){


const { userId } = useParams();

const navigate = useNavigate();

const [connectedUsers,setConnectedUsers] = useState([]);

const [selectedUser,setSelectedUser] = useState({

name:"",
location:"",
status:"",
prediction:"",
alerts:0

});


useEffect(()=>{


const loadUser = async()=>{
    const users = await getConnectedUsers();

setConnectedUsers(users);


try{


const data = await getGuardianUserDetails(userId);

console.log("GUARDIAN USER DETAILS:", data);


setSelectedUser({

name:
data.user_name || "Unknown User",


location:
data.location?.place || "Unknown Location",


status:
data.is_online
?
"ONLINE"
:
"OFFLINE",


prediction:
data.prediction || null,


alerts:
data.alerts || 0,


latitude:
data.location?.latitude,


longitude:
data.location?.longitude,


history:
data.history || [],


safe_zones:
data.safe_zones || []

});


}
catch(error){

console.log(
"User Details API Error:",
error
);

}


};


loadUser();


},[userId]);



// const selectedUser =
// users[Number(userId)] || users[1];




return (

<div

className="
min-h-screen
bg-[#050505]
text-white
p-6
pb-10
"

>



<button

onClick={()=>navigate(-1)}

className="
flex
items-center
gap-2
text-gray-300
mb-6
"

>

<FiArrowLeft/>

Back

</button>







{/* User Header */}


<motion.div

initial={{
opacity:0,
y:20
}}

animate={{
opacity:1,
y:0
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


<h1

className="
text-3xl
font-semibold
"

>

{selectedUser.name}

</h1>


<p

className="
text-gray-400
mt-1
"

>

Guardian monitoring dashboard

</p>




<div

className="
grid
grid-cols-2
gap-4
mt-6
"

>



<div

className="
rounded-2xl
bg-black/30
border
border-white/10
p-5
"

>


<FiMapPin

className="
text-blue-400
"

/>


<p

className="
text-gray-400
text-sm
mt-3
"

>

Current Location

</p>


<p>

{selectedUser.location}

</p>


</div>






<div

className="
rounded-2xl
bg-black/30
border
border-white/10
p-5
"

>


<FiShield

className="
text-green-400
"

/>


<p

className="
text-gray-400
text-sm
mt-3
"

>

Safety Status

</p>


<p>

{selectedUser.status}

</p>


</div>



</div>



</motion.div>







{/* Live Map */}




<div className="mt-6">

    {console.log(selectedUser)}

<GuardianUserLiveMap

user={selectedUser}

/>

</div>







{/* Analytics */}

<div className="mt-6">

<GuardianUserMovementChart

users={connectedUsers}

/>

</div>







{/* Safe Zone + AI Prediction */}

<div

className="
grid
grid-cols-1
lg:grid-cols-2
gap-6
mt-6
"

>


<GuardianUserSafeZones

user={selectedUser}

/>


<GuardianUserPredictionCard

user={selectedUser}

/>


</div>








{/* Notifications */}

<div className="mt-6">

<GuardianUserAlertTimeline

user={selectedUser}

/>

</div>





</div>

);

}