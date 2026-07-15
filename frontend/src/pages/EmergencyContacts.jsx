// function EmergencyContacts() {
//   return <h1>Emergency Contacts</h1>;
// }

// export default EmergencyContacts;
import DashboardLayout from "../components/layout/DashboardLayout";
import EmergencyList from "../components/emergency/EmergencyList";
import SOSButton from "../components/emergency/SOSButton";

export default function EmergencyContacts() {

    return (

        <DashboardLayout>

            <div className="flex justify-between items-center">

                <div>

                    <h1 className="text-5xl font-bold text-white">

                        Emergency Contacts

                    </h1>

                    <p className="text-slate-400 mt-2">

                        Manage trusted people for emergencies

                    </p>

                </div>

                <button className="bg-cyan-500 hover:bg-cyan-400 text-black font-bold px-6 py-3 rounded-xl">

                    + Add Contact

                </button>

            </div>

            <div className="mt-10">

                <EmergencyList />

            </div>

            <SOSButton />

        </DashboardLayout>

    );

}