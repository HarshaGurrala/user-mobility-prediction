import GlassCard from "./GlassCard";

export default function StatCard({

    title,

    value

}){

    return(

        <GlassCard>

            <h3 className="text-gray-400">

                {title}

            </h3>

            <h1 className="text-4xl font-bold mt-3">

                {value}

            </h1>

        </GlassCard>

    )

}