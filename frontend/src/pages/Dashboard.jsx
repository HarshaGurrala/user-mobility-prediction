import { motion } from "framer-motion";

import DashboardHeader from "../components/dashboard/DashboardHeader";
import AIPredictionCard from "../components/dashboard/AIPredictionCard";
import AlertsCard from "../components/dashboard/AlertsCard";

import LiveMap from "../components/dashboard/LiveMap";

import { useParams } from "react-router-dom";

import LocationPicker from "../components/LocationPicker";

import {
    useState,
    useEffect,
    useRef
} from "react";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";



import SingleUserMovementChart
from "../components/dashboard/SingleUserMovementChart";

import {
    getGuardianUserDetails,
    getGuardianUsers,
    getGuardianMovement,
    getEmergencyContacts,
    addEmergencyContact,
    updateEmergencyContact,
    deleteEmergencyContact,
    getMovementAnalytics,
    getNextPrediction,
    getGuardianAlerts
} from "../services/guardianApi";


import {
    getSafeLocations,
    addSafeLocation,
    updateSafeLocation,
    deleteSafeLocation,
    searchSafeLocation,
    searchSafeLocationApi
} from"../services/safeLocationApi";


export default function Dashboard() {


    const navigate = useNavigate();

    const { logout } = useAuth();

   const handleLogout = () => {

        logout();

        navigate("/login", {
            replace: true
        });

    };

    const [prediction, setPrediction] = useState(null);

const [predictionLoading, setPredictionLoading] =
    useState(false);

    const guardianId =
        localStorage.getItem("userId");
    const [showLocationPicker, setShowLocationPicker] =useState(false);

  const [users, setUsers] = useState([]);
  
  const [selectedUserData, setSelectedUserData] = useState(null);

  const [alerts, setAlerts] = useState([]);
    const [alertsLoading, setAlertsLoading] = useState(false);

  const [movementAnalytics, setMovementAnalytics] = useState(null);

  const [locationSuggestions, setLocationSuggestions] = useState([]);
const [locationSearching, setLocationSearching] = useState(false);
const [locationSearchDone, setLocationSearchDone] = useState(false);

  const [selectedUser, setSelectedUser] = useState(null);

    const [safeLocations, setSafeLocations] = useState([]);

    const [safeLocationLoading, setSafeLocationLoading] = useState(false);

    const [safeLocationError, setSafeLocationError] = useState(null);

    const [showSafeLocationForm, setShowSafeLocationForm] = useState(false);

    const [editingSafeLocationId, setEditingSafeLocationId] = useState(null);

    const [safeLocationForm, setSafeLocationForm] = useState({
        location_name: "",
        latitude: "",
        longitude: "",
        radius: "100"
    });

    const [emergencyContacts, setEmergencyContacts] = useState([]);

    const [emergencyLoading, setEmergencyLoading] = useState(false);

    const [emergencyError, setEmergencyError] = useState(null);

    const [emergencyMessage, setEmergencyMessage] = useState("");

    const [showEmergencyForm, setShowEmergencyForm] = useState(false);

    const [editingEmergencyId, setEditingEmergencyId] = useState(null);

    const [emergencyForm, setEmergencyForm] = useState({
        name: "",
        relationship_type: "",
        phone_number: "",
        email: ""
    });


    const [movement, setMovement] = useState(null);

    const { userId } = useParams();

    const [userDetails, setUserDetails] = useState(null);

    const [loading, setLoading] = useState(true);

    const [movementLoading, setMovementLoading] = useState(true);

    const [error, setError] = useState(null);

    const [movementError, setMovementError] = useState(null);




const safeLocationSearchTimer =
    useRef(null);


const handleSafeLocationChange = (e) => {

    const {
        name,
        value
    } = e.target;

    setSafeLocationForm(
        previous => ({
            ...previous,
            [name]: value
        })
    );
};


const resetSafeLocationForm = () => {

    setSafeLocationForm({
        location_name: "",
        latitude: "",
        longitude: "",
        radius: "100"
    });

    setEditingSafeLocationId(null);

    setShowSafeLocationForm(false);
};


  const handleAddSafeLocation = async () => {

    if (!selectedUserData?.user_id) {
        setSafeLocationError(
            "Please select a user first."
        );
        return;
    }

    try {

        setSafeLocationLoading(true);
        setSafeLocationError(null);

       const newLocation = await addSafeLocation(
    selectedUserData.user_id,
    {
        location_name: safeLocationForm.location_name,
        latitude: Number(safeLocationForm.latitude),
        longitude: Number(safeLocationForm.longitude),
        radius: Number(safeLocationForm.radius)
    }
);

        setSafeLocations(
            previous => [
                ...previous,
                newLocation
            ]
        );

        resetSafeLocationForm();

    } catch (err) {

        console.error(
            "Add safe location failed:",
            err
        );

        setSafeLocationError(
            err.response?.data?.detail ||
            err.message ||
            "Failed to add safe location."
        );

    } finally {

        setSafeLocationLoading(false);

    }
};




const handleEditSafeLocation = (location) => {

    setEditingSafeLocationId(
        location.id
    );

    setSafeLocationForm({
        location_name:
            location.location_name || "",

        latitude:
            String(
                location.latitude ?? ""
            ),

        longitude:
            String(
                location.longitude ?? ""
            ),

        radius:
            String(
                location.radius ?? 100
            )
    });

    setShowSafeLocationForm(true);
};


  const handleUpdateSafeLocation = async () => {

    if (!editingSafeLocationId) {
        return;
    }

    try {

        setSafeLocationLoading(true);
        setSafeLocationError(null);

        const updatedLocation =
            await updateSafeLocation(
                editingSafeLocationId,
                {
                    location_name:
                        safeLocationForm.location_name,

                    latitude:
                        Number(
                            safeLocationForm.latitude
                        ),

                    longitude:
                        Number(
                            safeLocationForm.longitude
                        ),

                    radius:
                        Number(
                            safeLocationForm.radius
                        )
                }
            );

        setSafeLocations(
            previous =>
                previous.map(location =>
                    location.id ===
                    editingSafeLocationId
                        ? updatedLocation
                        : location
                )
        );

        resetSafeLocationForm();

    } catch (err) {

        console.error(
            "Update safe location failed:",
            err
        );

        setSafeLocationError(
            err.response?.data?.detail ||
            err.message ||
            "Failed to update safe location."
        );

    } finally {

        setSafeLocationLoading(false);

    }
};




const handleDeleteSafeLocation = async (
    locationId
) => {

    const confirmed =
        window.confirm(
            "Delete this safe zone?"
        );

    if (!confirmed) {
        return;
    }

    try {

        setSafeLocationLoading(true);
        setSafeLocationError(null);

        await deleteSafeLocation(
            locationId
        );

        setSafeLocations(
            previous =>
                previous.filter(
                    location =>
                        location.id !== locationId
                )
        );

    } catch (err) {

        console.error(
            "Delete safe location failed:",
            err
        );

        setSafeLocationError(
            err.response?.data?.detail ||
            err.message ||
            "Failed to delete safe location."
        );

    } finally {

        setSafeLocationLoading(false);

    }
};




    const loadSafeLocations = async (userId) => {

    if (!userId) {
        setSafeLocations([]);
        return;
    }

    try {

        setSafeLocationLoading(true);
        setSafeLocationError(null);

        const response =
            await getSafeLocations(userId);

        setSafeLocations(
            Array.isArray(response)
                ? response
                : []
        );

    } catch (err) {

        console.error(
            "Load safe locations failed:",
            err
        );

        setSafeLocationError(
            err.response?.data?.detail ||
            err.message ||
            "Failed to load safe locations."
        );

    } finally {

        setSafeLocationLoading(false);

    }
};






    // =====================================================
    // ADD EMERGENCY CONTACT
    // =====================================================

    const handleAddEmergencyContact = async () => {

        if (!userId) {
            setEmergencyError("User ID is missing.");
            return;
        }

        try {

            setEmergencyLoading(true);
            setEmergencyError(null);
            setEmergencyMessage("");

            const contact =
                await addEmergencyContact(
                    userId,
                    emergencyForm
                );

            setEmergencyContacts(prev => [
                ...prev,
                contact
            ]);

            setEmergencyForm({
                name: "",
                relationship_type: "",
                phone_number: "",
                email: ""
            });

            setShowEmergencyForm(false);

            setEmergencyMessage(
                "Emergency contact added successfully."
            );

        } catch (err) {

            console.error(
                "Add emergency contact failed:",
                err
            );

            setEmergencyError(
                err.response?.data?.detail ||
                err.message ||
                "Failed to add emergency contact."
            );

        } finally {

            setEmergencyLoading(false);

        }

    };

  // =====================================================
// LOAD SAFE LOCATIONS WHEN SELECTED USER CHANGES
// =====================================================

useEffect(() => {
    if (selectedUserData?.user_id) {
        loadSafeLocations(selectedUserData.user_id);
    } else {
        setSafeLocations([]);
    }
}, [selectedUserData]);


// =====================================================
// LOAD SELECTED USER
// =====================================================

useEffect(() => {

    const loadUserDetails = async () => {

        if (!userId) {

            setError("User ID is missing.");

            setLoading(false);

            return;
        }

        try {

            setLoading(true);

            setError(null);


            // =========================================
            // SELECTED USER DETAILS
            // =========================================

            const userData =
                await getGuardianUserDetails(
                    userId
                );

            console.log(
                "Selected Guardian User:",
                userData
            );

            setSelectedUserData(
                userData
            );

            setUserDetails(
                userData
            );


            // =========================================
            // EMERGENCY CONTACTS
            // =========================================

            const contacts =
                await getEmergencyContacts(
                    userId
                );

            setEmergencyContacts(
                Array.isArray(contacts)
                    ? contacts
                    : []
            );


            // =========================================
            // SELECTED USER ALERTS
            // =========================================

            setAlertsLoading(true);

            try {

                const guardianAlerts =
                    await getGuardianAlerts(
                        guardianId
                    );

                console.log(
                    "Guardian Alerts:",
                    guardianAlerts
                );


                const selectedUserAlerts =
                    Array.isArray(
                        guardianAlerts
                    )
                        ? guardianAlerts.filter(
                            alert =>
                                Number(
                                    alert.user_id
                                ) ===
                                Number(userId)
                        )
                        : [];


                console.log(
                    "Selected User Alerts:",
                    selectedUserAlerts
                );


                setAlerts(
                    selectedUserAlerts
                );

            } catch (err) {

                console.error(
                    "Selected user alerts failed:",
                    err
                );

                setAlerts([]);

            } finally {

                setAlertsLoading(
                    false
                );
            }


            // =========================================
            // SELECTED USER MOVEMENT
            // =========================================

            console.log(
                "Loading movement for user:",
                userId
            );



            const movementData =
                await getMovementAnalytics(
                    userId
                );

            console.log(
                "Selected User Movement:",
                movementData
            );

            setMovement(
                movementData
            );

            setMovementAnalytics(
                movementData
            );

            // =========================================
// SELECTED USER AI PREDICTION
// =========================================

console.log(
    "Loading AI prediction for user:",
    userId
);

setPredictionLoading(true);

try {

    const predictionData =
        await getNextPrediction(userId);

    console.log(
        "Selected User AI Prediction:",
        predictionData
    );

    setPrediction(
        predictionData
    );

} catch (err) {

    console.error(
        "Selected user prediction failed:",
        err
    );

    setPrediction(null);

} finally {

    setPredictionLoading(false);
}


        } catch (err) {

            console.error(
                "Selected user details failed:",
                err
            );

            setError(
                err.response?.data?.detail ||
                err.message ||
                "Failed to load user details."
            );

        } finally {

            setLoading(false);

        }

    };


    loadUserDetails();




}, [userId]);





    if (loading) {

        return (
            <div className="
                min-h-screen
                bg-[#050505]
                flex
                items-center
                justify-center
            ">

                <div className="text-gray-400">
                    Loading user details...
                </div>

            </div>
        );

    }


    if (error) {

        return (
            <div className="
                min-h-screen
                bg-[#050505]
                flex
                items-center
                justify-center
            ">

                <div className="
                    rounded-2xl
                    border
                    border-red-500/30
                    bg-red-500/10
                    px-6
                    py-4
                    text-red-300
                ">

                    {error}

                </div>

            </div>
        );

    }


    if (!userDetails) {

        return (
            <div className="
                min-h-screen
                bg-[#050505]
                flex
                items-center
                justify-center
            ">

                <div className="text-gray-400">
                    No user data available.
                </div>

            </div>
        );

    }

    

    const handleEmergencyChange = (e) => {

    const {
        name,
        value
    } = e.target;

    setEmergencyForm(
        previous => ({
            ...previous,
            [name]: value
        })
    );
};


const resetEmergencyForm = () => {

    setEmergencyForm({
        name: "",
        relationship_type: "",
        phone_number: "",
        email: ""
    });

    setEditingEmergencyId(null);

    setShowEmergencyForm(false);
};


const handleAddEmergency = async () => {

    if (!userId) return;

    try {

        setEmergencyLoading(true);
        setEmergencyError(null);

        const newContact =
            await addEmergencyContact(
                userId,
                emergencyForm
            );

        setEmergencyContacts(
            previous => [
                ...previous,
                newContact
            ]
        );

        resetEmergencyForm();

    } catch (err) {

        console.error(
            "Add emergency contact failed:",
            err
        );

        setEmergencyError(
            err.response?.data?.detail ||
            "Failed to add emergency contact."
        );

    } finally {

        setEmergencyLoading(false);

    }
};


const handleEditEmergency = (contact) => {

    setEditingEmergencyId(
        contact.id
    );

    setEmergencyForm({
        name: contact.name || "",
        relationship_type:
            contact.relationship_type || "",
        phone_number:
            contact.phone_number || "",
        email:
            contact.email || ""
    });

    setShowEmergencyForm(true);
};


const handleUpdateEmergency = async () => {

    if (editingEmergencyId === null) return;

    try {

        setEmergencyLoading(true);
        setEmergencyError(null);

        const updatedContact =
            await updateEmergencyContact(
                editingEmergencyId,
                emergencyForm
            );

        setEmergencyContacts(previous =>
            previous.map(contact =>
                contact.id === editingEmergencyId
                    ? updatedContact
                    : contact
            )
        );

        resetEmergencyForm();

    } catch (err) {

        console.error(
            "Update emergency contact failed:",
            err
        );

        setEmergencyError(
            err.response?.data?.detail ||
            "Failed to update emergency contact."
        );

    } finally {

        setEmergencyLoading(false);

    }
};


const handleDeleteEmergency = async (
    contactId
) => {

    const confirmed =
        window.confirm(
            "Delete this emergency contact?"
        );

    if (!confirmed) return;

    try {

        setEmergencyLoading(true);
        setEmergencyError(null);

        await deleteEmergencyContact(
            contactId
        );

        setEmergencyContacts(
            previous =>
                previous.filter(
                    contact =>
                        contact.id !== contactId
                )
        );

    } catch (err) {

        console.error(
            "Delete emergency contact failed:",
            err
        );

        setEmergencyError(
            err.response?.data?.detail ||
            "Failed to delete emergency contact."
        );

    } finally {

        setEmergencyLoading(false);

    }
};


    const location =
        userDetails.location || {};

    
    console.log(
    "FULL LOCATION OBJECT:",
    JSON.stringify(location, null, 2)
);




    
    const searchSafeLocation = async (query) => {

    const searchText = query?.trim();

    if (!searchText || searchText.length < 3) {
        setLocationSuggestions([]);
        setLocationSearchDone(false);
        return;
    }

    try {

        setLocationSearching(true);
        setLocationSearchDone(false);

        console.log(
            "================================="
        );

        console.log(
            "SAFE LOCATION SEARCH:",
            searchText
        );

        const results =
            await searchSafeLocationApi(
                searchText
            );

        console.log(
            "SAFE LOCATION RESULTS:",
            results
        );

        if (Array.isArray(results)) {

            setLocationSuggestions(
                results
            );

        } else {

            setLocationSuggestions([]);

        }

        setLocationSearchDone(true);

    } catch (error) {

        console.error(
            "SAFE LOCATION SEARCH ERROR:",
            error
        );

        setLocationSuggestions([]);
        setLocationSearchDone(true);

    } finally {

        setLocationSearching(false);

    }
};

const handleSafeLocationSuggestionSelect = (
location
) => {

console.log(
    "SELECTED LOCATION:",
    location
);

setSafeLocationForm(
    previous => ({
        ...previous,

        latitude:
            String(
                location.lat ?? ""
            ),

        longitude:
            String(
                location.lon ?? ""
            )
    })
);

setLocationSuggestions([]);
setLocationSearchDone(false);


}; 





    return (

        <>

            <div
                className="
                    fixed
                    inset-0
                    bg-[#050505]
                    -z-10
                "
            />


            <div
                className="
                    fixed
                    top-[-300px]
                    left-1/2
                    -translate-x-1/2
                    w-[900px]
                    h-[900px]
                    bg-blue-600/10
                    blur-[180px]
                    rounded-full
                "
            />


            <div
                className="
                    relative
                    max-w-[1600px]
                    mx-auto
                    px-6
                    py-8
                    space-y-10
                "
            >


                <DashboardHeader
                    user={userDetails}
                    onlineStatus={
                        userDetails.is_online
                            ? "online"
                            : "offline"
                    }
                    onLogout={handleLogout}
                />

             


                <motion.div
                    initial={{
                        opacity: 0,
                        scale: 0.95
                    }}
                    animate={{
                        opacity: 1,
                        scale: 1
                    }}
                    transition={{
                        duration: 0.8
                    }}
                    className="
                        rounded-[40px]
                        p-3
                        bg-white/[0.04]
                        border
                        border-white/10
                        backdrop-blur-3xl
                    "
                >

                   <LiveMap
    latitude={userDetails?.location?.latitude}
    longitude={userDetails?.location?.longitude}
    place={userDetails?.location?.place}
    userName={
        userDetails?.user_name ||
        userDetails?.full_name ||
        "User"
    }
/>

                </motion.div>


                <div
                    className="
                        grid
                        grid-cols-1
                        xl:grid-cols-2
                        gap-6
                    "
                >

                    <AIPredictionCard
    prediction={prediction}
    loading={predictionLoading}
/>

                   <AlertsCard
    alerts={alerts}
    loading={alertsLoading}
/>

                </div>


                {/* USER DETAILS */}

                <div
                    className="
                        rounded-3xl
                        border
                        border-white/10
                        bg-white/[0.04]
                        backdrop-blur-2xl
                        p-6
                    "
                >

                    <h2 className="
                        text-xl
                        font-semibold
                        text-white
                        mb-6
                    ">
                        User Details
                    </h2>


                    <div className="
                        grid
                        grid-cols-1
                        md:grid-cols-2
                        xl:grid-cols-4
                        gap-4
                    ">


                        <div className="
                            rounded-2xl
                            bg-black/30
                            border
                            border-white/10
                            p-4
                        ">

                            <p className="text-xs text-gray-500">
                                User
                            </p>

                            <p className="text-white mt-1">
                                {userDetails.user_name || "Unknown"}
                            </p>

                        </div>


                        <div className="
                            rounded-2xl
                            bg-black/30
                            border
                            border-white/10
                            p-4
                        ">

                            <p className="text-xs text-gray-500">
                                SafePath ID
                            </p>

                            <p className="text-white mt-1">
                                {userDetails.safe_path_id || "Unknown"}
                            </p>

                        </div>


                        <div className="
                            rounded-2xl
                            bg-black/30
                            border
                            border-white/10
                            p-4
                        ">

                            <p className="text-xs text-gray-500">
                                Status
                            </p>

                            <p className={
                                userDetails.is_online
                                    ? "text-green-400 mt-1"
                                    : "text-red-400 mt-1"
                            }>

                                {
                                    userDetails.is_online
                                        ? "Online"
                                        : "Offline"
                                }

                            </p>

                        </div>


                        <div className="
    rounded-2xl
    bg-black/30
    border
    border-white/10
    p-4
">

    <p className="text-xs text-gray-500">
        Last Seen
    </p>

   <p className="text-white mt-1">
    {userDetails.last_seen
        ? new Date(
            userDetails.last_seen + "Z"
        ).toLocaleString("en-IN", {
            timeZone: "Asia/Kolkata",
            day: "2-digit",
            month: "short",
            year: "numeric",
            hour: "2-digit",
            minute: "2-digit",
            second: "2-digit",
            hour12: true
        })
        : "Unknown"
    }
</p>

</div>

                        <div className="
                            rounded-2xl
                            bg-black/30
                            border
                            border-white/10
                            p-4
                            md:col-span-2
                        ">

                            <p className="text-xs text-gray-500">
                                Current Location
                            </p>

                            <p className="text-white mt-1">
                                {location.place ||
                                    "Unknown Location"}
                            </p>

                        </div>


                        <div className="
                            rounded-2xl
                            bg-black/30
                            border
                            border-white/10
                            p-4
                        ">

                            <p className="text-xs text-gray-500">
                                Latitude
                            </p>

                            <p className="text-white mt-1">
                                {location.latitude ?? "Unknown"}
                            </p>

                        </div>


                        <div className="
                            rounded-2xl
                            bg-black/30
                            border
                            border-white/10
                            p-4
                        ">

                            <p className="text-xs text-gray-500">
                                Longitude
                            </p>

                            <p className="text-white mt-1">
                                {location.longitude ?? "Unknown"}
                            </p>

                        </div>

                    </div>

                </div>


                {/* =====================================================
            EMERGENCY CONTACT
         ===================================================== */}

                    <motion.div
                        initial={{
                            opacity: 0,
                            y: 20
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
                            bg-white/[0.04]
                            backdrop-blur-2xl
                            p-6
                        "
                    >

                        <div
                        className="
                            rounded-3xl
                            border
                            border-white/10
                            bg-white/[0.04]
                            backdrop-blur-2xl
                            p-6
                        "
                    >
                        <div className="
                            flex
                            items-center
                            justify-between
                            mb-6
                        ">

                            <div>
                                <h2 className="
                                    text-xl
                                    font-semibold
                                    text-white
                                ">
                                    Emergency Contact
                                </h2>

                                <p className="
                                    text-sm
                                    text-gray-400
                                    mt-1
                                ">
                                    Emergency contact assigned to{" "}
                                    {userDetails?.user_name || "this user"}
                                </p>
                            </div>

                            <button
                                onClick={() => {
                                    setEditingEmergencyId(null);

                                    setEmergencyForm({
                                        name: "",
                                        relationship_type: "",
                                        phone_number: "",
                                        email: ""
                                    });

                                    setEmergencyError(null);
                                    setEmergencyMessage("");
                                    setShowEmergencyForm(true);
                                }}
                                className="
                                    px-4
                                    py-2
                                    rounded-xl
                                    bg-violet-500
                                    hover:bg-violet-600
                                    text-white
                                    text-sm
                                "
                            >
                                + Add Contact
                            </button>

                        </div> 


                        {/* SUCCESS MESSAGE */}

                        {emergencyMessage && (
                            <div className="
                                mb-4
                                rounded-xl
                                border
                                border-green-500/20
                                bg-green-500/10
                                p-3
                                text-sm
                                text-green-300
                            ">
                                {emergencyMessage}
                            </div>
                        )}


                        {/* ERROR MESSAGE */}

                        {emergencyError && (
                            <div className="
                                mb-4
                                rounded-xl
                                border
                                border-red-500/20
                                bg-red-500/10
                                p-3
                                text-sm
                                text-red-300
                            ">
                                {emergencyError}
                            </div>
                        )}


                        {/* ADD / EDIT FORM */}

                        {showEmergencyForm && (
                            <div className="
                                mb-6
                                rounded-2xl
                                border
                                border-white/10
                                bg-black/30
                                p-5
                            ">

                                <h3 className="
                                    text-white
                                    font-semibold
                                    mb-4
                                ">
                                    {editingEmergencyId
                                        ? "Edit Emergency Contact"
                                        : "Add Emergency Contact"}
                                </h3>


                                <div className="
                                    grid
                                    grid-cols-1
                                    md:grid-cols-2
                                    gap-4
                                ">

                                    <input
                                        type="text"
                                        placeholder="Name"
                                        value={emergencyForm.name}
                                        onChange={(e) =>
                                            setEmergencyForm({
                                                ...emergencyForm,
                                                name: e.target.value
                                            })
                                        }
                                        className="
                                            rounded-xl
                                            border
                                            border-white/10
                                            bg-white/5
                                            px-4
                                            py-3
                                            text-white
                                            placeholder-gray-500
                                            outline-none
                                        "
                                    />


                                    <input
                                        type="text"
                                        placeholder="Relationship"
                                        value={
                                            emergencyForm.relationship_type
                                        }
                                        onChange={(e) =>
                                            setEmergencyForm({
                                                ...emergencyForm,
                                                relationship_type:
                                                    e.target.value
                                            })
                                        }
                                        className="
                                            rounded-xl
                                            border
                                            border-white/10
                                            bg-white/5
                                            px-4
                                            py-3
                                            text-white
                                            placeholder-gray-500
                                            outline-none
                                        "
                                    />


                                    <input
                                        type="text"
                                        placeholder="Phone Number"
                                        value={
                                            emergencyForm.phone_number
                                        }
                                        onChange={(e) =>
                                            setEmergencyForm({
                                                ...emergencyForm,
                                                phone_number:
                                                    e.target.value
                                            })
                                        }
                                        className="
                                            rounded-xl
                                            border
                                            border-white/10
                                            bg-white/5
                                            px-4
                                            py-3
                                            text-white
                                            placeholder-gray-500
                                            outline-none
                                        "
                                    />


                                    <input
                                        type="email"
                                        placeholder="Email"
                                        value={emergencyForm.email}
                                        onChange={(e) =>
                                            setEmergencyForm({
                                                ...emergencyForm,
                                                email: e.target.value
                                            })
                                        }
                                        className="
                                            rounded-xl
                                            border
                                            border-white/10
                                            bg-white/5
                                            px-4
                                            py-3
                                            text-white
                                            placeholder-gray-500
                                            outline-none
                                        "
                                    />

                                </div>


                                <div className="
                                    flex
                                    gap-3
                                    mt-5
                                ">

                                    <button
                                    onClick={() => {

                                            if (editingEmergencyId !== null) {
                                                handleUpdateEmergency();
                                            } else {
                                                handleAddEmergency();
                                            }

                                        }}
                                        disabled={emergencyLoading}
                                        className="
                                            px-5
                                            py-2
                                            rounded-xl
                                            bg-violet-500
                                            hover:bg-violet-600
                                            disabled:opacity-50
                                            text-white
                                            text-sm
                                        "
                                    >
                                        {emergencyLoading
                                            ? "Saving..."
                                            : editingEmergencyId
                                                ? "Update Contact"
                                                : "Save Contact"}
                                    </button>


                                    <button
                                        onClick={() => {
                                            setShowEmergencyForm(false);
                                            setEditingEmergencyId(null);
                                        }}
                                        className="
                                            px-5
                                            py-2
                                            rounded-xl
                                            bg-white/5
                                            border
                                            border-white/10
                                            text-gray-300
                                            text-sm
                                        "
                                    >
                                        Cancel
                                    </button>

                                </div>

                            </div>
                        )}


                        {/* CONTACT LIST */}

                        {emergencyContacts.length === 0 ? (

                            <div className="
                                rounded-2xl
                                border
                                border-white/10
                                bg-black/20
                                p-8
                                text-center
                            ">
                                <p className="text-gray-400">
                                    No emergency contact assigned yet.
                                </p>
                            </div>

                        ) : (

                            <div className="
                                grid
                                grid-cols-1
                                md:grid-cols-2
                                gap-4
                            ">

                                {emergencyContacts.map((contact) => (

                                    <div
                                        key={contact.id}
                                        className="
                                            rounded-2xl
                                            border
                                            border-white/10
                                            bg-black/30
                                            p-5
                                        "
                                    >

                                        <div className="
                                            flex
                                            items-start
                                            justify-between
                                            gap-4
                                        ">

                                            <div>

                                                <p className="
                                                    text-white
                                                    font-semibold
                                                    text-lg
                                                ">
                                                    {contact.name || "Unknown"}
                                                </p>

                                                <p className="
                                                    text-violet-400
                                                    text-sm
                                                    mt-1
                                                ">
                                                    {contact.relationship_type ||
                                                        "Relationship not specified"}
                                                </p>

                                            </div>

                                            <span className="
                                                text-red-400
                                                text-xl
                                            ">
                                                🆘
                                            </span>

                                        </div>


                                        <div className="
                                            mt-5
                                            space-y-2
                                            text-sm
                                        ">

                                            <p className="text-gray-300">
                                                <span className="text-gray-500">
                                                    Phone:
                                                </span>{" "}
                                                {contact.phone_number ||
                                                    "Not provided"}
                                            </p>

                                            <p className="text-gray-300">
                                                <span className="text-gray-500">
                                                    Email:
                                                </span>{" "}
                                                {contact.email ||
                                                    "Not provided"}
                                            </p>

                                        </div>


                                        {/* GUARDIAN ONLY ACTIONS */}

                                        <div className="
                                            mt-5
                                            flex
                                            gap-3
                                        ">

                                            <button
                                                type="button"
                                                onClick={() => handleEditEmergency(contact)}
                                                className="
                                                    px-4
                                                    py-2
                                                    rounded-xl
                                                    border
                                                    border-white/10
                                                    bg-white/5
                                                    hover:bg-white/10
                                                    text-gray-200
                                                    text-xs
                                                "
                                            >
                                                Edit
                                            </button>


                                            <button
                                                onClick={() =>
                                                    handleDeleteEmergency(contact.id)
                                                }
                                                className="
                                                    px-4
                                                    py-2
                                                    rounded-xl
                                                    border
                                                    border-red-500/20
                                                    bg-red-500/10
                                                    hover:bg-red-500/20
                                                    text-red-300
                                                    text-xs
                                                "
                                            >
                                                Delete
                                            </button>

                                        </div>

                                    </div>

                                ))}

                            </div>

                        )}

                    </div>




                            {/* ADD / EDIT FORM */}

                            {showEmergencyForm && (

                                <div className="
                                    mt-6
                                    rounded-2xl
                                    border
                                    border-white/10
                                    bg-black/30
                                    p-6
                                ">

                                    <h3 className="
                                        text-white
                                        font-semibold
                                        mb-5
                                    ">
                                        {editingEmergencyId
                                            ? "Edit Emergency Contact"
                                            : "Add Emergency Contact"}
                                    </h3>


                                    <div className="
                                        grid
                                        grid-cols-1
                                        md:grid-cols-2
                                        gap-4
                                    ">

                                        <input
                                            type="text"
                                            name="name"
                                            value={emergencyForm.name}
                                            onChange={handleEmergencyChange}
                                            placeholder="Contact Name"
                                            className="
                                                w-full
                                                rounded-xl
                                                border
                                                border-white/10
                                                bg-black/30
                                                px-4
                                                py-3
                                                text-white
                                                outline-none
                                            "
                                        />


                                        <input
                                            name="relationship_type"
                                            value={
                                                emergencyForm.relationship_type
                                            }
                                            onChange={
                                                handleEmergencyChange
                                            }
                                            placeholder="Relationship"
                                            className="
                                                w-full
                                                rounded-xl
                                                bg-white/5
                                                border
                                                border-white/10
                                                px-4
                                                py-3
                                                text-white
                                                placeholder-gray-500
                                                outline-none
                                            "
                                        />


                                        <input
                                            name="phone_number"
                                            value={
                                                emergencyForm.phone_number
                                            }
                                            onChange={
                                                handleEmergencyChange
                                            }
                                            placeholder="Phone number"
                                            className="
                                                w-full
                                                rounded-xl
                                                bg-white/5
                                                border
                                                border-white/10
                                                px-4
                                                py-3
                                                text-white
                                                placeholder-gray-500
                                                outline-none
                                            "
                                        />


                                        <input
                                            name="email"
                                            type="email"
                                            value={emergencyForm.email}
                                            onChange={
                                                handleEmergencyChange
                                            }
                                            placeholder="Email"
                                            className="
                                                w-full
                                                rounded-xl
                                                bg-white/5
                                                border
                                                border-white/10
                                                px-4
                                                py-3
                                                text-white
                                                placeholder-gray-500
                                                outline-none
                                            "
                                        />

                                    </div>


                                    <div className="
                                        mt-5
                                        flex
                                        gap-3
                                    ">

                                            <button
                                                type="button"
                                                onClick={
                                                    editingEmergencyId
                                                        ? handleUpdateEmergency
                                                        : handleAddEmergency
                                                }
                                                disabled={emergencyLoading}
                                                className="
                                                    px-5
                                                    py-3
                                                    rounded-xl
                                                    bg-violet-500
                                                    hover:bg-violet-600
                                                    text-white
                                                    text-sm
                                                    font-medium
                                                "
                                            >
                                                {emergencyLoading
                                                    ? "Saving..."
                                                    : editingEmergencyId
                                                        ? "Update Contact"
                                                        : "Add Contact"}
                                            </button>


                                        <button
                                            onClick={
                                                resetEmergencyForm
                                            }
                                            className="
                                                px-5
                                                py-2
                                                rounded-xl
                                                bg-white/5
                                                hover:bg-white/10
                                                border
                                                border-white/10
                                                text-gray-300
                                                text-sm
                                            "
                                        >
                                            Cancel
                                        </button>

                                    </div>

                                </div>

                            )}

                        </motion.div> 
                                        


                     

                {/* MOVEMENT ANALYSIS */}

                <div
                    className="
                        rounded-3xl
                        border
                        border-white/10
                        bg-white/[0.04]
                        backdrop-blur-2xl
                        p-6
                    "
                >

                    <div className="mb-6">

                        <h2 className="
                            text-xl
                            font-semibold
                            text-white
                        ">
                            Movement Analysis
                        </h2>

                        <p className="
                            text-sm
                            text-gray-400
                            mt-1
                        ">

                            Movement history for{" "}

                            {userDetails.user_name ||
                                "this user"}

                        </p>

                    </div>


                    {movementError && (

                        <div className="
                            mb-4
                            rounded-xl
                            border
                            border-red-500/20
                            bg-red-500/10
                            p-3
                            text-sm
                            text-red-300
                        ">

                            {movementError}

                        </div>

                    )}

                   <SingleUserMovementChart
                            movement={movement}
                            user={userDetails}
                            />

                </div>

 {/* ==========================================================
SAFE ZONES
========================================================== */}

<div className="flex items-center justify-between mb-4">

    <div>
        <h2 className="text-lg font-semibold text-white">
            Safe Zones
        </h2>

        <p className="text-sm text-gray-400">
            Manage safe locations for the selected user
        </p>
    </div>

    <button
        onClick={() => {
            setEditingSafeLocationId(null);

            setSafeLocationForm({
                location_name: "",
                latitude: "",
                longitude: "",
                radius: "100"
            });

            setSafeLocationError(null);
            setShowSafeLocationForm(true);
        }}
        disabled={!selectedUserData?.user_id}
        className="
            rounded-xl
            bg-violet-600
            px-4
            py-2
            text-sm
            font-medium
            text-white
            hover:bg-violet-500
            disabled:cursor-not-allowed
            disabled:opacity-40
        "
    >
        + Add Safe Zone
    </button>

</div>


{/* ERROR */}

{safeLocationError && (

    <div className="
        mb-4
        rounded-xl
        border
        border-red-500/20
        bg-red-500/10
        px-4
        py-3
        text-sm
        text-red-400
    ">
        {safeLocationError}
    </div>

)}


{/* ADD / EDIT FORM */}

{showSafeLocationForm && (

    <div className="
        mb-5
        rounded-2xl
        border
        border-white/10
        bg-white/[0.03]
        p-5
    ">

        <div className="mb-4">

            <h3 className="
                text-base
                font-semibold
                text-white
            ">
                {editingSafeLocationId
                    ? "Edit Safe Zone"
                    : "Add Safe Zone"}
            </h3>

        </div>


        {/* LOCATION NAME */}

        <div className="mb-4 relative">

    <label className="
        mb-2
        block
        text-xs
        text-gray-400
    ">
        Location Name
    </label>

  <input
    type="text"
    name="location_name"
    value={safeLocationForm.location_name}
   onChange={(e) => {

    const value = e.target.value;

    handleSafeLocationChange(e);

    if (safeLocationSearchTimer.current) {

        clearTimeout(
            safeLocationSearchTimer.current
        );

    }

    safeLocationSearchTimer.current =
        setTimeout(() => {

            searchSafeLocation(value);

        }, 600);

}}
    placeholder="Search location or enter full address"
    className="
        w-full
        rounded-xl
        border
        border-white/10
        bg-black/30
        px-4
        py-3
        text-sm
        text-white
        outline-none
        focus:border-violet-500/50
    "
/>

<button
    type="button"
    onClick={() => {
        setShowLocationPicker(true);
    }}
    className="
        mt-3
        w-full
        rounded-xl
        border
        border-violet-500/30
        bg-violet-500/10
        px-4
        py-3
        text-sm
        font-medium
        text-violet-300
        transition
        hover:bg-violet-500/20
    "
>
    📍 Select Exact Location on Map
</button>

    {/* SEARCHING */}

{locationSearching && (
    <div className="
        mt-2
        rounded-xl
        border
        border-white/10
        bg-black/30
        px-4
        py-3
        text-xs
        text-gray-400
    ">
        Searching locations...
    </div>
)}

{!locationSearching &&
 locationSuggestions.length > 0 && (

    <div className="
        mt-2
        max-h-64
        overflow-y-auto
        rounded-xl
        border
        border-white/10
        bg-black
        shadow-xl
        z-50
    ">

        {locationSuggestions.map((location) => (

            <button
                key={location.place_id}
                type="button"
                onClick={() => {

                    console.log(
                        "SELECTED SAFE LOCATION:",
                        location
                    );

                    setSafeLocationForm(previous => ({
                        ...previous,

                        location_name:
                            location.display_name,

                        latitude:
                            String(location.lat),

                        longitude:
                            String(location.lon)
                    }));

                    setLocationSuggestions([]);
                    setLocationSearchDone(false);

                }}
                className="
                    w-full
                    px-4
                    py-3
                    text-left
                    text-sm
                    text-gray-300
                    hover:bg-white/10
                "
            >
                {location.display_name}
            </button>

        ))}

    </div>
)}

{!locationSearching &&
 locationSearchDone &&
 locationSuggestions.length === 0 && (

    <div className="
        mt-2
        text-xs
        text-gray-500
    ">
        Location not found. You can enter latitude and longitude manually.
    </div>
)}


    {/* SUGGESTIONS */}

    {!locationSearching &&
        locationSuggestions.length > 0 && (

        <div className="
            absolute
            left-0
            right-0
            top-full
            z-50
            mt-2
            overflow-hidden
            rounded-xl
            border
            border-white/10
            bg-[#15151c]
            shadow-2xl
        ">

            {locationSuggestions.map(
                (location, index) => (

                <button
                    type="button"
                    key={
                        location.place_id ||
                        index
                    }
                    onClick={() => {

                        setSafeLocationForm(
                            previous => ({
                                ...previous,

                                location_name:
                                    location.display_name,

                                latitude:
                                    String(
                                        location.lat
                                    ),

                                longitude:
                                    String(
                                        location.lon
                                    )
                            })
                        );

                        setLocationSuggestions(
                            []
                        );

                        setLocationSearchDone(
                            false
                        );

                    }}
                    className="
                        block
                        w-full
                        border-b
                        border-white/5
                        px-4
                        py-3
                        text-left
                        hover:bg-white/5
                    "
                >

                    <div className="
                        flex
                        gap-3
                    ">

                        <span className="
                            mt-0.5
                            text-violet-400
                        ">
                            📍
                        </span>

                        <div>

                            <p className="
                                text-sm
                                text-white
                            ">
                                {
                                    location.name ||
                                    location.display_name
                                }
                            </p>

                            <p className="
                                mt-1
                                text-xs
                                text-gray-500
                            ">
                                {
                                    location.display_name
                                }
                            </p>

                        </div>

                    </div>

                </button>

            ))}

        </div>

    )}


    {/* NOT FOUND */}

    {!locationSearching &&
        locationSearchDone &&
        locationSuggestions.length === 0 && (

        <div className="
            absolute
            left-0
            right-0
            top-full
            z-50
            mt-2
            rounded-xl
            border
            border-white/10
            bg-[#15151c]
            px-4
            py-3
        ">

            <p className="
                text-xs
                text-gray-400
            ">
                Location not found.
            </p>

            <p className="
                mt-1
                text-[11px]
                text-gray-500
            ">
                You can enter latitude and longitude manually below.
            </p>

        </div>

    )}

</div>


        <div className="
            grid
            grid-cols-1
            gap-4
            md:grid-cols-2
        ">

            {/* LATITUDE */}

            <div>

                <label className="
                    mb-2
                    block
                    text-xs
                    text-gray-400
                ">
                    Latitude
                </label>

                <input
                    type="number"
                    step="any"
                    name="latitude"
                    value={
                        safeLocationForm.latitude
                    }
                    onChange={
                        handleSafeLocationChange
                    }
                    placeholder="17.3850"
                    className="
                        w-full
                        rounded-xl
                        border
                        border-white/10
                        bg-black/30
                        px-4
                        py-3
                        text-sm
                        text-white
                        outline-none
                        focus:border-violet-500/50
                    "
                />

            </div>


            {/* LONGITUDE */}

            <div>

                <label className="
                    mb-2
                    block
                    text-xs
                    text-gray-400
                ">
                    Longitude
                </label>

                <input
                    type="number"
                    step="any"
                    name="longitude"
                    value={
                        safeLocationForm.longitude
                    }
                    onChange={
                        handleSafeLocationChange
                    }
                    placeholder="78.4867"
                    className="
                        w-full
                        rounded-xl
                        border
                        border-white/10
                        bg-black/30
                        px-4
                        py-3
                        text-sm
                        text-white
                        outline-none
                        focus:border-violet-500/50
                    "
                />

            </div>

        </div>


        {/* RADIUS */}

        <div className="mt-4">

            <label className="
                mb-2
                block
                text-xs
                text-gray-400
            ">
                Radius (meters)
            </label>

            <input
                type="number"
                min="1"
                name="radius"
                value={
                    safeLocationForm.radius
                }
                onChange={
                    handleSafeLocationChange
                }
                placeholder="100"
                className="
                    w-full
                    rounded-xl
                    border
                    border-white/10
                    bg-black/30
                    px-4
                    py-3
                    text-sm
                    text-white
                    outline-none
                    focus:border-violet-500/50
                "
            />

        </div>


        {/* ACTIONS */}

        <div className="
            mt-5
            flex
            justify-end
            gap-3
        ">

            <button
                onClick={
                    resetSafeLocationForm
                }
                className="
                    rounded-xl
                    border
                    border-white/10
                    px-4
                    py-2
                    text-sm
                    text-gray-300
                    hover:bg-white/5
                "
            >
                Cancel
            </button>


            <button
                onClick={
                    editingSafeLocationId
                        ? handleUpdateSafeLocation
                        : handleAddSafeLocation
                }
                disabled={safeLocationLoading}
                className="
                    rounded-xl
                    bg-violet-600
                    px-5
                    py-2
                    text-sm
                    font-medium
                    text-white
                    hover:bg-violet-500
                    disabled:opacity-50
                "
            >
                {safeLocationLoading
                    ? "Saving..."
                    : editingSafeLocationId
                        ? "Update Safe Zone"
                        : "Add Safe Zone"}
            </button>

        </div>

    </div>

)}


{/* LOADING */}

{safeLocationLoading &&
    !showSafeLocationForm && (

    <div className="
        py-8
        text-center
        text-sm
        text-gray-400
    ">
        Loading safe zones...
    </div>

)}


{/* NO SAFE ZONES */}

{!safeLocationLoading &&
    safeLocations.length === 0 &&
    !showSafeLocationForm && (

    <div className="
        rounded-2xl
        border
        border-white/10
        bg-white/[0.03]
        px-5
        py-8
        text-center
    ">

        <p className="
            text-sm
            text-gray-400
        ">
            No safe zones configured for this user.
        </p>

    </div>

)}


{/* SAFE ZONE LIST */}

{safeLocations.length > 0 && (

    <div className="
        grid
        grid-cols-1
        gap-4
        lg:grid-cols-2
    ">

        {safeLocations.map(
            (location) => (

                <div
                    key={location.id}
                    className="
                        rounded-2xl
                        border
                        border-white/10
                        bg-white/[0.03]
                        p-5
                    "
                >

                    <div className="
                        flex
                        items-start
                        justify-between
                        gap-4
                    ">

                        <div>

                            <h3 className="
                                text-base
                                font-semibold
                                text-white
                            ">
                                {location.location_name}
                            </h3>

                            <p className="
                                mt-1
                                text-xs
                                text-gray-400
                            ">
                                Radius: {location.radius} meters
                            </p>

                        </div>


                        <div className="
                            flex
                            gap-2
                        ">

                            <button
                                onClick={() =>
                                    handleEditSafeLocation(
                                        location
                                    )
                                }
                                className="
                                    rounded-lg
                                    border
                                    border-white/10
                                    px-3
                                    py-1.5
                                    text-xs
                                    text-gray-300
                                    hover:bg-white/5
                                "
                            >
                                Edit
                            </button>

                            <button
                                onClick={() =>
                                    handleDeleteSafeLocation(
                                        location.id
                                    )
                                }
                                className="
                                    rounded-lg
                                    border
                                    border-red-500/20
                                    px-3
                                    py-1.5
                                    text-xs
                                    text-red-400
                                    hover:bg-red-500/10
                                "
                            >
                                Delete
                            </button>

                        </div>

                    </div>


                    <div className="
                        mt-4
                        grid
                        grid-cols-2
                        gap-3
                    ">

                        <div className="
                            rounded-xl
                            bg-black/20
                            p-3
                        ">

                            <p className="
                                text-[11px]
                                text-gray-500
                            ">
                                Latitude
                            </p>

                            <p className="
                                mt-1
                                text-sm
                                text-gray-300
                            ">
                                {location.latitude}
                            </p>

                        </div>


                        <div className="
                            rounded-xl
                            bg-black/20
                            p-3
                        ">

                            <p className="
                                text-[11px]
                                text-gray-500
                            ">
                                Longitude
                            </p>

                            <p className="
                                mt-1
                                text-sm
                                text-gray-300
                            ">
                                {location.longitude}
                            </p>

                        </div>

                    </div>

                </div>

            )
        )}

    </div>

)}


 {showLocationPicker && (

    <LocationPicker
        initialLatitude={
            safeLocationForm.latitude
        }

        initialLongitude={
            safeLocationForm.longitude
        }

        onClose={() => {
            setShowLocationPicker(false);
        }}

        onConfirm={(
            latitude,
            longitude
        ) => {

            setSafeLocationForm(
                previous => ({
                    ...previous,

                    latitude:
                        String(latitude),

                    longitude:
                        String(longitude)
                })
            );

            setShowLocationPicker(false);

        }}
    />

)}






                {/* <SafeLocationCard
                    safeLocations={safeLocations}
                    loading={safeLocationLoading}
                /> */}


            </div>

        </>

    );

}