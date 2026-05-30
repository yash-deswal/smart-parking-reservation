import { useEffect, useState } from 'react';
import api from '../services/api';

interface AdminStats {
  totalUsers: number;
  totalSlots: number;
  availableSlots: number;
  reservedSlots: number;
  activeReservations: number;
  cancelledReservations: number;
  completedReservations: number;
}

export const AdminDashboard = () => {
  const [stats, setStats] = useState<AdminStats | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const res = await api.get('/admin/dashboard/statistics');
        setStats(res.data);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchStats();
  }, []);

  if (loading) return <div className="text-center py-10">Loading...</div>;

  return (
    <div>
      <h2 className="text-3xl font-bold mb-6">System Overview</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <StatCard title="Total Users" value={stats?.totalUsers} color="border-blue-500" />
        <StatCard title="Total Slots" value={stats?.totalSlots} color="border-indigo-500" />
        <StatCard title="Available Slots" value={stats?.availableSlots} color="border-success" />
        <StatCard title="Reserved Slots" value={stats?.reservedSlots} color="border-danger" />
        <StatCard title="Active Reservations" value={stats?.activeReservations} color="border-primary" />
        <StatCard title="Completed Reservations" value={stats?.completedReservations} color="border-green-500" />
        <StatCard title="Cancelled Reservations" value={stats?.cancelledReservations} color="border-gray-500" />
      </div>
    </div>
  );
};

const StatCard = ({ title, value, color }: { title: string, value?: number, color: string }) => (
  <div className={`bg-white rounded-lg shadow p-6 border-l-4 ${color}`}>
    <h3 className="text-gray-500 text-sm font-medium uppercase tracking-wider">{title}</h3>
    <p className="mt-2 text-3xl font-bold text-gray-900">{value ?? 0}</p>
  </div>
);
