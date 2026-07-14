import {
  Bell,
  Search,
  UserCircle,
  Sun,
  ChevronDown
} from "lucide-react";

export default function Topbar() {
  return (
    <header className="sticky top-0 z-40 h-20 bg-slate-950/70 backdrop-blur-xl border-b border-white/10 flex items-center justify-between px-8">

      {/* Left */}

      <div>

        <h1 className="text-3xl font-bold text-white">

          AI Dashboard

        </h1>

        <p className="text-slate-400">

          Monitor • Predict • Protect

        </p>

      </div>

      {/* Center */}

      <div className="hidden lg:flex items-center">

        <div className="flex items-center bg-slate-900 rounded-2xl px-4 py-3 w-[350px]">

          <Search className="text-slate-400" />

          <input
            placeholder="Search..."
            className="ml-3 bg-transparent outline-none text-white w-full"
          />

        </div>

      </div>

      {/* Right */}

      <div className="flex items-center gap-5">

        <button className="bg-slate-900 p-3 rounded-xl hover:bg-slate-800 transition">

          <Sun className="text-yellow-400"/>

        </button>

        <button className="bg-slate-900 p-3 rounded-xl hover:bg-slate-800 transition relative">

          <Bell className="text-cyan-400"/>

          <span className="absolute -top-1 -right-1 w-3 h-3 rounded-full bg-red-500"/>

        </button>

        <div className="flex items-center gap-3 bg-slate-900 px-4 py-2 rounded-2xl">

          <UserCircle
            size={36}
            className="text-cyan-400"
          />

          <div>

            <h3 className="text-white font-semibold">

              Harsha

            </h3>

            <p className="text-slate-400 text-sm">

              Administrator

            </p>

          </div>

          <ChevronDown className="text-slate-400"/>

        </div>

      </div>

    </header>
  );
}