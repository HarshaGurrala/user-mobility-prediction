import { motion } from "framer-motion";

import {
    FiShield,
    FiUser,
    FiX,
    FiCamera,
    FiLogOut,
} from "react-icons/fi";

import {
    useEffect,
    useRef,
    useState
} from "react";

import { useNavigate } from "react-router-dom";

import { getUserProfile } from "../../services/locationService";

import axios from "axios";

import { useAuth } from "../../context/AuthContext";


export default function GuardianTopBar() {

    const navigate = useNavigate();

    const { logout } = useAuth();

    const [guardian, setGuardian] = useState(null);

    const [showProfile, setShowProfile] = useState(false);

    const [uploading, setUploading] = useState(false);

    const fileInputRef = useRef(null);


    // ==================================================
    // LOAD GUARDIAN PROFILE
    // ==================================================

    useEffect(() => {

        const loadGuardian = async () => {

            try {

                const data = await getUserProfile();

                console.log(
                    "Guardian Profile:",
                    data
                );

                setGuardian(data);

            }
            catch (error) {

                console.log(
                    "Guardian profile error:",
                    error
                );

            }

        };


        loadGuardian();


        const interval = setInterval(() => {

            loadGuardian();

        }, 10000);


        return () =>
            clearInterval(interval);

    }, []);


    // ==================================================
    // ONLINE STATUS
    // ==================================================

    const isOnline =
        guardian?.is_online === true ||
        guardian?.is_online === 1 ||
        guardian?.is_online === "1" ||
        guardian?.is_online === "true" ||
        guardian?.is_online === "online";


    // ==================================================
    // PROFILE IMAGE
    // ==================================================

    const profileImage =
        guardian?.profile_picture
            ? guardian.profile_picture.startsWith("http")
                ? guardian.profile_picture
                : `https://user-mobility-prediction.onrender.com${guardian.profile_picture}`
            : null;


    // ==================================================
    // LOGOUT
    // ==================================================

   const handleLogout = () => {

    console.log("Guardian logout");

    setShowProfile(false);

    logout();

    navigate("/", {
        replace: true
    });

};


    // ==================================================
    // UPLOAD PROFILE PICTURE
    // ==================================================

    const handleProfilePictureUpload = async (event) => {

        const file = event.target.files?.[0];

        if (!file) {
            return;
        }


        // Only image files
        if (!file.type.startsWith("image/")) {

            alert(
                "Please select an image file."
            );

            return;
        }


        try {

            setUploading(true);


            const formData = new FormData();

            formData.append(
                "profile_picture",
                file
            );


            const token =
                localStorage.getItem("token");


            const response =
                await axios.post(
                    "https://user-mobility-prediction.onrender.com/users/me/profile-picture",
                    formData,
                    {
                        headers: {
                            Authorization:
                                `Bearer ${token}`
                        }
                    }
                );


            console.log(
                "Profile picture uploaded:",
                response.data
            );


            // Immediately update profile
            setGuardian(
                response.data
            );


            // Clear file input
            if (fileInputRef.current) {

                fileInputRef.current.value = "";

            }

        }
        catch (error) {

            console.error(
                "Profile picture upload failed:",
                error
            );


            alert(
                error.response?.data?.detail ||
                "Failed to upload profile picture."
            );

        }
        finally {

            setUploading(false);

        }

    };


    return (

        <>

            {/* ==================================================
                TOP BAR
            ================================================== */}

            <motion.div

                initial={{
                    opacity: 0,
                    y: -30
                }}

                animate={{
                    opacity: 1,
                    y: 0
                }}

                transition={{
                    duration: 0.6
                }}

                className="
                mx-6
                mt-6
                rounded-3xl
                border
                border-white/10
                bg-white/5
                backdrop-blur-2xl
                px-6
                py-4
                shadow-[0_0_40px_rgba(59,130,246,.15)]
                "

            >

                <div className="
                flex
                items-center
                justify-between
                ">


                    {/* ==================================================
                        LEFT - GUARDIAN AI
                    ================================================== */}

                    <div className="
                    flex
                    items-center
                    gap-4
                    ">

                        <div className="
                        relative
                        h-12
                        w-12
                        rounded-2xl
                        bg-gradient-to-br
                        from-blue-500/40
                        to-violet-500/40
                        flex
                        items-center
                        justify-center
                        ">

                            <FiShield
                                className="
                                text-blue-300
                                text-2xl
                                "
                            />


                            <motion.div

                                animate={{
                                    scale: [1, 1.3, 1]
                                }}

                                transition={{
                                    repeat: Infinity,
                                    duration: 2
                                }}

                                className="
                                absolute
                                inset-0
                                rounded-2xl
                                bg-blue-400/20
                                "
                            />

                        </div>


                        <div>

                            <h1 className="
                            text-white
                            font-semibold
                            text-xl
                            ">

                                Guardian AI

                            </h1>


                            <p className="
                            text-xs
                            text-gray-400
                            ">

                                Family Safety Monitoring

                            </p>

                        </div>

                    </div>


                    {/* ==================================================
                        SYSTEM STATUS
                    ================================================== */}

                    <div className="
                    hidden
                    md:flex
                    items-center
                    gap-3
                    px-5
                    py-3
                    rounded-2xl
                    bg-black/30
                    border
                    border-white/10
                    ">

                        <div

                            className={`
                            h-3
                            w-3
                            rounded-full

                            ${
                                isOnline
                                    ? "bg-green-400 shadow-[0_0_20px_#22c55e]"
                                    : "bg-red-400 shadow-[0_0_20px_#ef4444]"
                            }
                            `}

                        />


                        <div>

                            <p className="
                            text-xs
                            text-gray-400
                            ">

                                SYSTEM STATUS

                            </p>


                            <p className={`
                            text-sm

                            ${
                                isOnline
                                    ? "text-green-300"
                                    : "text-red-300"
                            }
                            `}>

                                {
                                    isOnline
                                        ? "AI Tracking Active"
                                        : "Guardian Offline"
                                }

                            </p>

                        </div>

                    </div>


                    {/* ==================================================
                        PROFILE
                    ================================================== */}

                    <div className="
                    flex
                    items-center
                    gap-4
                    ">

                        <button

                            type="button"

                            onClick={() =>
                                setShowProfile(true)
                            }

                            className="
                            flex
                            items-center
                            gap-3
                            px-4
                            py-2
                            rounded-2xl
                            bg-white/5
                            border
                            border-white/10
                            hover:bg-white/10
                            transition
                            cursor-pointer
                            "

                        >

                            <div className="
                            h-9
                            w-9
                            rounded-xl
                            overflow-hidden
                            bg-gradient-to-br
                            from-blue-500
                            to-violet-500
                            flex
                            items-center
                            justify-center
                            ">

                                {
                                    profileImage ? (

                                        <img
                                            src={profileImage}
                                            alt="Guardian profile"
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
                                            "
                                        />

                                    )
                                }

                            </div>


                            <div className="hidden md:block">

                                <p className="
                                text-sm
                                text-white
                                ">

                                    {
                                        guardian?.full_name ||
                                        "Guardian"
                                    }

                                </p>


                                <div className="
                                flex
                                items-center
                                gap-2
                                ">

                                    <div className={`
                                    h-2
                                    w-2
                                    rounded-full

                                    ${
                                        isOnline
                                            ? "bg-green-400"
                                            : "bg-red-400"
                                    }
                                    `} />

                                    <p className="
                                    text-xs
                                    text-gray-400
                                    ">

                                        {
                                            isOnline
                                                ? "Online"
                                                : "Offline"
                                        }

                                    </p>

                                </div>

                            </div>

                        </button>

                    </div>

                </div>

            </motion.div>


            {/* ==================================================
                PROFILE POPUP
            ================================================== */}

            {
                showProfile && (

                    <div

                        className="
                        fixed
                        inset-0
                        z-50
                        flex
                        items-center
                        justify-center
                        bg-black/70
                        backdrop-blur-sm
                        "

                        onClick={() =>
                            setShowProfile(false)
                        }

                    >

                        <div

                            className="
                            relative
                            w-[90%]
                            max-w-md
                            rounded-3xl
                            border
                            border-white/10
                            bg-[#111827]
                            p-6
                            shadow-[0_0_50px_rgba(59,130,246,.25)]
                            "

                            onClick={(event) =>
                                event.stopPropagation()
                            }

                        >

                            <button

                                type="button"

                                onClick={() =>
                                    setShowProfile(false)
                                }

                                className="
                                absolute
                                right-4
                                top-4
                                p-2
                                rounded-xl
                                bg-white/5
                                border
                                border-white/10
                                text-gray-300
                                hover:bg-white/10
                                "

                            >

                                <FiX />

                            </button>


                            <div className="
                            flex
                            flex-col
                            items-center
                            ">


                                {/* PROFILE IMAGE */}

                                <div className="
                                h-32
                                w-32
                                rounded-3xl
                                overflow-hidden
                                bg-gradient-to-br
                                from-blue-500
                                to-violet-500
                                flex
                                items-center
                                justify-center
                                border
                                border-white/10
                                ">

                                    {
                                        profileImage ? (

                                            <img
                                                src={profileImage}
                                                alt="Guardian profile"
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
                                                text-5xl
                                                "
                                            />

                                        )
                                    }

                                </div>


                                {/* ==================================================
                                    UPLOAD BUTTON
                                ================================================== */}

                                <input

                                    ref={fileInputRef}

                                    type="file"

                                    accept="image/jpeg,image/png,image/webp"

                                    onChange={
                                        handleProfilePictureUpload
                                    }

                                    className="hidden"

                                />


                                <button

                                    type="button"

                                    onClick={() =>
                                        fileInputRef.current?.click()
                                    }

                                    disabled={uploading}

                                    className="
                                    mt-4
                                    flex
                                    items-center
                                    gap-2
                                    px-4
                                    py-2
                                    rounded-xl
                                    bg-blue-500/20
                                    border
                                    border-blue-400/30
                                    text-blue-300
                                    hover:bg-blue-500/30
                                    transition
                                    disabled:opacity-50
                                    disabled:cursor-not-allowed
                                    "

                                >

                                    <FiCamera />

                                    {
                                        uploading
                                            ? "Uploading..."
                                            : "Upload Profile Picture"
                                    }

                                </button>


                                <h2 className="
                                mt-5
                                text-xl
                                font-semibold
                                text-white
                                ">

                                    {
                                        guardian?.full_name ||
                                        "Guardian"
                                    }

                                </h2>


                                {
                                    guardian?.email && (

                                        <p className="
                                        mt-1
                                        text-sm
                                        text-gray-400
                                        ">

                                            {guardian.email}

                                        </p>

                                    )
                                }


                                <div className="
                                mt-4
                                flex
                                items-center
                                gap-2
                                ">

                                    <div className={`
                                    h-2.5
                                    w-2.5
                                    rounded-full

                                    ${
                                        isOnline
                                            ? "bg-green-400"
                                            : "bg-red-400"
                                    }
                                    `} />


                                    <span className="
                                    text-sm
                                    text-gray-300
                                    ">

                                        {
                                            isOnline
                                                ? "Online"
                                                : "Offline"
                                        }

                                    </span>

                                </div>


                                {/* ==================================================
                                    LOGOUT BUTTON
                                ================================================== */}

                                <button

                                    type="button"

                                    onClick={handleLogout}

                                    className="
                                    mt-6
                                    w-full
                                    flex
                                    items-center
                                    justify-center
                                    gap-2
                                    rounded-xl
                                    bg-red-500/15
                                    border
                                    border-red-400/30
                                    px-4
                                    py-3
                                    text-sm
                                    font-medium
                                    text-red-300
                                    hover:bg-red-500/25
                                    hover:text-red-200
                                    transition
                                    "

                                >

                                    <FiLogOut />

                                    Logout

                                </button>

                            </div>

                        </div>

                    </div>

                )
            }

        </>

    );

}