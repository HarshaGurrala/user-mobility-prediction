import { useState } from "react";
import { FiLock, FiEye, FiEyeOff } from "react-icons/fi";


export default function PasswordInput({

    name,
    value,
    onChange

}) {


const [show,setShow]=useState(false);



return (

<div className="space-y-2">


<label className="
text-sm
text-gray-300
">

Password

</label>



<div className="
relative
">


<FiLock

className="
absolute
left-4
top-1/2
-translate-y-1/2
text-gray-400
"

/>



<input

name={name}

type={show ? "text" : "password"}

value={value}

onChange={onChange}

placeholder="Enter your password"


className="
w-full
pl-12
pr-12
py-3
rounded-xl
bg-white/5
border
border-white/10
text-white
placeholder-gray-500
outline-none
focus:border-blue-500
focus:ring-2
focus:ring-blue-500/20
transition
"

/>



<button

type="button"

onClick={()=>setShow(!show)}

className="
absolute
right-4
top-1/2
-translate-y-1/2
text-gray-400
hover:text-white
"

>

{
show ?

<FiEyeOff/>

:

<FiEye/>

}

</button>


</div>


</div>

);

}