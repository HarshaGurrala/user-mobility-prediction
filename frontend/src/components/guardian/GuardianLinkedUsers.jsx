
import { motion } from "framer-motion";

import {
  FiUser,
  FiChevronRight,
} from "react-icons/fi";

import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

import { getGuardianUsers } from "../../services/guardianApi";


export default function GuardianLinkedUsers() {


    const navigate = useNavigate();


    const [users, setUsers] = useState([]);


    useEffect(() => {

        const loadUsers = async () => {

            try {

                const data =
                    await getGuardianUsers();


                setUsers(
                    data.map((user) => ({

                        id: user.id,

                        full_name:
                            user.full_name,

                        is_online:
                            user.is_online,

                        profile_picture:
                            user.profile_picture,

                        status:
                            user.is_online
                                ? "Online"
                                : "Offline"

                    }))
                );


            }
            catch (error) {

                console.log(
                    "Guardian users error:",
                    error
                );

            }

        };


        loadUsers();


    }, []);


    const openUserDetails = (user) => {

        navigate(
            `/dashboard/${user.id}`
        );

    };


    // ==================================================
    // PROFILE IMAGE URL
    // ==================================================

    const getProfileImage = (user) => {

        if (!user.profile_picture) {

            return null;

        }


        // Already a complete URL
        if (
            user.profile_picture.startsWith(
                "http://"
            ) ||
            user.profile_picture.startsWith(
                "https://"
            )
        ) {

            return user.profile_picture;

        }


        // Backend relative upload path
        return `https://user-mobility-prediction.onrender.com${user.profile_picture}`;

    };


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
                duration: 0.5
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


            <div className="
            flex
            items-center
            justify-between
            mb-5
            ">


                <div>

                    <h2 className="
                    text-white
                    font-semibold
                    text-lg
                    ">

                        Linked Users

                    </h2>


                    <p className="
                    text-xs
                    text-gray-400
                    ">

                        Users connected with guardian

                    </p>

                </div>


                <p className="
                text-xs
                text-blue-300
                ">

                    {users.length} Users

                </p>


            </div>


            <div

                className="
                flex
                gap-4
                overflow-x-auto
                pb-2
                "

            >


                {
                    users.map((user) => {

                        const profileImage =
                            getProfileImage(user);


                        return (

                            <motion.button

                                key={user.id}

                                whileHover={{
                                    scale: 1.05
                                }}


                                onClick={() =>
                                    openUserDetails(user)
                                }


                                className="
                                min-w-[180px]
                                rounded-3xl
                                border
                                border-white/10
                                bg-black/30
                                p-5
                                text-left
                                cursor-pointer
                                "

                            >


                                <div className="
                                flex
                                items-center
                                justify-between
                                ">


                                    {/* PROFILE IMAGE */}

                                    <div

                                        className="
                                        h-12
                                        w-12
                                        rounded-2xl
                                        overflow-hidden
                                        bg-blue-500/20
                                        flex
                                        items-center
                                        justify-center
                                        "

                                    >

                                        {
                                            profileImage ? (

                                                <img
                                                    src={profileImage}
                                                    alt={user.full_name}
                                                    className="
                                                    h-full
                                                    w-full
                                                    object-cover
                                                    "
                                                />

                                            ) : (

                                                <FiUser
                                                    className="
                                                    text-white
                                                    text-xl
                                                    "
                                                />

                                            )
                                        }

                                    </div>


                                    <FiChevronRight
                                        className="
                                        text-gray-400
                                        "
                                    />

                                </div>


                                <h3

                                    className="
                                    text-white
                                    font-medium
                                    mt-4
                                    "

                                >

                                    {user.full_name}

                                </h3>


                                <div className="
                                flex
                                items-center
                                gap-2
                                mt-2
                                ">


                                    <div

                                        className={`
                                        h-2
                                        w-2
                                        rounded-full

                                        ${
                                            user.is_online
                                                ? "bg-green-400"
                                                : "bg-red-400"
                                        }

                                        `}

                                    />


                                    <p

                                        className="
                                        text-xs
                                        text-gray-400
                                        "

                                    >

                                        {user.status}

                                    </p>


                                </div>


                            </motion.button>

                        );

                    })

                }


            </div>


        </motion.div>

    );

}

