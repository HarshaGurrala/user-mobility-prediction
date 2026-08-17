import { motion } from "framer-motion";

import {
  FiShield,
  FiUser,
  FiX,
} from "react-icons/fi";

import { useState } from "react";


export default function DashboardHeader({
    user,
    onlineStatus,
    onLogout
}) {

  // ==========================================================
  // PROFILE POPUP
  // ==========================================================

  const [showProfile, setShowProfile] = useState(false);


  // ==========================================================
  // PROFILE IMAGE
  // ==========================================================

  const profileImage =
    user?.profile_picture
        ? user.profile_picture.startsWith("http")
            ? user.profile_picture
            : `https://safepath-guardian.onrender.com${user.profile_picture}`
        : null;


  const displayName =
      user?.full_name ||
      user?.user_name ||
      "User";


  return (

    <>

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
          duration: .6
        }}

        className="
        mx-6
        mt-5
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


          {/* ==================================================
              BRAND
          ================================================== */}

          <div

            className="
            flex
            items-center
            gap-4
            "

          >

            <div

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
                  scale: [1, 1.4, 1]
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
                tracking-wide
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

                User Mobility Prediction System

              </p>

            </div>

          </div>


          {/* ==================================================
              CENTER STATUS
          ================================================== */}

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

            <motion.div

              animate={{
                scale: [1, 1.3, 1]
              }}

              transition={{
                repeat: Infinity,
                duration: 1.5
              }}

              className="
              h-3
              w-3
              rounded-full
              bg-green-400
              shadow-[0_0_20px_#22c55e]
              "

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

                className="
                text-sm
                text-green-300
                "

              >

                AI Tracking Active

              </p>

            </div>

          </div>


          {/* ==================================================
              RIGHT SECTION
          ================================================== */}

          <div

            className="
            flex
            items-center
            gap-4
            "

          >

            {/* ==================================================
                PROFILE BUTTON
            ================================================== */}

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

              {/* ==================================================
                  USER PROFILE IMAGE
              ================================================== */}

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

                {
                  profileImage ? (

                    <img

                      src={profileImage}

                      alt="User profile"

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

                <p

                  className="
                  text-sm
                  text-white
                  "

                >

                  {displayName}

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
                      onlineStatus === "online"
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
                      onlineStatus === "online"
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


      {/* ==========================================================
          PROFILE POPUP
      ========================================================== */}

      {
        showProfile && (

          <div

            className="
            fixed
            inset-0
            z-[1000]
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

            <motion.div

              initial={{
                opacity: 0,
                scale: 0.9,
                y: 20
              }}

              animate={{
                opacity: 1,
                scale: 1,
                y: 0
              }}

              transition={{
                duration: 0.25
              }}

              className="
              relative
              w-[90%]
             max-w-lg
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

              {/* ==================================================
                  CLOSE BUTTON
              ================================================== */}

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
                transition
                "

              >

                <FiX />

              </button>


              {/* ==================================================
                  PROFILE CONTENT
              ================================================== */}

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

                  {
                    profileImage ? (

                      <img

                        src={profileImage}

                        alt="User profile"

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


                {/* USER NAME */}

                <h2

                  className="
                  mt-5
                  text-xl
                  font-semibold
                  text-white
                  "

                >

                  {displayName}

                </h2>


                {/* EMAIL */}

                {
                  user?.email && (

                    <p

                      className="
                      mt-1
                      text-sm
                      text-gray-400
                      "

                    >

                      {user.email}

                    </p>

                  )
                }


                {/* ONLINE STATUS */}

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
                      onlineStatus === "online"
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
                      onlineStatus === "online"
                        ? "Online"
                        : "Offline"
                    }

                  </span>

                </div>

              </div>

            </motion.div>

          </div>

        )
      }

    </>

  );

}