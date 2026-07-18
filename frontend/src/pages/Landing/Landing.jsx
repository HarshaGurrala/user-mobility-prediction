import Navbar from "../../components/landing/Navbar";
import Hero from "../../components/landing/Hero";
import Features from "../../components/landing/Features";
import DashboardPreview from "../../components/landing/DashboardPreview";
import Stats from "../../components/landing/Stats";
import CTA from "../../components/landing/CTA";
import Footer from "../../components/landing/Footer";

export default function Landing() {
  return (
    <>
      <Navbar />
      <Hero />
      <Features />
      <DashboardPreview />
      <Stats />
      <CTA />
      <Footer />
    </>
  );
}