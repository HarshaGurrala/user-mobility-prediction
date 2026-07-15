import {
ResponsiveContainer,
LineChart,
Line,
XAxis,
YAxis,
Tooltip,
CartesianGrid
} from "recharts";

const data=[

{day:"Mon",km:12},
{day:"Tue",km:18},
{day:"Wed",km:25},
{day:"Thu",km:20},
{day:"Fri",km:30},
{day:"Sat",km:15},
{day:"Sun",km:10}

];

export default function WeeklyMovementChart(){

return(

<div className="bg-slate-900 rounded-3xl border border-white/10 p-6">

<h2 className="text-white text-2xl font-bold mb-6">

Weekly Movement

</h2>

<div style={{height:320}}>

<ResponsiveContainer>

<LineChart data={data}>

<CartesianGrid stroke="#334155"/>

<XAxis dataKey="day"/>

<YAxis/>

<Tooltip/>

<Line
type="monotone"
dataKey="km"
stroke="#06b6d4"
strokeWidth={4}
/>

</LineChart>

</ResponsiveContainer>

</div>

</div>

)

}