import { FiMap, FiActivity, FiShield, FiNavigation, FiCpu, FiBell, FiHeart, FiTarget, FiMail } from "react-icons/fi";
import SectionPanel from "./SectionPanel";

const cardData = {
  home: [
    { title: "Live Tracking", text: "Monitor movement in real time with connected location insights.", icon: <FiMap size={24} /> },
    { title: "Smart Prediction", text: "Forecast likely routes and future mobility patterns with AI.", icon: <FiActivity size={24} /> },
    { title: "Safety Alerts", text: "Stay informed with timely guardian notifications and safe-zone awareness.", icon: <FiShield size={24} /> },
  ],
  platform: [
    { title: "Route Intelligence", text: "Understand your movement pattern and gain predictive confidence.", icon: <FiNavigation size={24} /> },
    { title: "Guardian Control", text: "Keep trusted contacts informed about important mobility changes.", icon: <FiBell size={24} /> },
  ],
  technology: [
    { title: "Prediction Models", text: "Use machine learning to estimate likely routes and behavior patterns.", icon: <FiCpu size={24} /> },
    { title: "Real-Time Data", text: "Continuously update movement insights as changes occur.", icon: <FiTarget size={24} /> },
  ],
  about: [
    { title: "Purpose", text: "Support safer journeys through prediction and safety-aware monitoring.", icon: <FiHeart size={24} /> },
    { title: "Impact", text: "Bring clarity to mobility decisions for users and guardians alike.", icon: <FiShield size={24} /> },
  ],
};

export default function SectionSections() {
  return (
    <>
      <SectionPanel
        id="home"
        eyebrow="Home"
        title="Welcome to User Mobility Prediction"
        description="A modern landing experience for AI-driven mobility prediction, location safety, and guardian awareness, all in one connected platform."
      >
        <div className="mt-10 grid md:grid-cols-3 gap-6">
          {cardData.home.map((item) => (
            <div key={item.title} className="rounded-[32px] border border-white/10 bg-white/[0.04] backdrop-blur-2xl p-8 h-full hover:bg-white/[0.08] transition">
              <div className="w-14 h-14 rounded-2xl bg-white/10 flex items-center justify-center text-blue-400">
                {item.icon}
              </div>
              <h3 className="text-white font-semibold text-xl mt-6">{item.title}</h3>
              <p className="mt-3 text-gray-400 leading-7">{item.text}</p>
            </div>
          ))}
        </div>
      </SectionPanel>

      <SectionPanel
        id="platform"
        eyebrow="Platform"
        title="Built for Everyday Mobility"
        description="The platform connects users, guardians, and location intelligence in a simple flow for health, travel, and safety monitoring."
      >
        <div className="mt-10 grid md:grid-cols-2 gap-6">
          {cardData.platform.map((item) => (
            <div key={item.title} className="rounded-[32px] border border-white/10 bg-white/[0.04] backdrop-blur-2xl p-8 h-full hover:bg-white/[0.08] transition">
              <div className="w-14 h-14 rounded-2xl bg-white/10 flex items-center justify-center text-blue-400">
                {item.icon}
              </div>
              <h3 className="text-white font-semibold text-xl mt-6">{item.title}</h3>
              <p className="mt-3 text-gray-400 leading-7">{item.text}</p>
            </div>
          ))}
        </div>
      </SectionPanel>

      <SectionPanel
        id="technology"
        eyebrow="Technology"
        title="AI-Powered Insight Layer"
        description="Advanced prediction models and live analytics work together to help users make better mobility decisions with confidence."
      >
        <div className="mt-10 grid md:grid-cols-2 gap-6">
          {cardData.technology.map((item) => (
            <div key={item.title} className="rounded-[32px] border border-white/10 bg-white/[0.04] backdrop-blur-2xl p-8 h-full hover:bg-white/[0.08] transition">
              <div className="w-14 h-14 rounded-2xl bg-white/10 flex items-center justify-center text-blue-400">
                {item.icon}
              </div>
              <h3 className="text-white font-semibold text-xl mt-6">{item.title}</h3>
              <p className="mt-3 text-gray-400 leading-7">{item.text}</p>
            </div>
          ))}
        </div>
      </SectionPanel>

      <SectionPanel
        id="about"
        eyebrow="About"
        title="Why This Project Matters"
        description="User Mobility Prediction focuses on making travel safer, smarter, and more connected for users who want better situational awareness."
      >
        <div className="mt-10 grid md:grid-cols-2 gap-6">
          {cardData.about.map((item) => (
            <div key={item.title} className="rounded-[32px] border border-white/10 bg-white/[0.04] backdrop-blur-2xl p-8 h-full hover:bg-white/[0.08] transition">
              <div className="w-14 h-14 rounded-2xl bg-white/10 flex items-center justify-center text-blue-400">
                {item.icon}
              </div>
              <h3 className="text-white font-semibold text-xl mt-6">{item.title}</h3>
              <p className="mt-3 text-gray-400 leading-7">{item.text}</p>
            </div>
          ))}
        </div>
      </SectionPanel>

      <SectionPanel
        id="contact"
        eyebrow="Contact"
        title="Get in Touch"
        description="Reach out for product questions, collaborations, or to learn how this mobility platform can be extended further."
      >
        <div className="mt-10 rounded-[32px] border border-white/10 bg-white/[0.04] backdrop-blur-2xl p-8 text-gray-400 leading-8 hover:bg-white/[0.08] transition">
          <div className="w-14 h-14 rounded-2xl bg-white/10 flex items-center justify-center text-blue-400">
            <FiMail size={24} />
          </div>
          <p className="mt-6 text-white font-semibold text-xl">Contact Details</p>
          <p className="mt-3">Email: support@mobilityprediction.com</p>
          <p>Location: Smart Mobility Lab</p>
          <p>Availability: Open for product and collaboration inquiries</p>
        </div>
      </SectionPanel>
    </>
  );
}
