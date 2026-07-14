import { UserCircle } from "lucide-react";

export default function UserInfoCard() {
  return (

    <div className="bg-slate-900 border border-white/10 rounded-3xl p-6">

      <div className="flex items-center gap-4">

        <UserCircle
          size={60}
          className="text-cyan-400"
        />

        <div>

          <h2 className="text-white text-2xl font-bold">

            Harsha

          </h2>

          <p className="text-slate-400">

            Active User

          </p>

        </div>

      </div>

      <div className="mt-8 space-y-4">

        <div className="flex justify-between">

          <span className="text-slate-400">

            Current City

          </span>

          <span className="text-white">

            Hyderabad

          </span>

        </div>

        <div className="flex justify-between">

          <span className="text-slate-400">

            Last Updated

          </span>

          <span className="text-green-400">

            Just Now

          </span>

        </div>

        <div className="flex justify-between">

          <span className="text-slate-400">

            Status

          </span>

          <span className="text-green-400">

            SAFE

          </span>

        </div>

      </div>

    </div>

  );
}