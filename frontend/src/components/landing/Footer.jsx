import {
  FaGithub,
  FaLinkedin,
  FaInstagram,
  FaTwitter,
} from "react-icons/fa";

export default function Footer() {
  return (
    <footer className="bg-[#050505] border-t border-white/10">

      <div className="max-w-7xl mx-auto px-8 py-24">

        <div className="grid lg:grid-cols-4 gap-16">

          {/* Logo */}

          <div>

            <div className="flex items-center gap-4">

              <div className="w-12 h-12 rounded-full bg-white flex items-center justify-center text-black font-bold">

                M

              </div>

              <h2 className="text-white text-2xl font-bold">

                MobilityAI

              </h2>

            </div>

            <p className="text-gray-400 mt-8 leading-8">

              AI Powered User Mobility Prediction
              platform for intelligent navigation,
              safety monitoring and guardian alerts.

            </p>

          </div>

          {/* Product */}

          <div>

            <h3 className="text-white font-semibold mb-8">

              Product

            </h3>

            <div className="space-y-5">

              <p className="text-gray-400 hover:text-white cursor-pointer">
                Features
              </p>

              <p className="text-gray-400 hover:text-white cursor-pointer">
                Dashboard
              </p>

              <p className="text-gray-400 hover:text-white cursor-pointer">
                Live Tracking
              </p>

              <p className="text-gray-400 hover:text-white cursor-pointer">
                Analytics
              </p>

            </div>

          </div>

          {/* Company */}

          <div>

            <h3 className="text-white font-semibold mb-8">

              Company

            </h3>

            <div className="space-y-5">

              <p className="text-gray-400 hover:text-white cursor-pointer">
                About
              </p>

              <p className="text-gray-400 hover:text-white cursor-pointer">
                Privacy
              </p>

              <p className="text-gray-400 hover:text-white cursor-pointer">
                Terms
              </p>

              <p className="text-gray-400 hover:text-white cursor-pointer">
                Contact
              </p>

            </div>

          </div>

          {/* Social */}

          <div>

            <h3 className="text-white font-semibold mb-8">

              Follow Us

            </h3>

            <div className="flex gap-5 text-2xl">

              <FaGithub className="text-gray-400 hover:text-white cursor-pointer" />

              <FaLinkedin className="text-gray-400 hover:text-blue-400 cursor-pointer" />

              <FaInstagram className="text-gray-400 hover:text-pink-500 cursor-pointer" />

              <FaTwitter className="text-gray-400 hover:text-sky-400 cursor-pointer" />

            </div>

          </div>

        </div>

        <div className="border-t border-white/10 mt-20 pt-8 flex justify-between">

          <p className="text-gray-500">

            © 2026 MobilityAI. All rights reserved.

          </p>

          <p className="text-gray-500">

            Designed with ❤️ using React & Tailwind

          </p>

        </div>

      </div>

    </footer>
  );
}