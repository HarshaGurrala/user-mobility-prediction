// export default function TechStack() {
//   return (
//     <section className="py-20 text-center text-white">
//       <h2 className="text-4xl font-bold">Technology Stack</h2>
//     </section>
//   );
// }
import {
  Database,
  BrainCircuit,
  MapPinned,
  Server,
  Globe,
  Shield
} from "lucide-react";

const tech = [

{
title:"React",
icon:Globe
},

{
title:"FastAPI",
icon:Server
},

{
title:"MySQL",
icon:Database
},

{
title:"Machine Learning",
icon:BrainCircuit
},

{
title:"Leaflet Maps",
icon:MapPinned
},

{
title:"JWT Security",
icon:Shield
}

];

export default function TechStack(){

return(

<section
id="tech"
className="py-28 bg-slate-900 px-8"
>

<div className="max-w-7xl mx-auto">

<h2 className="text-5xl font-bold text-center">

Technology Stack

</h2>

<div className="grid md:grid-cols-3 lg:grid-cols-6 gap-8 mt-16">

{tech.map((item,index)=>{

const Icon=item.icon;

return(

<div

key={index}

className="bg-white/5
rounded-3xl
border
border-white/10
p-8
text-center
hover:scale-105
transition"

>

<Icon
size={42}
className="mx-auto text-cyan-400"
/>

<h3 className="mt-5 font-semibold">

{item.title}

</h3>

</div>

)

})}

</div>

</div>

</section>

)

}