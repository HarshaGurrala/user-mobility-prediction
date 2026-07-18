import Navbar from "./Navbar";
import AnimatedBackground from "./AnimatedBackground";
import MouseGlow from "./MouseGlow";
import HeroContent from "./HeroContent";
import HeroVisual from "./HeroVisual";

export default function Hero() {
  return (
    <section className="relative min-h-screen bg-[#050505] overflow-hidden">

      <AnimatedBackground />

      <MouseGlow />

      <Navbar />

      <div className="relative z-20 max-w-7xl mx-auto px-10 grid lg:grid-cols-2 min-h-screen items-center gap-10">

        <HeroContent />

        <HeroVisual />

      </div>

    </section>
  );
}