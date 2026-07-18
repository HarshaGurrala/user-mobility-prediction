import { motion } from "framer-motion";
import {
  FiAlertTriangle,
  FiShield,
  FiClock,
} from "react-icons/fi";

const AlertsCard = ({
  alerts = [],
  loading = false,
}) => {
  return (
    <motion.div
      initial={{
        opacity: 0,
        y: 30,
      }}
      animate={{
        opacity: 1,
        y: 0,
      }}
      transition={{
        duration: 0.6,
        delay: 0.2,
      }}
      className="
rounded-3xl
border
border-white/10
bg-white/5
backdrop-blur-2xl
p-6
shadow-[0_0_40px_rgba(255,80,80,0.12)]
relative
overflow-hidden
"
    >
      <div
        className="
absolute
bottom-0
right-0
h-40
w-40
bg-red-500/20
blur-3xl
rounded-full
"
      />

      <div
        className="
flex
items-center
justify-between
"
      >
        <div className="flex items-center gap-3">
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
              Security Alerts
            </h2>

            <p
              className="
text-xs
text-gray-400
"
            >
              Guardian monitoring system
            </p>
          </div>
        </div>

        <div
          className={`
px-3
py-1
rounded-full
text-xs
${
  alerts.length > 0
    ? "bg-red-500/20 text-red-300"
    : "bg-green-500/20 text-green-300"
}
`}
        >
          {alerts.length > 0 ? "ALERT" : "ACTIVE"}
        </div>
      </div>

      <div
        className="
mt-6
space-y-3
"
      >
        {loading ? (
          <div
            className="
rounded-2xl
bg-black/30
border
border-white/10
p-5
text-center
text-gray-400
"
          >
            Loading alerts...
          </div>
        ) : alerts.length === 0 ? (
          <div
            className="
rounded-2xl
bg-black/30
border
border-white/10
p-5
text-center
"
          >
            <FiShield
              className="
mx-auto
text-green-400
text-3xl
mb-3
"
            />

            <p
              className="
text-white
"
            >
              All movements are safe
            </p>

            <p
              className="
text-gray-400
text-sm
mt-1
"
            >
              No unknown location detected
            </p>
          </div>
        ) : (
          alerts.map((alert, index) => (
            <motion.div
              key={alert.id || index}
              whileHover={{
                scale: 1.02,
              }}
              className="
rounded-2xl
bg-black/30
border
border-white/10
p-4
"
            >
              <div
                className="
flex
items-center
gap-3
"
              >
                <FiAlertTriangle
                  className="
text-yellow-400
"
                />

                <div>
                  <p
                    className="
text-white
text-sm
"
                  >
                    {alert.message ||
                      alert.title ||
                      alert.description ||
                      "Unknown alert"}
                  </p>

                  <div
                    className="
flex
items-center
gap-2
text-xs
text-gray-400
mt-2
"
                  >
                    <FiClock />

                    {alert.created_at ||
                      alert.time ||
                      "Recently detected"}
                  </div>
                </div>
              </div>
            </motion.div>
          ))
        )}
      </div>
    </motion.div>
  );
};

export default AlertsCard;