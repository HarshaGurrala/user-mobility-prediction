import { motion } from "framer-motion";
import {
    FiX,
    FiUser,
    FiMail,
    FiUsers,
    FiBriefcase,
} from "react-icons/fi";
import { useEffect, useState } from "react";
import { getGuardianTeam } from "../../services/guardianApi";

export default function GuardianTeam({ onClose }) {
    const [team, setTeam] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadTeam = async () => {
            try {
                setLoading(true);
                setError("");

                const data = await getGuardianTeam();

                setTeam(
                    Array.isArray(data)
                        ? data
                        : []
                );
            } catch (err) {
                console.error(
                    "Guardian team error:",
                    err
                );

                setError(
                    err.response?.data?.detail ||
                    "Failed to load guardian team."
                );
            } finally {
                setLoading(false);
            }
        };

        loadTeam();
    }, []);

    const hod = team.find(
        (person) =>
            person.person_type?.toUpperCase() === "HOD"
    );

    const mentor = team.find(
        (person) =>
            person.person_type?.toUpperCase() === "MENTOR"
    );

    const members = team.filter(
        (person) =>
            person.person_type?.toUpperCase() === "MEMBER"
    );

    const getImage = (person) => {
        if (!person?.image) {
            return null;
        }

        if (
            person.image.startsWith("http://") ||
            person.image.startsWith("https://")
        ) {
            return person.image;
        }

        return `https://user-mobility-prediction.onrender.com${person.image}`;
    };

    const PersonCard = ({ person, type }) => {
        if (!person) {
            return (
                <div className="
                    rounded-2xl
                    border
                    border-white/10
                    bg-black/20
                    p-4
                ">
                    <div className="
                        flex
                        items-center
                        gap-3
                    ">
                        <div className="
                            h-12
                            w-12
                            rounded-2xl
                            bg-blue-500/10
                            flex
                            items-center
                            justify-center
                        ">
                            <FiUser className="
                                text-gray-500
                                text-xl
                            " />
                        </div>

                        <div>
                            <p className="
                                text-xs
                                text-gray-500
                            ">
                                {type}
                            </p>

                            <p className="
                                text-sm
                                text-gray-500
                            ">
                                Not added yet
                            </p>
                        </div>
                    </div>
                </div>
            );
        }

        const image = getImage(person);

        return (
            <div className="
                rounded-2xl
                border
                border-white/10
                bg-black/30
                p-4
            ">
                <div className="
                    flex
                    items-start
                    gap-3
                ">
                    <div className="
                        h-12
                        w-12
                        shrink-0
                        rounded-2xl
                        overflow-hidden
                        bg-blue-500/20
                        flex
                        items-center
                        justify-center
                    ">
                        {image ? (
                            <img
                                src={image}
                                alt={person.name}
                                className="
                                    h-full
                                    w-full
                                    object-cover
                                "
                            />
                        ) : (
                            <FiUser className="
                                text-white
                                text-xl
                            " />
                        )}
                    </div>

                    <div className="min-w-0">
                        <p className="
                            text-xs
                            text-blue-300
                            uppercase
                            tracking-wide
                        ">
                            {type}
                        </p>

                        <p className="
                            mt-1
                            text-white
                            font-semibold
                            truncate
                        ">
                            {person.name}
                        </p>

                        {person.email && (
                            <div className="
                                mt-1
                                flex
                                items-center
                                gap-2
                            ">
                                <FiMail className="
                                    text-gray-500
                                    text-xs
                                " />

                                <p className="
                                    text-xs
                                    text-gray-400
                                    truncate
                                ">
                                    {person.email}
                                </p>
                            </div>
                        )}

                        {person.role && (
                            <p className="
                                mt-1
                                text-xs
                                text-gray-500
                            ">
                                {person.role}
                            </p>
                        )}
                    </div>
                </div>

                {person.details && (
                    <p className="
                        mt-3
                        border-t
                        border-white/5
                        pt-3
                        text-xs
                        leading-5
                        text-gray-400
                    ">
                        {person.details}
                    </p>
                )}
            </div>
        );
    };

    return (
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
            onClick={onClose}
        >
            <motion.div
                initial={{
                    opacity: 0,
                    scale: 0.95,
                    y: 20,
                }}
                animate={{
                    opacity: 1,
                    scale: 1,
                    y: 0,
                }}
                transition={{
                    duration: 0.25,
                }}
                onClick={(event) =>
                    event.stopPropagation()
                }
                className="
                    relative
                    w-full
                    max-w-3xl
                    max-h-[90vh]
                    overflow-y-auto
                    rounded-3xl
                    border
                    border-white/10
                    bg-[#111827]
                    p-6
                    shadow-[0_0_60px_rgba(59,130,246,.25)]
                "
            >
                <button
                    type="button"
                    onClick={onClose}
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

                <div className="pr-12">
                    <div className="
                        flex
                        items-center
                        gap-3
                    ">
                        <div className="
                            h-12
                            w-12
                            rounded-2xl
                            bg-gradient-to-br
                            from-blue-500/30
                            to-violet-500/30
                            flex
                            items-center
                            justify-center
                        ">
                            <FiUsers className="
                                text-blue-300
                                text-xl
                            " />
                        </div>

                        <div>
                            <h2 className="
                                text-xl
                                font-semibold
                                text-white
                            ">
                                Guardian Team
                            </h2>

                            <p className="
                                text-xs
                                text-gray-400
                            ">
                                HOD, Mentor and Team Members
                            </p>
                        </div>
                    </div>
                </div>

                {loading && (
                    <div className="
                        py-12
                        text-center
                    ">
                        <p className="
                            text-sm
                            text-gray-400
                        ">
                            Loading team information...
                        </p>
                    </div>
                )}

                {!loading && error && (
                    <div className="
                        mt-6
                        rounded-2xl
                        border
                        border-red-500/20
                        bg-red-500/10
                        p-4
                    ">
                        <p className="
                            text-sm
                            text-red-300
                        ">
                            {error}
                        </p>
                    </div>
                )}

                {!loading && !error && (
                    <div className="mt-6 space-y-6">

                        {/* HOD */}
                        <section>
                            <div className="
                                mb-3
                                flex
                                items-center
                                gap-2
                            ">
                                <FiBriefcase className="
                                    text-blue-300
                                " />

                                <h3 className="
                                    text-sm
                                    font-semibold
                                    text-white
                                ">
                                    HOD
                                </h3>
                            </div>

                            <PersonCard
                                person={hod}
                                type="HOD"
                            />
                        </section>

                        {/* MENTOR */}
                        <section>
                            <div className="
                                mb-3
                                flex
                                items-center
                                gap-2
                            ">
                                <FiUser className="
                                    text-violet-300
                                " />

                                <h3 className="
                                    text-sm
                                    font-semibold
                                    text-white
                                ">
                                    Mentor
                                </h3>
                            </div>

                            <PersonCard
                                person={mentor}
                                type="MENTOR"
                            />
                        </section>

                        {/* TEAM MEMBERS */}
                        <section>
                            <div className="
                                mb-3
                                flex
                                items-center
                                justify-between
                            ">
                                <div className="
                                    flex
                                    items-center
                                    gap-2
                                ">
                                    <FiUsers className="
                                        text-green-300
                                    " />

                                    <h3 className="
                                        text-sm
                                        font-semibold
                                        text-white
                                    ">
                                        Team Members
                                    </h3>
                                </div>

                                <span className="
                                    text-xs
                                    text-gray-500
                                ">
                                    {members.length} Members
                                </span>
                            </div>

                            {members.length === 0 ? (
                                <PersonCard
                                    type="MEMBER"
                                />
                            ) : (
                                <div className="
                                    grid
                                    grid-cols-1
                                    sm:grid-cols-2
                                    gap-3
                                ">
                                    {members
                                        .slice(0, 4)
                                        .map((member) => (
                                            <PersonCard
                                                key={member.id}
                                                person={member}
                                                type={
                                                    member.roll_no
                                                        ? `MEMBER • ${member.roll_no}`
                                                        : "MEMBER"
                                                }
                                            />
                                        ))}
                                </div>
                            )}
                        </section>
                    </div>
                )}
            </motion.div>
        </div>
    );
}
EOFcat > frontend/src/components/guardian/GuardianTeam.jsx <<'EOF'
import { motion } from "framer-motion";
import {
    FiX,
    FiUser,
    FiMail,
    FiUsers,
    FiBriefcase,
} from "react-icons/fi";
import { useEffect, useState } from "react";
import { getGuardianTeam } from "../../services/guardianApi";

