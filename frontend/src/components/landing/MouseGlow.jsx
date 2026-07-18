import { useEffect, useState } from "react";

export default function MouseGlow() {

  const [mouse, setMouse] = useState({
    x: 0,
    y: 0,
  });

  useEffect(() => {

    const move = (e) => {

      setMouse({
        x: e.clientX,
        y: e.clientY,
      });

    };

    window.addEventListener("mousemove", move);

    return () => window.removeEventListener("mousemove", move);

  }, []);

  return (
    <div
      className="pointer-events-none fixed z-10 w-[450px] h-[450px] rounded-full blur-[150px]"
      style={{
        left: mouse.x - 225,
        top: mouse.y - 225,
        background:
          "radial-gradient(circle, rgba(59,130,246,.12), transparent 70%)",
      }}
    />
  );
}