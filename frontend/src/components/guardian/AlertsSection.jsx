import AlertCard from "./AlertCard";

export default function AlertsSection() {
  return (
    <div className=" p-9 rmt-8">

      <h2 className="text-3xl font-bold text-white mb-6">
        Recent Alerts
      </h2>

      <div className="space-y-4">

        <AlertCard
          title="Harsha entered Home Safe Zone"
          time="2 minutes ago"
          type="success"
        />

        <AlertCard
          title="Harsha started moving"
          time="15 minutes ago"
          type="info"
        />

        <AlertCard
          title="Rahul left School Safe Zone"
          time="Yesterday"
          type="warning"
        />

      </div>

    </div>
  );
}