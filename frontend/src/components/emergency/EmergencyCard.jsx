import {
    Phone,
    Pencil,
    Trash2,
    UserCircle
} from "lucide-react";

export default function EmergencyCard({

    name,
    relation,
    phone

}) {

    return (

        <div className="bg-slate-900 border border-white/10 rounded-3xl p-6 hover:border-cyan-400 transition">

            <div className="flex justify-between">

                <div className="flex gap-4">

                    <UserCircle
                        size={50}
                        className="text-cyan-400"
                    />

                    <div>

                        <h2 className="text-white text-2xl font-bold">

                            {name}

                        </h2>

                        <p className="text-slate-400">

                            {relation}

                        </p>

                        <p className="text-green-400 mt-2">

                            {phone}

                        </p>

                    </div>

                </div>

            </div>

            <div className="flex gap-3 mt-6">

                <button className="flex-1 bg-green-500 rounded-xl py-3 flex justify-center">

                    <Phone />

                </button>

                <button className="flex-1 bg-blue-500 rounded-xl py-3 flex justify-center">

                    <Pencil />

                </button>

                <button className="flex-1 bg-red-500 rounded-xl py-3 flex justify-center">

                    <Trash2 />

                </button>

            </div>

        </div>

    );

}