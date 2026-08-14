import { motion } from "framer-motion";

import {
    FiAlertTriangle,
    FiShield,
    FiClock,
    FiMapPin,
} from "react-icons/fi";

import { useEffect, useState } from "react";

import { getGuardianAlerts } from "../../services/guardianApi";


export default function GuardianAlertFeed() {

    const [alerts, setAlerts] = useState([]);


    useEffect(() => {

        const loadAlerts = async () => {

            try {

                const guardianId =
                    localStorage.getItem("userId");

                const data =
                    await getGuardianAlerts(guardianId);

                setAlerts(data);

            }
            catch (error) {

                console.log(
                    "Alert API Error:",
                    error
                );

            }

        };


        loadAlerts();

    }, []);


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
                shadow-[0_0_40px_rgba(255,80,80,.12)]
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
                        bg-red-500/20
                    "
                >

                    <FiAlertTriangle
                        className="
                            text-red-400
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
                        Guardian Notifications
                    </h2>


                    <p
                        className="
                            text-xs
                            text-gray-400
                        "
                    >
                        All user safety events
                    </p>

                </div>

            </div>


            {/* Notifications */}

<div
    className="
        space-y-3
        max-h-[500px]
        overflow-y-auto
        pr-2
        scrollbar-thin
        scrollbar-thumb-white/20
        scrollbar-track-transparent
    "
>

                {alerts.length === 0 ? (

                    <div
                        className="
                            text-center
                            py-8
                            text-gray-400
                            text-sm
                        "
                    >
                        No notifications available
                    </div>

                ) : (

                    alerts.map((alert) => (

                        <motion.div

                            key={alert.id}

                            whileHover={{
                                scale: 1.02
                            }}

                            className={`
                                rounded-2xl
                                border
                                p-4
                                ${
                                    alert.status === "unread"
                                        ? "bg-red-500/10 border-red-500/30"
                                        : "bg-black/30 border-white/10"
                                }
                            `}

                        >

                            {/* Main Row */}

                            <div
                                className="
                                    flex
                                    items-center
                                    justify-between
                                "
                            >

                                <div
                                    className="
                                        flex
                                        items-start
                                        gap-3
                                    "
                                >

                                    {/* Icon */}

                                    <div>

                                        {alert.type === "SAFE" ? (

                                            <FiShield
                                                className="
                                                    text-green-400
                                                    mt-1
                                                "
                                            />

                                        ) : (

                                            <FiAlertTriangle
                                                className="
                                                    text-yellow-400
                                                    mt-1
                                                "
                                            />

                                        )}

                                    </div>


                                    {/* Content */}

                                    <div>

                                        <p
                                            className="
                                                text-white
                                                text-sm
                                                font-medium
                                            "
                                        >
                                            {alert.user}
                                        </p>


                                        <p
                                            className="
                                                text-gray-300
                                                text-sm
                                                mt-1
                                            "
                                        >
                                            {alert.message}
                                        </p>


                                        {/* Location */}

                                        <div
                                            className="
                                                flex
                                                items-center
                                                gap-1
                                                mt-2
                                                text-xs
                                                text-gray-400
                                            "
                                        >

                                            <FiMapPin />

                                            <span>
                                                {alert.location ||
                                                    "Location unavailable"}
                                            </span>

                                        </div>

                                    </div>

                                </div>


                                {/* Status */}

                                <span
                                    className={`
                                        text-xs
                                        px-3
                                        py-1
                                        rounded-full
                                        ${
                                            alert.status === "unread"
                                                ? "bg-red-500/20 text-red-300"
                                                : "bg-gray-500/20 text-gray-400"
                                        }
                                    `}
                                >

                                    {alert.status === "unread"
                                        ? "UNREAD"
                                        : "READ"}

                                </span>

                            </div>


                            {/* Time */}

                            <div
                                className="
                                    flex
                                    items-center
                                    gap-2
                                    mt-3
                                    text-xs
                                    text-gray-400
                                "
                            >

                                <FiClock />

                              {alert.time
    ? new Date(
        alert.time + "Z"
    ).toLocaleString("en-IN", {
        timeZone: "Asia/Kolkata",
        day: "2-digit",
        month: "short",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit",
        second: "2-digit",
        hour12: true,
    })
    : "Time unavailable"}

                            </div>

                        </motion.div>

                    ))

                )}

            </div>

        </motion.div>

    );

}