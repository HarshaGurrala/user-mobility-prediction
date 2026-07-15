import { Siren } from "lucide-react";

export default function SOSButton() {

    return (

        <button className="w-full mt-10 bg-red-600 hover:bg-red-500 rounded-3xl py-6 text-white text-2xl font-bold shadow-lg shadow-red-500/30 transition">

            <div className="flex justify-center items-center gap-4">

                <Siren />

                SEND SOS ALERT

            </div>

        </button>

    );

}