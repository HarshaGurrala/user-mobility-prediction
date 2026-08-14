import { motion } from "framer-motion";

import {
    LineChart,
    Line,
    XAxis,
    YAxis,
    Tooltip,
    ResponsiveContainer,
    CartesianGrid
} from "recharts";

import { useEffect, useState } from "react";

import {
    FiTrendingUp
} from "react-icons/fi";


export default function SingleUserMovementChart({
    movement,
    user
}) {

    const [data, setData] = useState([]);

    const [filter, setFilter] =
        useState("Daily");


    const userName =
        user?.user_name ||
        user?.full_name ||
        user?.email ||
        "User";


    // =====================================================
    // LOAD BACKEND DATA
    // =====================================================

    useEffect(() => {

        console.log(
            "========== SINGLE USER MOVEMENT =========="
        );

        console.log(
            movement
        );

        console.log(
            "=========================================="
        );


        if (!movement) {

            setData([]);

            return;
        }


        const movementObject =
            movement.movement ||
            movement;


        let source = [];


        // =================================================
        // DAILY
        // =================================================

        if (filter === "Daily") {

            source =
                movementObject.daily || [];

        }


        // =================================================
        // WEEKLY
        // =================================================

        else if (filter === "Weekly") {

            source =
                movementObject.weekly || [];

        }


        // =================================================
        // MONTHLY
        // =================================================

        else if (filter === "Monthly") {

            source =
                movementObject.monthly || [];

        }


        // =================================================
        // YEARLY
        // =================================================

        else {

            source =
                movementObject.yearly || [];

        }


        console.log(
            `${filter} BACKEND SOURCE:`,
            source
        );


        // =================================================
        // CONVERT TO RECHARTS DATA
        // =================================================

     const chartData = source.map(item => ({
    time: item.time,
    [userName]: Number(item[userName]) || 0
}));


        console.log(
            `${filter} FINAL CHART DATA:`,
            chartData
        );


        setData(chartData);


    }, [
        movement,
        filter,
        userName
    ]);


    // =====================================================
    // RENDER
    // =====================================================

    return (

        <motion.div

            initial={{
                opacity: 0,
                y: 30
            }}

            animate={{
                opacity: 1,
                y: 0
            }}

            transition={{
                duration: 0.6
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


            {/* HEADER */}

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

                    <div
                        className="
                            text-white
                            font-semibold
                            text-lg
                        "
                    >

                        {userName}
                        {" "}
                        Movement Analytics

                    </div>


                    <div
                        className="
                            text-xs
                            text-gray-400
                        "
                    >

                        {filter}
                        {" "}
                        movement analysis

                    </div>

                </div>

            </div>


            {/* LEGEND */}

            <div
                className="
                    flex
                    gap-5
                    mb-5
                    text-xs
                "
            >

                <div
                    className="
                        text-gray-300
                    "
                >

                    <span
                        className="
                            text-violet-400
                        "
                    >
                        ●
                    </span>

                    {" "}

                    {userName}

                </div>

            </div>


            {/* CHART */}

            <div
                className="
                    h-[420px]
                    w-full
                "
            >

                <ResponsiveContainer
                    width="100%"
                    height="100%"
                >

                    <LineChart
                        data={data}
                        margin={{
                            top: 10,
                            right: 20,
                            left: 10,
                            bottom:
                                filter === "Monthly"
                                    ? 30
                                    : 10
                        }}
                    >


                        {/* GRID */}

                        <CartesianGrid
                            strokeDasharray="3 3"
                            stroke="rgba(255,255,255,0.08)"
                        />


                        {/* X AXIS */}

                        <XAxis

                            dataKey="time"

                            type="category"

                            allowDuplicatedCategory={false}

                            interval={0}

                            padding={{
                                left: 10,
                                right: 10
                            }}

                            tick={{
                                fill: "#aaa",
                                fontSize: 12
                            }}

                            tickLine={{
                                stroke: "#555"
                            }}

                            axisLine={{
                                stroke: "#555"
                            }}

                            angle={
                                filter === "Monthly"
                                    ? -45
                                    : 0
                            }

                            textAnchor={
                                filter === "Monthly"
                                    ? "end"
                                    : "middle"
                            }

                        />


                        {/* Y AXIS */}

                        <YAxis

                            type="number"

                            allowDecimals={true}

                            domain={[
                                0,
                                (dataMax) =>
                                    dataMax === 0
                                        ? 5
                                        : Math.ceil(
                                            dataMax + 5
                                        )
                            ]}

                            tick={{
                                fill: "#aaa",
                                fontSize: 12
                            }}

                            tickLine={{
                                stroke: "#555"
                            }}

                            axisLine={{
                                stroke: "#555"
                            }}

                            label={{
                                value:
                                    "Distance (KM)",
                                angle: -90,
                                position:
                                    "insideLeft",
                                fill: "#aaa"
                            }}

                        />


                        {/* TOOLTIP */}

                        <Tooltip

                            content={({
                                active,
                                payload,
                                label
                            }) => {

                                if (
                                    !active ||
                                    !payload ||
                                    payload.length === 0
                                ) {

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

                                        <div
                                            className="
                                                text-white
                                                mb-2
                                                font-medium
                                            "
                                        >

                                            {label}

                                        </div>


                                        {

                                            payload.map(
                                                (
                                                    item,
                                                    index
                                                ) => (

                                                    <div
                                                        key={
                                                            index
                                                        }
                                                        className="
                                                            flex
                                                            items-center
                                                            gap-2
                                                            text-gray-300
                                                        "
                                                    >

                                                        <span
                                                            style={{
                                                                color:
                                                                    item.color
                                                            }}
                                                        >
                                                            ●
                                                        </span>


                                                        {userName}

                                                        :

                                                        {" "}

                                                        {
                                                            Number(
                                                                item.value
                                                            ).toFixed(2)
                                                        }

                                                        {" "}
                                                        km

                                                    </div>

                                                )
                                            )

                                        }

                                    </div>

                                );

                            }}

                        />


                        {/* REAL USER LINE */}

                        <Line

                            type="monotone"

                            dataKey={userName}

                            name={userName}

                            stroke="#c084fc"

                            strokeWidth={3}

                            dot={{
                                r: 4
                            }}

                            activeDot={{
                                r: 6
                            }}

                            connectNulls={true}

                            isAnimationActive={true}

                        />

                    </LineChart>

                </ResponsiveContainer>

            </div>


            {/* FILTERS */}

            <div
                className="
                    mt-5
                    flex
                    gap-3
                    flex-wrap
                "
            >

                {
                    [
                        "Daily",
                        "Weekly",
                        "Monthly",
                        "Yearly"
                    ].map(item => (

                        <button

                            key={item}

                            onClick={() =>
                                setFilter(item)
                            }

                            className={`
                                px-4
                                py-2
                                rounded-xl
                                border
                                text-xs

                                ${
                                    filter === item
                                        ? "bg-violet-500 text-white"
                                        : "bg-white/5 text-gray-300"
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