const places=[

"Home",

"Office",

"College",

"Shopping Mall",

"Hospital"

];

export default function VisitedPlaces(){

return(

<div className="bg-slate-900 rounded-3xl border border-white/10 p-6">

<h2 className="text-white text-2xl font-bold mb-6">

Frequently Visited

</h2>

{

places.map((place,index)=>(

<div
key={index}
className="bg-slate-800 rounded-xl p-4 mb-4 text-white"
>

{place}

</div>

))

}

</div>

)

}