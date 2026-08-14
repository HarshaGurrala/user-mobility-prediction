import { motion } from "framer-motion";

import {
  FiCpu,
  FiActivity,
  FiCheckCircle,
  FiTrendingUp,
} from "react-icons/fi";




import { useEffect, useState } from "react";
import { getGuardianAIReport } from "../../services/guardianApi";
export default function GuardianAIReport() {

  

const [report,setReport] = useState({

score:0,

safeUsers:0,

warnings:0,

summary:"Loading AI report..."

});


useEffect(() => {

    const loadReport = async () => {

        try {

            const guardianId =
                localStorage.getItem("userId");

            console.log(
                "AI Report Guardian ID:",
                guardianId
            );

            const data =
                await getGuardianAIReport(
                    guardianId
                );

            console.log(
                "AI Report Backend Data:",
                data
            );

            setReport(data);

        } catch (error) {

            console.log(
                "AI Report API Error:",
                error
            );

        }

    };

    loadReport();

}, []);


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
relative
overflow-hidden
rounded-3xl
border
border-white/10
bg-white/5
backdrop-blur-2xl
p-6
shadow-[0_0_40px_rgba(139,92,246,.15)]
"

>


<div

className="
absolute
right-[-60px]
top-[-60px]
h-48
w-48
rounded-full
bg-violet-500/20
blur-3xl
"

/>





{/* Header */}

<div

className="
flex
items-center
gap-3
relative
"

>


<div

className="
p-3
rounded-2xl
bg-violet-500/20
"

>


<FiCpu

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

AI Family Insights

</h2>


<p

className="
text-xs
text-gray-400
"

>

Smart safety analysis report

</p>


</div>



</div>







{/* Score */}

<div

className="
mt-6
rounded-2xl
bg-black/30
border
border-white/10
p-5
flex
items-center
justify-between
"

>


<div>


<p

className="
text-xs
text-gray-400
"

>

Family Safety Score

</p>


<h3

className="
text-3xl
font-semibold
text-green-300
mt-2
"

>

{report.score}%

</h3>


</div>



<FiCheckCircle

className="
text-green-400
text-5xl
"

/>


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
bg-white/5
border
border-white/10
p-4
"

>


<FiActivity

className="
text-blue-400
mb-2
"

/>


<p

className="
text-xs
text-gray-400
"

>

Safe Users

</p>


<p

className="
text-white
text-xl
"

>

{report.safeUsers}

</p>


</div>





<div

className="
rounded-2xl
bg-white/5
border
border-white/10
p-4
"

>


<FiTrendingUp

className="
text-yellow-400
mb-2
"

/>


<p

className="
text-xs
text-gray-400
"

>

Warnings

</p>


<p

className="
text-white
text-xl
"

>

{report.warnings}

</p>


</div>





<div

className="
rounded-2xl
bg-white/5
border
border-white/10
p-4
"

>


<FiCpu

className="
text-purple-400
mb-2
"

/>


<p

className="
text-xs
text-gray-400
"

>

AI Status

</p>


<p

className="
text-white
text-sm
"

>

Active

</p>


</div>



</div>







{/* AI Message */}

<div

className="
mt-5
rounded-2xl
bg-violet-500/10
border
border-violet-400/20
p-4
"

>


<p

className="
text-xs
text-violet-300
"

>

AI OBSERVATION

</p>


<p

className="
text-sm
text-white
mt-2
"

>

{report.summary}

</p>



</div>





</motion.div>

);

}