export default function GuardianTeam({ onClose }) {
    const [team, setTeam] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadTeam = async () => {
            try {
                setLoading(true);
                setError("");

                const data = await getGuardianTeam();

                setTeam(
                    Array.isArray(data)
                        ? data
                        : []
                );
            } catch (err) {
                console.error(
                    "Guardian team error:",
                    err
                );

                setError(
                    err.response?.data?.detail ||
                    "Failed to load guardian team."
                );
            } finally {
                setLoading(false);
            }
        };

        loadTeam();
    }, []);

    const hod = team.find(
        (person) =>
            person.person_type?.toUpperCase() === "HOD"
    );

    const mentor = team.find(
        (person) =>
            person.person_type?.toUpperCase() === "MENTOR"
    );

    const members = team.filter(
        (person) =>
            person.person_type?.toUpperCase() === "MEMBER"
    );

    const getImage = (person) => {
        if (!person?.image) {
            return null;
        }

        if (
            person.image.startsWith("http://") ||
            person.image.startsWith("https://")
        ) {
            return person.image;
        }

        return `https://user-mobility-prediction.onrender.com${person.image}`;
    };

    const PersonCard = ({ person, type }) => {
        if (!person) {
            return (
                <div className="
                    rounded-2xl
                    border
                    border-white/10
                    bg-black/20
                    p-4
                ">
                    <div className="
                        flex
                        items-center
                        gap-3
                    ">
                        <div className="
                            h-12
                            w-12
                            rounded-2xl
                            bg-blue-500/10
                            flex
                            items-center
                            justify-center
                        ">
                            <FiUser className="
                                text-gray-500
                                text-xl
                            " />
                        </div>

                        <div>
                            <p className="
                                text-xs
                                text-gray-500
                            ">
                                {type}
                            </p>

                            <p className="
                                text-sm
                                text-gray-500
                            ">
                                Not added yet
                            </p>
                        </div>
                    </div>
                </div>
            );
        }

        const image = getImage(person);

        return (
            <div className="
                rounded-2xl
                border
                border-white/10
                bg-black/30
                p-4
            ">
                <div className="
                    flex
                    items-start
                    gap-3
                ">
                    <div className="
                        h-12
                        w-12
                        shrink-0
                        rounded-2xl
                        overflow-hidden
                        bg-blue-500/20
                        flex
                        items-center
                        justify-center
                    ">
                        {image ? (
                            <img
                                src={image}
                                alt={person.name}
                                className="
                                    h-full
                                    w-full
                                    object-cover
                                "
                            />
                        ) : (
                            <FiUser className="
                                text-white
                                text-xl
                            " />
                        )}
                    </div>

                    <div className="min-w-0">
                        <p className="
                            text-xs
                            text-blue-300
                            uppercase
                            tracking-wide
                        ">
                            {type}
                        </p>

                        <p className="
                            mt-1
                            text-white
                            font-semibold
                            truncate
                        ">
                            {person.name}
                        </p>

                        {person.email && (
                            <div className="
                                mt-1
                                flex
                                items-center
                                gap-2
                            ">
                                <FiMail className="
                                    text-gray-500
                                    text-xs
                                " />

                                <p className="
                                    text-xs
                                    text-gray-400
                                    truncate
                                ">
                                    {person.email}
                                </p>
                            </div>
                        )}

                        {person.role && (
                            <p className="
                                mt-1
                                text-xs
                                text-gray-500
                            ">
                                {person.role}
                            </p>
                        )}
                    </div>
                </div>

                {person.details && (
                    <p className="
                        mt-3
                        border-t
                        border-white/5
                        pt-3
                        text-xs
                        leading-5
                        text-gray-400
                    ">
                        {person.details}
                    </p>
                )}
            </div>
        );
    };

    return (
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
            onClick={onClose}
        >
            <motion.div
                initial={{
                    opacity: 0,
                    scale: 0.95,
                    y: 20,
                }}
                animate={{
                    opacity: 1,
                    scale: 1,
                    y: 0,
                }}
                transition={{
                    duration: 0.25,
                }}
                onClick={(event) =>
                    event.stopPropagation()
                }
                className="
                    relative
                    w-full
                    max-w-3xl
                    max-h-[90vh]
                    overflow-y-auto
                    rounded-3xl
                    border
                    border-white/10
                    bg-[#111827]
                    p-6
                    shadow-[0_0_60px_rgba(59,130,246,.25)]
                "
            >
                <button
                    type="button"
                    onClick={onClose}
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

                <div className="pr-12">
                    <div className="
                        flex
                        items-center
                        gap-3
                    ">
                        <div className="
                            h-12
                            w-12
                            rounded-2xl
                            bg-gradient-to-br
                            from-blue-500/30
                            to-violet-500/30
                            flex
                            items-center
                            justify-center
                        ">
                            <FiUsers className="
                                text-blue-300
                                text-xl
                            " />
                        </div>

                        <div>
                            <h2 className="
                                text-xl
                                font-semibold
                                text-white
                            ">
                                Guardian Team
                            </h2>

                            <p className="
                                text-xs
                                text-gray-400
                            ">
                                HOD, Mentor and Team Members
                            </p>
                        </div>
                    </div>
                </div>

                {loading && (
                    <div className="
                        py-12
                        text-center
                    ">
                        <p className="
                            text-sm
                            text-gray-400
                        ">
                            Loading team information...
                        </p>
                    </div>
                )}

                {!loading && error && (
                    <div className="
                        mt-6
                        rounded-2xl
                        border
                        border-red-500/20
                        bg-red-500/10
                        p-4
                    ">
                        <p className="
                            text-sm
                            text-red-300
                        ">
                            {error}
                        </p>
                    </div>
                )}

                {!loading && !error && (
                    <div className="mt-6 space-y-6">

                        {/* HOD */}
                        <section>
                            <div className="
                                mb-3
                                flex
                                items-center
                                gap-2
                            ">
                                <FiBriefcase className="
                                    text-blue-300
                                " />

                                <h3 className="
                                    text-sm
                                    font-semibold
                                    text-white
                                ">
                                    HOD
                                </h3>
                            </div>

                            <PersonCard
                                person={hod}
                                type="HOD"
                            />
                        </section>

                        {/* MENTOR */}
                        <section>
                            <div className="
                                mb-3
                                flex
                                items-center
                                gap-2
                            ">
                                <FiUser className="
                                    text-violet-300
                                " />

                                <h3 className="
                                    text-sm
                                    font-semibold
                                    text-white
                                ">
                                    Mentor
                                </h3>
                            </div>

                            <PersonCard
                                person={mentor}
                                type="MENTOR"
                            />
                        </section>

                        {/* TEAM MEMBERS */}
                        <section>
                            <div className="
                                mb-3
                                flex
                                items-center
                                justify-between
                            ">
                                <div className="
                                    flex
                                    items-center
                                    gap-2
                                ">
                                    <FiUsers className="
                                        text-green-300
                                    " />

                                    <h3 className="
                                        text-sm
                                        font-semibold
                                        text-white
                                    ">
                                        Team Members
                                    </h3>
                                </div>

                                <span className="
                                    text-xs
                                    text-gray-500
                                ">
                                    {members.length} Members
                                </span>
                            </div>

                            {members.length === 0 ? (
                                <PersonCard
                                    type="MEMBER"
                                />
                            ) : (
                                <div className="
                                    grid
                                    grid-cols-1
                                    sm:grid-cols-2
                                    gap-3
                                ">
                                    {members
                                        .slice(0, 4)
                                        .map((member) => (
                                            <PersonCard
                                                key={member.id}
                                                person={member}
                                                type={
                                                    member.roll_no
                                                        ? `MEMBER • ${member.roll_no}`
                                                        : "MEMBER"
                                                }
                                            />
                                        ))}
                                </div>
                            )}
                        </section>
                    </div>
                )}
            </motion.div>
        </div>
    );
}
