const stats = [

{
title:"Safe Zones",
value:"26"
},

{
title:"Hospitals",
value:"12"
},

{
title:"Police",
value:"8"
},

{
title:"Risk",
value:"Low"
}

];

export default function SafeStats(){

return(

<div className="grid md:grid-cols-2 xl:grid-cols-4 gap-6">

{

stats.map((item,index)=>(

<div
key={index}
className="bg-slate-900 rounded-3xl border border-white/10 p-6"
>

<h3 className="text-slate-400">

{item.title}

</h3>

<h1 className="text-white text-4xl font-bold mt-4">

{item.value}

</h1>

</div>

))

}

</div>

)

}