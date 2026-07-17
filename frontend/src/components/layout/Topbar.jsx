export default function Topbar() {
  return (
    <header className="h-20 bg-slate-900 border-b border-cyan-500/20 flex items-center justify-between px-8">

      <div>

        <h2 className="text-2xl text-white font-bold">
          Guardian Dashboard
        </h2>

        <p className="text-slate-400">
          AI Powered User Mobility Prediction
        </p>

      </div>

      <div className="text-right">

        <h3 className="text-white font-semibold">
          Welcome Guardian
        </h3>

        <p className="text-cyan-400">
          GUARD688183
        </p>

      </div>

    </header>
  );
}