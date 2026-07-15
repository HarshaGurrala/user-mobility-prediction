import AuthBackground from "./AuthBackground";

export default function AuthLayout({

title,
subtitle,
children

}){

return(

<div className="relative min-h-screen bg-slate-950 overflow-hidden">

<AuthBackground/>

<div className="relative z-10 flex min-h-screen">

{/* LEFT */}

<div className="hidden lg:flex w-1/2 items-center justify-center px-16">

<div>

<h1 className="text-6xl font-black text-white leading-tight">

AI User

<br/>

Mobility

<br/>

Prediction

</h1>

<p className="mt-8 text-xl text-slate-300 leading-9">

Predict user movement with Machine Learning,
Live GPS Tracking and Intelligent Analytics.

</p>

<div className="mt-10 space-y-4 text-slate-300">

<p>✓ AI Prediction Engine</p>

<p>✓ Live GPS Tracking</p>

<p>✓ Emergency Alerts</p>

<p>✓ Safe Zone Detection</p>

<p>✓ Real-time Analytics</p>

</div>

</div>

</div>

{/* RIGHT */}

<div className="flex flex-1 items-center justify-center px-6">

<div className="w-full max-w-lg rounded-[35px]
bg-white/5
backdrop-blur-3xl
border border-white/10
shadow-2xl
p-10">

<h2 className="text-4xl font-bold text-white">

{title}

</h2>

<p className="text-slate-400 mt-3">

{subtitle}

</p>

<div className="mt-10">

{children}

</div>

</div>

</div>

</div>

</div>

)

}