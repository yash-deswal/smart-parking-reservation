import { useEffect, useState } from 'react';
import api from '../services/api';

interface AdminReservation {
  id: number;
  userName: string;
  slotNumber: string;
  vehicleNumber: string;
  startTime: string;
  endTime: string;
  status: string;
}

export const AdminReservations = () => {
  const [reservations, setReservations] = useState<AdminReservation[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchRes = async () => {
      try {
        const res = await api.get('/admin/reservations');
        setReservations(res.data);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchRes();
  }, []);

  if (loading) return <div className="text-center py-10">Loading...</div>;

  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">All Reservations</h2>
      <div className="bg-white rounded-lg shadow overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">User</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Slot</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Time</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {reservations.map(res => (
              <tr key={res.id}>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{res.id}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm font-semibold">{res.userName}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm font-semibold">{res.slotNumber}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                  <div>{new Date(res.startTime).toLocaleString()}</div>
                  <div>to {new Date(res.endTime).toLocaleString()}</div>
                </td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                    res.status === 'ACTIVE' ? 'bg-blue-100 text-blue-800' :
                    res.status === 'COMPLETED' ? 'bg-green-100 text-green-800' :
                    'bg-red-100 text-red-800'
                  }`}>
                    {res.status}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {reservations.length === 0 && <div className="text-center text-gray-500 py-10">No reservations found.</div>}
      </div>
    </div>
  );
};
