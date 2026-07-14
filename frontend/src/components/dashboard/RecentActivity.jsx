export default function RecentActivity() {

  const activity = [

    "Prediction generated",

    "Location updated",

    "Safe Zone entered",

    "Emergency contact synced"

  ];

  return (

    <div className="bg-slate-900 border border-white/10 rounded-3xl p-6">

      <h2 className="text-2xl text-white font-bold">

        Recent Activity

      </h2>

      <div className="mt-6 space-y-4">

        {activity.map((item, index) => (

          <div
            key={index}
            className="bg-slate-800 rounded-xl px-4 py-3 text-slate-300"
          >

            {item}

          </div>

        ))}

      </div>

    </div>

  );

}