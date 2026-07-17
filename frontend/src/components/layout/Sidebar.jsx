export default function Sidebar() {
  return (
    <aside className="w-72 bg-slate-900 border-r border-cyan-500/20">

      <div className="p-6">

        <h1 className="text-2xl font-bold text-cyan-400">
          Mobility AI
        </h1>

      </div>

      <nav className="px-4 space-y-2">

        <button className="w-full text-left p-3 rounded-xl hover:bg-slate-800 text-white">
          Dashboard
        </button>

        <button className="w-full text-left p-3 rounded-xl hover:bg-slate-800 text-white">
          Children
        </button>

        <button className="w-full text-left p-3 rounded-xl hover:bg-slate-800 text-white">
          Alerts
        </button>

        <button className="w-full text-left p-3 rounded-xl hover:bg-slate-800 text-white">
          Analytics
        </button>

        <button className="w-full text-left p-3 rounded-xl hover:bg-slate-800 text-white">
          Settings
        </button>

      </nav>

    </aside>
  );
}