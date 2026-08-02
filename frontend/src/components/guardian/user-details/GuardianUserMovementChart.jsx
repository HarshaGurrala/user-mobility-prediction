import { motion } from "framer-motion";

import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  Legend
} from "recharts";

import {
  FiTrendingUp
} from "react-icons/fi";


export default function GuardianUserMovementChart({

users = []

}) {



const colors = [

"#38bdf8",
"#22c55e",
"#a78bfa",
"#f97316",
"#ec4899",
"#eab308"

];



// Convert users movement data into chart format

const chartData = [];



users.forEach(user => {


    user.movement?.forEach(item => {


        let dayData =
        chartData.find(
            data => data.day === item.day
        );


        if(!dayData){

            dayData = {
                day:item.day
            };

            chartData.push(dayData);

        }



        dayData[user.user_name] =
        item.distance;



    });


});





return (

<motion.div

initial={{
opacity:0,
y:30
}}

animate={{
opacity:1,
y:0
}}

transition={{
duration:0.5
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



{/* Header */}

<div

className="
flex
items-center
gap-3
mb-6
"

>


<div

className="
p-3
rounded-2xl
bg-violet-500/20
"

>

<FiTrendingUp

className="
text-violet-400
text-xl
"

/>


</div>



<div>


<h2

className="
text-white
font-semibold
"

>

Movement Analytics

</h2>


<p

className="
text-xs
text-gray-400
"

>

All connected users travel pattern

</p>


</div>


</div>






{/* Graph */}

<div

className="
h-[320px]
"

>


<ResponsiveContainer

width="100%"

height="100%"

>


<LineChart

data={chartData}

>


<XAxis

dataKey="day"

stroke="#777"

/>



<YAxis

stroke="#777"

/>




<Tooltip

contentStyle={{

background:"#111",

border:"1px solid rgba(255,255,255,.1)",

borderRadius:"16px"

}}

/>



<Legend />





{

users.map((user,index)=>(


<Line

key={user.user_id}

type="monotone"

dataKey={user.user_name}

stroke={
colors[index % colors.length]
}

strokeWidth={3}

dot={{
r:4
}}

/>


))

}



</LineChart>


</ResponsiveContainer>


</div>







{/* Stats */}

<div

className="
grid
grid-cols-3
gap-3
mt-5
"

>


<div

className="
rounded-2xl
bg-black/30
border
border-white/10
p-4
"

>


<p

className="
text-xs
text-gray-400
"

>

Total Users

</p>


<p

className="
text-white
text-lg
mt-2
"

>

{users.length}

</p>


</div>





<div

className="
rounded-2xl
bg-black/30
border
border-white/10
p-4
"

>


<p

className="
text-xs
text-gray-400
"

>

Tracking

</p>


<p

className="
text-white
text-lg
mt-2
"

>

Active

</p>


</div>






<div

className="
rounded-2xl
bg-black/30
border
border-white/10
p-4
"

>


<p

className="
text-xs
text-gray-400
"

>

Pattern

</p>


<p

className="
text-green-300
text-lg
mt-2
"

>

Normal

</p>


</div>



</div>





</motion.div>

);

}