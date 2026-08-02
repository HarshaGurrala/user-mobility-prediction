import { motion } from "framer-motion";

import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
} from "recharts";

import { useEffect, useState } from "react";
import { getGuardianMovement } from "../../services/guardianApi";

import {
  FiTrendingUp,
} from "react-icons/fi";

export default function GuardianFamilyChart() {


const [data,setData] = useState([]);

const [weeklyData,setWeeklyData] = useState([]);

const [monthlyData,setMonthlyData] = useState([]);

const [yearlyData,setYearlyData] = useState([]);

const [users,setUsers] = useState([]);

const [filter,setFilter] = useState("Daily");



const colors = [

"#60a5fa",
"#4ade80",
"#c084fc",
"#fb923c",
"#ec4899",
"#eab308"

];



useEffect(()=>{


const loadMovement = async()=>{


try{


const response = await getGuardianMovement();


const movementUsers = response.users || response;


setUsers(
    movementUsers
);



// DAILY

const dailyChart=[];


movementUsers.forEach((user)=>{


user.movement?.forEach((item,index)=>{


if(!dailyChart[index]){

dailyChart[index]={
time:item.day
};

}


dailyChart[index][user.user_name]=
item.distance;



});


});


setData(dailyChart);





// WEEKLY
const weeklyChart=[];


response.forEach((user)=>{

    user.movement?.forEach((item,index)=>{


        if(!weeklyChart[index]){

            weeklyChart[index]={
                time:`Week ${index+1}`
            };

        }


        weeklyChart[index][user.user_name] =
            item.distance;


    });


});


setWeeklyData(weeklyChart);










// MONTHLY

const months=[
"Jan",
"Feb",
"Mar",
"Apr",
"May",
"Jun",
"Jul",
"Aug",
"Sep",
"Oct",
"Nov",
"Dec"
];


const monthlyChart=[];

movementUsers.forEach((user)=>{


user.movement?.forEach((item,index)=>{


if(!monthlyChart[index]){

monthlyChart[index]={
    time:months[index]
};

}


monthlyChart[index][user.user_name] =
Number(item.distance)*4;


});


});


setMonthlyData(monthlyChart);







// YEARLY

const years=[
2025,
2026,
2027
];


const yearlyChart=[];


years.forEach((year,index)=>{


const chartItem={
    time:String(year)
};


movementUsers.forEach((user)=>{


const total =
user.movement.reduce(
(sum,item)=>sum+Number(item.distance),
0
);


chartItem[user.user_name] =
Number(total.toFixed(2));


});


yearlyChart.push(chartItem);


});





setYearlyData(yearlyChart);



}

catch(error){

console.log(
"Movement API Error:",
error
);

}


};


loadMovement();


},[]);



const chartData =
    filter === "Daily"
        ? data
        : filter === "Weekly"
        ? weeklyData
        : filter === "Monthly"
        ? monthlyData
        : yearlyData;





// const chartData = data.map(item=>{


// if(filter==="Daily"){

//     return item;

// }


// if(filter==="Weekly"){

//     return item;

// }


//     if(filter==="Monthly"){

//         const updated={...item};


//         Object.keys(updated).forEach(key=>{

//             if(key!=="time"){

//                 updated[key] =
//                 Number(updated[key])*4;

//             }

//         });


//         return updated;

//     }



//     if(filter==="Yearly"){

//         const updated={...item};


//         Object.keys(updated).forEach(key=>{

//             if(key!=="time"){

//                 updated[key] =
//                 Number(updated[key])*52;

//             }

//         });


//         return updated;

//     }


//     return item;


// });





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
duration:0.6
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
text-lg
"

>

Family Movement Analytics

</h2>


<p

className="
text-xs
text-gray-400
"

>

Daily movement comparison

</p>


</div>


</div>








<div

className="
flex
gap-5
mb-5
text-xs
flex-wrap
"

>


{
users.map((user,index)=>(

<div

key={user.user_id}

className="text-gray-300"

>

<span

style={{

color:
colors[index % colors.length]

}}

>

●

</span>


{" "}

{user.user_name}


</div>

))

}



</div>









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

dataKey="time"

stroke="#777"

/>



<YAxis

stroke="#777"

/>



<Tooltip

content={({active,payload,label})=>{


if(!active || !payload){

return null;

}


return (

<div

className="
rounded-xl
bg-black
border
border-white/20
p-3
text-xs
"

>


<p className="text-gray-400 mb-2">

{label}

</p>



{

payload.map((item,index)=>(

<div

key={index}

className="
flex
items-center
gap-2
"

>

<span

style={{
color:item.color
}}

>

●

</span>


<span className="text-white">

{item.name}

:

{" "}

{item.value} km

</span>


</div>


))

}


</div>

);


}}


/>





{

users.map((user,index)=>(

<Line
 key={user.user_id}
 dataKey={user.user_name}
 stroke={colors[index % colors.length]}
 strokeWidth={3}
 dot={{r:4}}
 />


))

}



</LineChart>


</ResponsiveContainer>


</div>








<div

className="
mt-5
flex
gap-3
"

>


{

["Daily","Weekly","Monthly","Yearly"].map(item=>(

<button

key={item}

onClick={()=>setFilter(item)}

className={`
px-4
py-2
rounded-xl
border
text-xs

${
filter===item
?
"bg-violet-500 text-white"
:
"bg-white/5 text-gray-300"
}

`}

>

{item}

</button>


))

}



</div>





</motion.div>


);

}