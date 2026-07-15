import { FaGoogle, FaGithub } from "react-icons/fa";

export default function SocialLogin() {
  return (
    <>

      <div className="flex items-center my-8">

        <div className="flex-1 h-px bg-white/10"></div>

        <span className="px-4 text-slate-400">

          OR

        </span>

        <div className="flex-1 h-px bg-white/10"></div>

      </div>

      <div className="space-y-4">

        <button
          type="button"
          className="w-full rounded-xl border border-white/10 py-4 text-white hover:bg-white/5 transition flex justify-center items-center gap-3"
        >

          <FaGoogle />

          Continue with Google

        </button>

        <button
          type="button"
          className="w-full rounded-xl border border-white/10 py-4 text-white hover:bg-white/5 transition flex justify-center items-center gap-3"
        >

          <FaGithub />

          Continue with GitHub

        </button>

      </div>

    </>
  );
}