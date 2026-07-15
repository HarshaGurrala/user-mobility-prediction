// function Profile() {
//   return <h1>Profile</h1>;
// }

// export default Profile;
import { useEffect, useState } from "react";

import DashboardLayout from "../components/layout/DashboardLayout";

import { getCurrentUser } from "../services/userService";


export default function Profile(){


const [user,setUser]=useState(null);



useEffect(()=>{


    loadUser();


},[]);



const loadUser=async()=>{

    try{

        const data = await getCurrentUser();

        setUser(data);


    }
    catch(error){

        console.log(error);

    }

};



return(

<DashboardLayout>


<h1 className="text-5xl font-bold text-white">

Profile

</h1>



<div className="mt-10 bg-slate-900/60 p-8 rounded-3xl border border-white/10">


{
user ?

<>

<h2 className="text-3xl text-white font-bold">

{user.full_name}

</h2>


<p className="text-slate-400 mt-4">

Email: {user.email}

</p>


<p className="text-slate-400 mt-2">

Phone: {user.phone_number}

</p>


</>

:

<p className="text-white">

Loading...

</p>

}


</div>


</DashboardLayout>

)


}