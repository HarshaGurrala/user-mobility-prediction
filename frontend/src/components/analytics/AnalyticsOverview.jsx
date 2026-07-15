const cards = [

{
title:"Total Trips",
value:"145"
},

{
title:"Distance",
value:"1,245 km"
},

{
title:"Predictions",
value:"520"
},

{
title:"Accuracy",
value:"98.4%"
}

];

export default function AnalyticsOverview(){

return(

<div className="grid md:grid-cols-2 xl:grid-cols-4 gap-6">

{

cards.map((card,index)=>(

<div
key={index}
className="bg-slate-900 border border-white/10 rounded-3xl p-6 hover:border-cyan-400 transition"
>

<h3 className="text-slate-400">

{card.title}

</h3>

<h1 className="text-white text-4xl font-bold mt-4">

{card.value}

</h1>

</div>

))

}

</div>

)

}