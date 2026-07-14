const stats = [

{
title:"Latitude",
value:"17.3850"
},

{
title:"Longitude",
value:"78.4867"
},

{
title:"ETA",
value:"12 min"
},

{
title:"Risk Score",
value:"Low"
}

];

export default function TrackingStats(){

return(

<div className="grid grid-cols-2 xl:grid-cols-4 gap-6 mt-6">

{

stats.map((item,index)=>(

<div
key={index}
className="bg-slate-900 border border-white/10 rounded-2xl p-6"
>

<h3 className="text-slate-400">

{item.title}

</h3>

<h1 className="text-white text-3xl font-bold mt-3">

{item.value}

</h1>

</div>

))

}

</div>

)

}