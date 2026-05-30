import { Outlet, Link, useNavigate } from 'react-router-dom';
import { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { LayoutDashboard, Car, Calendar, LogOut, Menu } from 'lucide-react';

export const UserLayout = () => {
  const { logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="min-h-screen bg-gray-50 flex">
      {/* Sidebar */}
      <aside className="w-64 bg-accent text-white flex flex-col">
        <div className="p-4 text-2xl font-bold border-b border-gray-700 text-center">
          Smart Parking
        </div>
        <nav className="flex-1 p-4 space-y-2">
          <Link to="/dashboard" className="flex items-center space-x-2 p-2 hover:bg-gray-700 rounded transition">
            <LayoutDashboard size={20} />
            <span>Dashboard</span>
          </Link>
          <Link to="/slots" className="flex items-center space-x-2 p-2 hover:bg-gray-700 rounded transition">
            <Car size={20} />
            <span>Parking Slots</span>
          </Link>
          <Link to="/reservations" className="flex items-center space-x-2 p-2 hover:bg-gray-700 rounded transition">
            <Calendar size={20} />
            <span>My Reservations</span>
          </Link>
        </nav>
        <div className="p-4 border-t border-gray-700">
          <button onClick={handleLogout} className="flex items-center space-x-2 p-2 w-full hover:bg-red-600 rounded transition text-left">
            <LogOut size={20} />
            <span>Logout</span>
          </button>
        </div>
      </aside>

      {/* Main Content */}
      <main className="flex-1 flex flex-col">
        <header className="bg-white shadow p-4 flex items-center justify-between">
          <h1 className="text-xl font-semibold text-gray-800">User Portal</h1>
          <Menu className="text-gray-500 cursor-pointer md:hidden" />
        </header>
        <div className="p-6 overflow-auto flex-1">
          <Outlet />
        </div>
      </main>
    </div>
  );
};
