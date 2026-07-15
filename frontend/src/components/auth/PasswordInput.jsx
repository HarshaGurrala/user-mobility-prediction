import { useState } from "react";
import { FaEye, FaEyeSlash } from "react-icons/fa";

export default function PasswordInput({
  value,
  onChange,
  name,
  placeholder = "Password",
})
{
  const [show, setShow] = useState(false);

  return (
    <div className="relative">

     <input
  type={show ? "text" : "password"}
  name={name}
  value={value}
  onChange={onChange}
  placeholder={placeholder}
  className="w-full bg-slate-900/70 border border-white/10 rounded-xl p-4 text-white outline-none focus:border-cyan-400"
/>
      <button
        type="button"
        onClick={() => setShow(!show)}
        className="absolute right-5 top-5 text-slate-400"
      >
        {show ? <FaEyeSlash /> : <FaEye />}
      </button>

    </div>
  );
}