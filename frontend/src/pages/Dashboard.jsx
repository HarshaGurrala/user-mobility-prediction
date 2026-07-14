import DashboardLayout from "../components/layout/DashboardLayout";
import DashboardStats from "../components/dashboard/SafeStats";
import PredictionCard from "../components/dashboard/PredictionCard";
import EmergencyStatus from "../components/dashboard/EmergencyStatus";
import DashboardHeader from "../components/dashboard/DashboardHeader";
import RecentActivity from "../components/dashboard/RecentActivity";
import UserInfoCard from "../components/dashboard/UserInfoCard";
import WeatherCard from "../components/dashboard/WeatherCard";
import LiveMap from "../components/map/LiveMap";

export default function Dashboard() {
  return (
    <DashboardLayout>

      <DashboardStats />

      <div className="grid lg:grid-cols-3 gap-6 mt-8">

        <div className="lg:col-span-2 space-y-6">

          <LiveMap />

          <RecentActivity />

        </div>

        <div className="space-y-6">

          <PredictionCard />
          <UserInfoCard />

          <WeatherCard />

          <EmergencyStatus />

        </div>

      </div>

    </DashboardLayout>
  );
}