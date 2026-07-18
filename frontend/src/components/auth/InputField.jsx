import { motion } from "framer-motion";


export default function InputField({

    icon: Icon,
    label,
    name,
    type="text",
    placeholder,
    value,
    onChange

}) {


return (

<div className="space-y-2">


<label className="
text-sm
text-gray-300
">

{label}

</label>


<div className="
relative
">


<Icon

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

type={type}

placeholder={placeholder}

value={value}

onChange={onChange}


className="
w-full
pl-12
pr-4
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


</div>


</div>

);

}