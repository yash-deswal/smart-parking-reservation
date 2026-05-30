import { Link } from 'react-router-dom';
import { Car, ShieldCheck, Clock, CheckCircle2, ArrowRight, UserPlus, LogIn } from 'lucide-react';

export const Home = () => {
  return (
    <div className="min-h-screen bg-gray-50 flex flex-col font-sans">
      {/* Navigation Header */}
      <header className="bg-white/80 backdrop-blur-md sticky top-0 z-50 border-b border-gray-100">
        <div className="max-w-7xl mx-auto px-6 h-16 flex items-center justify-between">
          <Link to="/" className="flex items-center space-x-2 text-primary font-bold text-xl">
            <Car size={32} className="text-primary" />
            <span className="tracking-tight text-gray-900 font-extrabold">Smart<span className="text-primary font-medium">Parking</span></span>
          </Link>
          
          <nav className="hidden md:flex space-x-8 text-sm font-semibold text-gray-600">
            <a href="#features" className="hover:text-primary transition">Features</a>
            <a href="#metrics" className="hover:text-primary transition">Statistics</a>
            <a href="#about" className="hover:text-primary transition">About</a>
          </nav>

          <div className="flex items-center space-x-3">
            <Link to="/login" className="flex items-center space-x-1.5 px-4 py-2 text-sm font-semibold text-gray-700 hover:text-primary transition">
              <LogIn size={16} />
              <span>Sign In</span>
            </Link>
            <Link to="/register" className="flex items-center space-x-1.5 bg-primary hover:bg-blue-600 text-white px-5 py-2 rounded-full text-sm font-semibold shadow hover:shadow-lg transition">
              <UserPlus size={16} />
              <span>Register</span>
            </Link>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <main className="flex-1">
        <section className="max-w-7xl mx-auto px-6 py-12 md:py-20 grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
          <div className="space-y-6">
            <div className="inline-flex items-center space-x-2 bg-blue-50 text-primary px-3 py-1 rounded-full text-xs font-semibold uppercase tracking-wider">
              <span>Next-Gen Parking System</span>
            </div>
            <h1 className="text-4xl md:text-6xl font-extrabold text-gray-900 tracking-tight leading-none">
              Effortless Parking, <br />
              <span className="text-primary bg-gradient-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent">Instant Reservations.</span><br />
              Yash Deswal
            </h1>
            <p className="text-gray-600 text-lg md:text-xl max-w-lg leading-relaxed">
              Ditch the parking search frustration. Instantly discover, reserve, and manage premium parking slots in real time with our smart-indicator reservation system.
            </p>
            <div className="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
              <Link to="/login" className="flex items-center justify-center space-x-2 bg-primary hover:bg-blue-600 text-white font-semibold px-8 py-4 rounded-lg shadow-md hover:shadow-lg transition text-base">
                <span>Book a Slot Now</span>
                <ArrowRight size={18} />
              </Link>
              <Link to="/slots" className="flex items-center justify-center space-x-2 bg-white border-2 border-gray-200 hover:border-primary text-gray-700 hover:text-primary font-semibold px-8 py-4 rounded-lg shadow-sm transition text-base">
                <span>View Empty Slots</span>
              </Link>
            </div>
          </div>

          <div className="relative">
            <div className="absolute inset-0 bg-gradient-to-tr from-primary/10 to-indigo-500/10 rounded-2xl filter blur-2xl"></div>
            <img 
              src="/parking_hero.png" 
              alt="Smart Parking Hero Banner" 
              className="relative rounded-2xl shadow-2xl border border-gray-200/50 w-full object-cover aspect-[4/3] transform hover:scale-[1.02] transition duration-500"
            />
          </div>
        </section>

        {/* Metrics Banner */}
        <section id="metrics" className="bg-white border-y border-gray-100 py-12">
          <div className="max-w-7xl mx-auto px-6 grid grid-cols-1 md:grid-cols-3 gap-8 text-center">
            <div className="space-y-1">
              <p className="text-4xl md:text-5xl font-extrabold text-gray-900">20+</p>
              <p className="text-gray-500 text-sm font-semibold uppercase tracking-wider">Premium Slots</p>
            </div>
            <div className="space-y-1">
              <p className="text-4xl md:text-5xl font-extrabold text-primary">10,000+</p>
              <p className="text-gray-500 text-sm font-semibold uppercase tracking-wider">Happy Bookers</p>
            </div>
            <div className="space-y-1">
              <p className="text-4xl md:text-5xl font-extrabold text-gray-900">99.9%</p>
              <p className="text-gray-500 text-sm font-semibold uppercase tracking-wider">Availability Match</p>
            </div>
          </div>
        </section>

        {/* Features Grid */}
        <section id="features" className="max-w-7xl mx-auto px-6 py-20">
          <div className="text-center max-w-2xl mx-auto mb-16">
            <h2 className="text-3xl font-bold text-gray-900 tracking-tight">Designed for Modern Urban Drivers</h2>
            <p className="text-gray-500 mt-3">Experience premium booking features designed for maximum convenience and reliability.</p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            {/* Feature 1 */}
            <div className="bg-white p-8 rounded-xl shadow-sm border border-gray-100 hover:shadow-md hover:border-primary/20 transition duration-300">
              <div className="w-12 h-12 bg-blue-50 text-primary flex items-center justify-center rounded-lg mb-6">
                <Clock size={28} />
              </div>
              <h3 className="text-xl font-bold text-gray-900 mb-3">Real-Time Indicators</h3>
              <p className="text-gray-600 leading-relaxed">
                Check up-to-the-second slot availability and indicators before reaching your destination. No guesswork involved.
              </p>
            </div>

            {/* Feature 2 */}
            <div className="bg-white p-8 rounded-xl shadow-sm border border-gray-100 hover:shadow-md hover:border-primary/20 transition duration-300">
              <div className="w-12 h-12 bg-indigo-50 text-indigo-600 flex items-center justify-center rounded-lg mb-6">
                <ShieldCheck size={28} />
              </div>
              <h3 className="text-xl font-bold text-gray-900 mb-3">Secure JWT Auth</h3>
              <p className="text-gray-600 leading-relaxed">
                Your bookings and dashboard data are shielded by cryptographic JWT keys and secure, stateless request guards.
              </p>
            </div>

            {/* Feature 3 */}
            <div className="bg-white p-8 rounded-xl shadow-sm border border-gray-100 hover:shadow-md hover:border-primary/20 transition duration-300">
              <div className="w-12 h-12 bg-green-50 text-green-600 flex items-center justify-center rounded-lg mb-6">
                <CheckCircle2 size={28} />
              </div>
              <h3 className="text-xl font-bold text-gray-900 mb-3">Instant Cancellation</h3>
              <p className="text-gray-600 leading-relaxed">
                Plans changed? Cancel your active reservations instantly with a single click, immediately releasing the slot.
              </p>
            </div>
          </div>
        </section>
      </main>

      {/* Footer */}
      <footer className="bg-gray-900 text-gray-400 py-12 border-t border-gray-800">
        <div className="max-w-7xl mx-auto px-6 flex flex-col md:flex-row items-center justify-between text-sm">
          <div className="flex items-center space-x-2 text-white font-bold mb-4 md:mb-0">
            <Car size={24} className="text-primary" />
            <span className="tracking-tight text-white font-extrabold">Smart<span className="text-primary font-medium">Parking</span></span>
          </div>
          <p>© {new Date().getFullYear()} Smart Parking Reservation System. All rights reserved.</p>
        </div>
      </footer>
    </div>
  );
};
