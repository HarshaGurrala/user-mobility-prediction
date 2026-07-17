import ChildCard from "./ChildCard";

export default function ChildrenSection() {
  return (
    <div className="mt-12">

      <h2 className="text-3xl font-bold text-white mb-6">
        My Children
      </h2>

      <div className="space-y-6">

        <ChildCard
          name="Harsha"
          status="Online"
          battery="82%"
          lastSeen="Just now"
        />

        <ChildCard
          name="Rahul"
          status="Offline"
          battery="61%"
          lastSeen="12 mins ago"
        />

      </div>

    </div>
  );
}