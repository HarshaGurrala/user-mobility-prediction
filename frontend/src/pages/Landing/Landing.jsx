import Navbar from "../../components/landing/Navbar";
import Hero from "../../components/landing/Hero";
import Features from "../../components/landing/Features";
import DashboardPreview from "../../components/landing/DashboardPreview";
import Stats from "../../components/landing/Stats";
import CTA from "../../components/landing/CTA";
import Footer from "../../components/landing/Footer";
import SectionSections from "../../components/landing/SectionSections";
import FullPageDecorations from "../../components/landing/FullPageDecorations";

export default function Landing() {
  return (
    <>
      <FullPageDecorations />
      <Navbar />
      <Hero />
      <Features />
      <DashboardPreview />
      <Stats />
      <SectionSections />
      <CTA />
      <Footer />
    </>
  );
}