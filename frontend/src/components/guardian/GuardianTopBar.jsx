
import { motion } from "framer-motion";

import {
    FiShield,
    FiUser,
    FiX,
    FiCamera,
    FiLogOut,
    FiEdit2,
} from "react-icons/fi";

import {
    useEffect,
    useRef,
    useState
} from "react";

import { useNavigate } from "react-router-dom";

import { getUserProfile } from "../../services/locationService";

import {
    getGuardianTeam,
    uploadGuardianTeamPhoto,
    updateGuardianTeamMember
} from "../../services/guardianApi";

import axios from "axios";

import { useAuth } from "../../context/AuthContext";

const BACKEND_URL =
    "https://user-mobility-prediction.onrender.com";

export default function GuardianTopBar() {

    const navigate = useNavigate();

    const { logout } = useAuth();

    const [guardian, setGuardian] = useState(null);

    const [showProfile, setShowProfile] = useState(false);

    const [showTeam, setShowTeam] = useState(false);

    const [teamMembers, setTeamMembers] = useState([]);

    const [teamLoading, setTeamLoading] = useState(false);

    const [uploading, setUploading] = useState(false);

    const [uploadingTeamId, setUploadingTeamId] = useState(null);

    const [editingTeamId, setEditingTeamId] = useState(null);

    const [editTeamForm, setEditTeamForm] = useState({
        name: "",
        roll_no: "",
        email: "",
        role: "",
        details: ""
    });

    const [savingTeamId, setSavingTeamId] = useState(null);

    const teamFileInputRefs = useRef({});

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


    useEffect(() => {

        if (showTeam || showProfile || editingTeamId) {

            document.body.style.overflow = "hidden";

        } else {

            document.body.style.overflow = "";

        }

        return () => {

            document.body.style.overflow = "";

        };

    }, [
        showTeam,
        showProfile,
        editingTeamId
    ]);


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
                : `${BACKEND_URL}${guardian.profile_picture}`
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
                    `${BACKEND_URL}/users/me/profile-picture`,
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


            setGuardian(
                response.data
            );


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


    // ==================================================
    // LOAD GUARDIAN TEAM
    // ==================================================

    const loadGuardianTeam = async () => {

        try {

            setTeamLoading(true);

            const data = await getGuardianTeam();

            console.log(
                "Guardian Team:",
                data
            );

            setTeamMembers(
                Array.isArray(data)
                    ? data
                    : []
            );

        }
        catch (error) {

            console.log(
                "Guardian team error:",
                error
            );

            setTeamMembers([]);

        }
        finally {

            setTeamLoading(false);

        }

    };


    // ==================================================
    // UPLOAD TEAM MEMBER PICTURE
    // ==================================================

    const handleTeamMemberImageUpload = async (
        person,
        event
    ) => {

        const file =
            event.target.files?.[0];

        if (!file) {
            return;
        }


        if (!file.type.startsWith("image/")) {

            alert(
                "Please select an image file."
            );

            event.target.value = "";

            return;
        }


        try {

            const formData =
                new FormData();

            formData.append(
                "file",
                file
            );


            const token =
                localStorage.getItem("token");


            console.log(
                "Uploading team member image:",
                person.id
            );


            await axios.post(
                `${BACKEND_URL}/guardian/team/${person.id}/image`,
                formData,
                {
                    headers: {
                        Authorization:
                            `Bearer ${token}`
                    }
                }
            );


            console.log(
                "Team member image uploaded successfully."
            );


            await loadGuardianTeam();

        }
        catch (error) {

            console.error(
                "Team member image upload failed:",
                error
            );


            alert(
                error.response?.data?.detail ||
                "Failed to upload team member picture."
            );

        }
        finally {

            event.target.value = "";

        }

    };


    // ==================================================
    // SEPARATE TEAM TYPES
    // ==================================================

    const hodMembers = [
        {
            id: "hod-1",
            person_type: "HOD",
            name: "Dr. HOD Name",
            email: "hod@college.com",
            role: "Head of Department",
            details: "Computer Science and Engineering",
            image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRKSZrraDYcfuXI23SGP_zbHedHjdCVOou7I7j6Wk3Y3g&s=10",
        },
    ];


    const mentorMembers = [
        {
            id: "mentor-1",
            person_type: "MENTOR",
            name: "Dr. Mentor Name",
            email: "mentor@college.com",
            role: "Project Mentor",
            details: "Project Guide",
            image: "https://adityatekkali.edu.in/Files/images/A5INT00T72.jpg",
        },
    ];


    const memberMembers =
        teamMembers.filter(
            (person) =>
                String(person?.person_type || "")
                    .toUpperCase() === "MEMBER"
        );


    // ==================================================
    // TEAM IMAGE URL
    // ==================================================

    const getTeamImage = (person) => {

    if (!person?.image) {
        return null;
    }

    const imageUrl =
        typeof person.image === "string" &&
        person.image.startsWith("http")
            ? person.image
            : `${BACKEND_URL}${person.image}`;

    return person.updated_at
        ? `${imageUrl}?t=${encodeURIComponent(person.updated_at)}`
        : imageUrl;
};


    // ==================================================
    // TEAM PERSON CARD
    // HOD + MENTOR
    // ==================================================

    const TeamPersonCard = ({
        person,
        type
    }) => {

        const image =
            getTeamImage(person);


        return (

            <div
                className="
                rounded-2xl
                border
                border-white/10
                bg-white/5
                p-5
                "
            >

                <div
                    className="
                    flex
                    items-center
                    gap-4
                    "
                >

                    {/* IMAGE */}

                    <div
                        className="
                        h-34
                        w-34
                        shrink-0
                        rounded-2xl
                        overflow-hidden
                        bg-blue-500/20
                        flex
                        items-center
                        justify-center
                        "
                    >

                        {image ? (

                            <img
                                src={image}
                                alt={
                                    person?.name ||
                                    "Team member"
                                }
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
                                text-2xl
                                "
                            />

                        )}

                    </div>


                    {/* DETAILS */}

                    <div
                        className="
                        min-w-0
                        flex-1
                        "
                    >

                        <p
                            className="
                            text-white
                            font-semibold
                            text-lg
                            "
                        >
                            {person?.name || "Unknown"}
                        </p>


                        <p
                            className="
                            text-xs
                            text-blue-300
                            mt-1
                            "
                        >
                            {type}
                        </p>


                        {person?.role && (

                            <p
                                className="
                                text-xs
                                text-gray-400
                                mt-1
                                "
                            >
                                {person.role}
                            </p>

                        )}


                        {person?.roll_no && (

                            <p
                                className="
                                text-xs
                                text-gray-400
                                mt-1
                                "
                            >
                                Roll No: {person.roll_no}
                            </p>

                        )}


                        {person?.email && (

                            <p
                                className="
                                text-xs
                                text-gray-400
                                mt-1
                                truncate
                                "
                            >
                                {person.email}
                            </p>

                        )}

                    </div>

                </div>


                {person?.details && (

                    <p
                        className="
                        mt-4
                        text-sm
                        text-gray-400
                        border-t
                        border-white/10
                        pt-3
                        "
                    >
                        {person.details}
                    </p>

                )}

            </div>

        );

    };


    // ==================================================
    // TEAM MEMBER PHOTO UPLOAD
    // ==================================================

    const handleTeamMemberPhotoUpload = async (
        event,
        person
    ) => {

        const file =
            event.target.files?.[0];

        if (!file) {
            return;
        }


        if (!file.type.startsWith("image/")) {

            alert(
                "Please select an image file."
            );

            event.target.value = "";

            return;
        }


        try {

            setUploadingTeamId(person.id);


            const updatedPerson =
                await uploadGuardianTeamPhoto(
                    person.id,
                    file
                );


            console.log(
                "Team member photo uploaded:",
                updatedPerson
            );


            await loadGuardianTeam();

        }
        catch (error) {

            console.error(
                "Team member photo upload failed:",
                error
            );


            alert(
                error.response?.data?.detail ||
                "Failed to upload team member photo."
            );

        }
        finally {

            setUploadingTeamId(null);


            if (
                teamFileInputRefs.current[person.id]
            ) {

                teamFileInputRefs.current[
                    person.id
                ].value = "";

            }

        }

    };


    // ==================================================
    // EDIT TEAM MEMBER
    // ==================================================

    const handleEditTeamMember = (person) => {

        setEditingTeamId(person.id);

        setEditTeamForm({
            name: person.name || "",
            roll_no: person.roll_no || "",
            email: person.email || "",
            role: person.role || "",
            details: person.details || ""
        });

    };


    // ==================================================
    // EDIT FORM CHANGE
    // ==================================================

    const handleEditTeamFormChange = (event) => {

        const {
            name,
            value
        } = event.target;


        setEditTeamForm(
            (previous) => ({
                ...previous,
                [name]: value
            })
        );

    };


    // ==================================================
    // SAVE TEAM MEMBER
    // ==================================================

    const handleSaveTeamMember = async (person) => {

        try {

            setSavingTeamId(person.id);


            const updatedPerson =
                await updateGuardianTeamMember(
                    person.id,
                    {
                        name: editTeamForm.name,
                        roll_no: editTeamForm.roll_no,
                        email: editTeamForm.email,
                        role: editTeamForm.role,
                        details: editTeamForm.details
                    }
                );


            console.log(
                "Team member updated:",
                updatedPerson
            );


            setEditingTeamId(null);


            await loadGuardianTeam();

        }
        catch (error) {

            console.error(
                "Team member update failed:",
                error
            );


            alert(
                error.response?.data?.detail ||
                "Failed to update team member."
            );

        }
        finally {

            setSavingTeamId(null);

        }

    };


    // ==================================================
    // CANCEL EDIT
    // ==================================================

    const handleCancelEditTeamMember = () => {

        setEditingTeamId(null);

    };


    // ==================================================
    // TEAM MEMBER CARD
    // ONLY TEAM MEMBERS HAVE EDIT / UPLOAD
    // ==================================================

    const TeamMemberCard = ({
        person
    }) => {

        const image =
            getTeamImage(person);


        const inputId =
            `team-member-image-${person.id}`;


        return (

            <div
                className="
                rounded-2xl
                border
                border-white/10
                bg-white/5
                p-4
                "
            >

                {/* IMAGE */}

                <div
                    className="
                    mx-auto
                    h-20
                    w-20
                    rounded-2xl
                    overflow-hidden
                    bg-blue-500/20
                    flex
                    items-center
                    justify-center
                    "
                >

                    {image ? (

                        <img
                            src={image}
                            alt={
                                person?.name ||
                                "Team member"
                            }
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
                            text-2xl
                            "
                        />

                    )}

                </div>


                {/* IMAGE UPLOAD */}

                <input
                    id={inputId}
                    type="file"
                    accept="image/jpeg,image/png,image/webp"
                    onChange={(event) =>
                        handleTeamMemberPhotoUpload(
                            event,
                            person
                        )
                    }
                    className="hidden"
                />


                <label
                    htmlFor={inputId}
                    className="
                    mx-auto
                    mt-3
                    flex
                    w-fit
                    cursor-pointer
                    items-center
                    gap-2
                    rounded-xl
                    border
                    border-blue-400/30
                    bg-blue-500/10
                    px-3
                    py-2
                    text-xs
                    font-medium
                    text-blue-300
                    transition
                    hover:bg-blue-500/20
                    "
                >

                    {uploadingTeamId === person.id ? (

                        <>
                            <FiCamera />
                            Uploading...
                        </>

                    ) : image ? (

                        <>
                            <FiEdit2 />
                            Edit Picture
                        </>

                    ) : (

                        <>
                            <FiCamera />
                            Upload Picture
                        </>

                    )}

                </label>


                {/* EDIT DETAILS BUTTON */}

                <button
                    type="button"
                    onClick={() =>
                        handleEditTeamMember(person)
                    }
                    className="
                    mx-auto
                    mt-2
                    flex
                    w-fit
                    items-center
                    gap-2
                    rounded-xl
                    border
                    border-violet-400/30
                    bg-violet-500/10
                    px-3
                    py-2
                    text-xs
                    font-medium
                    text-violet-300
                    transition
                    hover:bg-violet-500/20
                    "
                >

                    <FiEdit2 />

                    Edit Details

                </button>


                {/* DETAILS */}

                <div
                    className="
                    mt-4
                    text-center
                    "
                >

                    <p
                        className="
                        text-white
                        font-semibold
                        truncate
                        "
                    >
                        {person?.name || "Unknown"}
                    </p>


                    <p
                        className="
                        text-xs
                        text-violet-300
                        mt-1
                        "
                    >
                        Team Member
                    </p>


                    {person?.roll_no && (

                        <p
                            className="
                            text-xs
                            text-gray-400
                            mt-2
                            "
                        >
                            Roll No: {person.roll_no}
                        </p>

                    )}


                    {person?.role && (

                        <p
                            className="
                            text-xs
                            text-gray-400
                            mt-1
                            truncate
                            "
                        >
                            {person.role}
                        </p>

                    )}


                    {person?.email && (

                        <p
                            className="
                            text-xs
                            text-gray-400
                            mt-1
                            truncate
                            "
                        >
                            {person.email}
                        </p>

                    )}

                </div>


                {person?.details && (

                    <p
                        className="
                        mt-3
                        text-xs
                        text-gray-400
                        border-t
                        border-white/10
                        pt-3
                        "
                    >
                        {person.details}
                    </p>

                )}

            </div>

        );

    };


    // ==================================================
    // RETURN
    // ==================================================

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

                <div
                    className="
                    flex
                    items-center
                    justify-between
                    "
                >

                    {/* LEFT */}

                    <div
                        className="
                        flex
                        items-center
                        gap-4
                        "
                    >

                        <div
                            onClick={() => {

                                setShowTeam(true);

                                loadGuardianTeam();

                            }}
                            className="
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
                            cursor-pointer
                            "
                        >

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

                            <h1
                                className="
                                text-white
                                font-semibold
                                text-xl
                                "
                            >
                                Guardian AI
                            </h1>


                            <p
                                className="
                                text-xs
                                text-gray-400
                                "
                            >
                                Family Safety Monitoring
                            </p>

                        </div>

                    </div>


                    {/* SYSTEM STATUS */}

                    <div
                        className="
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
                        "
                    >

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

                            <p
                                className="
                                text-xs
                                text-gray-400
                                "
                            >
                                SYSTEM STATUS
                            </p>


                            <p
                                className={`
                                text-sm
                                ${
                                    isOnline
                                        ? "text-green-300"
                                        : "text-red-300"
                                }
                                `}
                            >
                                {
                                    isOnline
                                        ? "AI Tracking Active"
                                        : "Guardian Offline"
                                }
                            </p>

                        </div>

                    </div>


                    {/* PROFILE */}

                    <div
                        className="
                        flex
                        items-center
                        gap-4
                        "
                    >

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

                            <div
                                className="
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
                                "
                            >

                                {profileImage ? (

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

                                )}

                            </div>


                            <div
                                className="
                                hidden
                                md:block
                                "
                            >

                                <p
                                    className="
                                    text-sm
                                    text-white
                                    "
                                >
                                    {
                                        guardian?.full_name ||
                                        "Guardian"
                                    }
                                </p>


                                <div
                                    className="
                                    flex
                                    items-center
                                    gap-2
                                    "
                                >

                                    <div
                                        className={`
                                        h-2
                                        w-2
                                        rounded-full
                                        ${
                                            isOnline
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

            {showProfile && (

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
                        w-[95%]
                        max-w-6xl
                        max-h-[92vh]
                        overflow-y-visible
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


                        <div
                            className="
                            flex
                            flex-col
                            items-center
                            "
                        >

                            {/* PROFILE IMAGE */}

                            <div
                                className="
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
                                "
                            >

                                {profileImage ? (

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

                                )}

                            </div>


                            {/* UPLOAD */}

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


                            <h2
                                className="
                                mt-5
                                text-xl
                                font-semibold
                                text-white
                                "
                            >
                                {
                                    guardian?.full_name ||
                                    "Guardian"
                                }
                            </h2>


                            {guardian?.email && (

                                <p
                                    className="
                                    mt-1
                                    text-sm
                                    text-gray-400
                                    "
                                >
                                    {guardian.email}
                                </p>

                            )}


                            <div
                                className="
                                mt-4
                                flex
                                items-center
                                gap-2
                                "
                            >

                                <div
                                    className={`
                                    h-2.5
                                    w-2.5
                                    rounded-full
                                    ${
                                        isOnline
                                            ? "bg-green-400"
                                            : "bg-red-400"
                                    }
                                    `}
                                />


                                <span
                                    className="
                                    text-sm
                                    text-gray-300
                                    "
                                >
                                    {
                                        isOnline
                                            ? "Online"
                                            : "Offline"
                                    }
                                </span>

                            </div>


                            {/* LOGOUT */}

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

            )}


            {/* ==================================================
                GUARDIAN TEAM POPUP
            ================================================== */}

            {showTeam && (

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
                        setShowTeam(false)
                    }
                >

                    <div
                        className="
                        relative
                        w-[93%]
                        max-w-[1400px]
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

                        {/* CLOSE */}

                        <button
                            type="button"
                            onClick={() =>
                                setShowTeam(false)
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


                        {/* HEADER */}

                        <div
                            className="
                            mb-6
                            "
                        >

                            <h2
                                className="
                                text-xl
                                font-semibold
                                text-white
                                "
                            >
                                Guardian Team
                            </h2>


                            <p
                                className="
                                mt-1
                                text-sm
                                text-gray-400
                                "
                            >
                                Team members connected with Guardian AI
                            </p>

                        </div>


                        {/* LOADING */}

                        {teamLoading ? (

                            <div
                                className="
                                py-10
                                text-center
                                text-gray-400
                                "
                            >
                                Loading Guardian Team...
                            </div>

                        ) : teamMembers.length === 0 ? (

                            <div
                                className="
                                py-10
                                text-center
                                text-gray-400
                                "
                            >
                                No Guardian Team information available.
                            </div>

                        ) : (

                            <div
                                className="
                                space-y-8
                                "
                            >

                                {/* HOD + MENTOR */}

                                <div>

                                    <div
                                        className="
                                        flex
                                        items-center
                                        gap-2
                                        mb-3
                                        "
                                    >

                                        <div
                                            className="
                                            h-2
                                            w-2
                                            rounded-full
                                            bg-blue-400
                                            "
                                        />


                                        <h3
                                            className="
                                            text-sm
                                            font-semibold
                                            text-blue-300
                                            uppercase
                                            tracking-wider
                                            "
                                        >
                                            HOD & Mentor
                                        </h3>

                                    </div>


                                    <div
                                        className="
                                        grid
                                        grid-cols-1
                                        md:grid-cols-2
                                        gap-4
                                        "
                                    >

                                        {hodMembers.map(
                                            (person) => (

                                                <TeamPersonCard
                                                    key={person.id}
                                                    person={person}
                                                    type="HOD"
                                                />

                                            )
                                        )}


                                        {mentorMembers.map(
                                            (person) => (

                                                <TeamPersonCard
                                                    key={person.id}
                                                    person={person}
                                                    type="Mentor"
                                                />

                                            )
                                        )}

                                    </div>

                                </div>


                                {/* TEAM MEMBERS */}

                                {memberMembers.length > 0 && (

                                    <div>

                                        <div
                                            className="
                                            flex
                                            items-center
                                            gap-2
                                            mb-3
                                            "
                                        >

                                            <div
                                                className="
                                                h-2
                                                w-2
                                                rounded-full
                                                bg-violet-400
                                                "
                                            />


                                            <h3
                                                className="
                                                text-sm
                                                font-semibold
                                                text-violet-300
                                                uppercase
                                                tracking-wider
                                                "
                                            >
                                                Team Members
                                            </h3>

                                        </div>


                                        <div
                                            className="
                                            grid
                                            grid-cols-1
                                            sm:grid-cols-2
                                            lg:grid-cols-4
                                            gap-4
                                            "
                                        >

                                            {memberMembers
                                                .slice(0, 4)
                                                .map(
                                                    (person) => (

                                                        <TeamMemberCard
                                                            key={person.id}
                                                            person={person}
                                                        />

                                                    )
                                                )}

                                        </div>

                                    </div>

                                )}

                            </div>

                        )}

                    </div>

                </div>

            )}


            {/* ==================================================
                EDIT TEAM MEMBER POPUP
            ================================================== */}

            {editingTeamId && (

                <div
                    className="
                    fixed
                    inset-0
                    z-[60]
                    flex
                    items-center
                    justify-center
                    bg-black/70
                    backdrop-blur-sm
                    p-4
                    "
                    onClick={
                        handleCancelEditTeamMember
                    }
                >

                    <div
                        className="
                        relative
                        w-full
                        max-w-lg
                        max-h-[90vh]
                        overflow-y-auto
                        rounded-3xl
                        border
                        border-white/10
                        bg-[#111827]
                        p-6
                        shadow-[0_0_50px_rgba(139,92,246,.25)]
                        "
                        onClick={(event) =>
                            event.stopPropagation()
                        }
                    >

                        {/* CLOSE */}

                        <button
                            type="button"
                            onClick={
                                handleCancelEditTeamMember
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
                            transition
                            "
                        >
                            <FiX />
                        </button>


                        {/* HEADER */}

                        <div
                            className="
                            mb-6
                            pr-10
                            "
                        >

                            <h2
                                className="
                                text-xl
                                font-semibold
                                text-white
                                "
                            >
                                Edit Team Member
                            </h2>


                            <p
                                className="
                                mt-1
                                text-sm
                                text-gray-400
                                "
                            >
                                Update team member information
                            </p>

                        </div>


                        {/* FORM */}

                        <div
                            className="
                            space-y-4
                            "
                        >

                            {/* NAME */}

                            <div>

                                <label
                                    className="
                                    mb-1.5
                                    block
                                    text-xs
                                    font-medium
                                    text-gray-400
                                    "
                                >
                                    Name
                                </label>


                                <input
                                    type="text"
                                    name="name"
                                    value={
                                        editTeamForm.name
                                    }
                                    onChange={
                                        handleEditTeamFormChange
                                    }
                                    className="
                                    w-full
                                    rounded-xl
                                    border
                                    border-white/10
                                    bg-white/5
                                    px-4
                                    py-3
                                    text-sm
                                    text-white
                                    outline-none
                                    placeholder:text-gray-500
                                    focus:border-violet-400/50
                                    "
                                    placeholder="Enter name"
                                />

                            </div>


                            {/* ROLL NUMBER */}

                            <div>

                                <label
                                    className="
                                    mb-1.5
                                    block
                                    text-xs
                                    font-medium
                                    text-gray-400
                                    "
                                >
                                    Roll Number
                                </label>


                                <input
                                    type="text"
                                    name="roll_no"
                                    value={
                                        editTeamForm.roll_no
                                    }
                                    onChange={
                                        handleEditTeamFormChange
                                    }
                                    className="
                                    w-full
                                    rounded-xl
                                    border
                                    border-white/10
                                    bg-white/5
                                    px-4
                                    py-3
                                    text-sm
                                    text-white
                                    outline-none
                                    placeholder:text-gray-500
                                    focus:border-violet-400/50
                                    "
                                    placeholder="Enter roll number"
                                />

                            </div>


                            {/* EMAIL */}

                            <div>

                                <label
                                    className="
                                    mb-1.5
                                    block
                                    text-xs
                                    font-medium
                                    text-gray-400
                                    "
                                >
                                    Email
                                </label>


                                <input
                                    type="email"
                                    name="email"
                                    value={
                                        editTeamForm.email
                                    }
                                    onChange={
                                        handleEditTeamFormChange
                                    }
                                    className="
                                    w-full
                                    rounded-xl
                                    border
                                    border-white/10
                                    bg-white/5
                                    px-4
                                    py-3
                                    text-sm
                                    text-white
                                    outline-none
                                    placeholder:text-gray-500
                                    focus:border-violet-400/50
                                    "
                                    placeholder="Enter email"
                                />

                            </div>


                            {/* ROLE */}

                            <div>

                                <label
                                    className="
                                    mb-1.5
                                    block
                                    text-xs
                                    font-medium
                                    text-gray-400
                                    "
                                >
                                    Role
                                </label>


                                <input
                                    type="text"
                                    name="role"
                                    value={
                                        editTeamForm.role
                                    }
                                    onChange={
                                        handleEditTeamFormChange
                                    }
                                    className="
                                    w-full
                                    rounded-xl
                                    border
                                    border-white/10
                                    bg-white/5
                                    px-4
                                    py-3
                                    text-sm
                                    text-white
                                    outline-none
                                    placeholder:text-gray-500
                                    focus:border-violet-400/50
                                    "
                                    placeholder="Enter role"
                                />

                            </div>


                            {/* DETAILS */}

                            <div>

                                <label
                                    className="
                                    mb-1.5
                                    block
                                    text-xs
                                    font-medium
                                    text-gray-400
                                    "
                                >
                                    Details
                                </label>


                                <textarea
                                    name="details"
                                    value={
                                        editTeamForm.details
                                    }
                                    onChange={
                                        handleEditTeamFormChange
                                    }
                                    rows={3}
                                    className="
                                    w-full
                                    resize-none
                                    rounded-xl
                                    border
                                    border-white/10
                                    bg-white/5
                                    px-4
                                    py-3
                                    text-sm
                                    text-white
                                    outline-none
                                    placeholder:text-gray-500
                                    focus:border-violet-400/50
                                    "
                                    placeholder="Enter details"
                                />

                            </div>

                        </div>


                        {/* BUTTONS */}

                        <div
                            className="
                            mt-6
                            flex
                            gap-3
                            "
                        >

                            <button
                                type="button"
                                onClick={
                                    handleCancelEditTeamMember
                                }
                                disabled={
                                    savingTeamId !== null
                                }
                                className="
                                flex-1
                                rounded-xl
                                border
                                border-white/10
                                bg-white/5
                                px-4
                                py-3
                                text-sm
                                font-medium
                                text-gray-300
                                hover:bg-white/10
                                transition
                                disabled:opacity-50
                                "
                            >
                                Cancel
                            </button>


                            <button
                                type="button"
                                onClick={() => {

                                    const person =
                                        teamMembers.find(
                                            (member) =>
                                                member.id ===
                                                editingTeamId
                                        );


                                    if (person) {

                                        handleSaveTeamMember(
                                            person
                                        );

                                    }

                                }}
                                disabled={
                                    savingTeamId ===
                                    editingTeamId
                                }
                                className="
                                flex-1
                                rounded-xl
                                bg-violet-500/20
                                border
                                border-violet-400/30
                                px-4
                                py-3
                                text-sm
                                font-medium
                                text-violet-300
                                hover:bg-violet-500/30
                                transition
                                disabled:opacity-50
                                disabled:cursor-not-allowed
                                "
                            >

                                {
                                    savingTeamId ===
                                    editingTeamId
                                        ? "Saving..."
                                        : "Save Changes"
                                }

                            </button>

                        </div>

                    </div>

                </div>

            )}

        </>

    );

}
