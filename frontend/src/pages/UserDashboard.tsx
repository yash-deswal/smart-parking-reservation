import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import api from '../services/api';

interface DashboardStats {
  availableSlots: number;
  reservedSlots: number;
  myActiveReservations: number;
}

export const UserDashboard = () => {
  const [stats, setStats] = useState<DashboardStats | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchDashboard = async () => {
      try {
        const res = await api.get('/user/dashboard');
        setStats(res.data);
      } catch (err) {
        console.error('Failed to load dashboard', err);
      } finally {
        setLoading(false);
      }
    };
    fetchDashboard();
  }, []);

  if (loading) return <div className="text-center py-10">Loading...</div>;

  return (
    <div>
      <h2 className="text-3xl font-bold mb-6">Welcome to Smart Parking</h2>
      
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <div className="bg-white rounded-lg shadow p-6 border-l-4 border-success">
          <h3 className="text-gray-500 text-sm font-medium uppercase tracking-wider">Available Slots</h3>
          <p className="mt-2 text-3xl font-bold text-gray-900">{stats?.availableSlots || 0}</p>
        </div>
        <div className="bg-white rounded-lg shadow p-6 border-l-4 border-danger">
          <h3 className="text-gray-500 text-sm font-medium uppercase tracking-wider">Reserved Slots</h3>
          <p className="mt-2 text-3xl font-bold text-gray-900">{stats?.reservedSlots || 0}</p>
        </div>
        <div className="bg-white rounded-lg shadow p-6 border-l-4 border-primary">
          <h3 className="text-gray-500 text-sm font-medium uppercase tracking-wider">My Active Reservations</h3>
          <p className="mt-2 text-3xl font-bold text-gray-900">{stats?.myActiveReservations || 0}</p>
        </div>
      </div>

      <div className="bg-white rounded-lg shadow p-6">
        <h3 className="text-xl font-semibold mb-4">Quick Actions</h3>
        <div className="flex space-x-4">
          <Link to="/reservations/create" className="bg-primary text-white px-4 py-2 rounded shadow hover:bg-blue-600 transition">
            Book a Slot
          </Link>
          <Link to="/slots" className="bg-gray-100 text-gray-800 px-4 py-2 rounded shadow hover:bg-gray-200 transition">
            View All Slots
          </Link>
        </div>
      </div>
    </div>
  );
};
