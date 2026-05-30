import os
import json

def write_file(path, content):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, 'w', encoding='utf-8') as f:
        f.write(content)

base_dir = "frontend"
files = {}

files["src/pages/ParkingSlots.tsx"] = """import { useEffect, useState } from 'react';
import api from '../services/api';

interface Slot {
  id: number;
  slotNumber: string;
  isAvailable: boolean;
  isDisabled: boolean;
}

export const ParkingSlots = () => {
  const [slots, setSlots] = useState<Slot[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchSlots = async () => {
      try {
        const res = await api.get('/slots');
        setSlots(res.data);
      } catch (err) {
        console.error('Failed to load slots', err);
      } finally {
        setLoading(false);
      }
    };
    fetchSlots();
  }, []);

  if (loading) return <div className="text-center py-10">Loading...</div>;

  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Parking Slots</h2>
      <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-6 gap-4">
        {slots.map(slot => (
          <div 
            key={slot.id} 
            className={`p-6 rounded-lg shadow-md text-center border-2 ${
              slot.isDisabled ? 'bg-gray-200 border-gray-400 text-gray-500' :
              slot.isAvailable ? 'bg-green-50 border-success text-green-800' : 
              'bg-red-50 border-danger text-red-800'
            }`}
          >
            <div className="text-xl font-bold mb-2">{slot.slotNumber}</div>
            <div className="text-sm font-semibold uppercase tracking-wider">
              {slot.isDisabled ? 'Disabled' : slot.isAvailable ? 'Available' : 'Reserved'}
            </div>
          </div>
        ))}
      </div>
      {slots.length === 0 && <div className="text-center text-gray-500 py-10">No slots available.</div>}
    </div>
  );
};
"""

files["src/pages/CreateReservation.tsx"] = """import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';

interface Slot {
  id: number;
  slotNumber: string;
}

export const CreateReservation = () => {
  const [slots, setSlots] = useState<Slot[]>([]);
  const [selectedSlotId, setSelectedSlotId] = useState('');
  const [vehicleNumber, setVehicleNumber] = useState('');
  const [vehicleType, setVehicleType] = useState('CAR');
  const [startTime, setStartTime] = useState('');
  const [endTime, setEndTime] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchAvailableSlots = async () => {
      try {
        const res = await api.get('/slots/available');
        setSlots(res.data);
      } catch (err) {
        console.error(err);
      }
    };
    fetchAvailableSlots();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await api.post('/reservations', {
        slotId: Number(selectedSlotId),
        vehicleNumber,
        vehicleType,
        startTime,
        endTime
      });
      navigate('/reservations');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to create reservation.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-2xl mx-auto bg-white p-8 rounded-lg shadow">
      <h2 className="text-2xl font-bold mb-6">Book a Parking Slot</h2>
      {error && <div className="bg-red-100 text-red-700 p-3 rounded mb-4">{error}</div>}
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">Select Slot</label>
          <select 
            required
            value={selectedSlotId}
            onChange={e => setSelectedSlotId(e.target.value)}
            className="mt-1 block w-full rounded border-gray-300 shadow-sm p-2 border focus:ring-primary focus:border-primary"
          >
            <option value="" disabled>Select an available slot</option>
            {slots.map(slot => (
              <option key={slot.id} value={slot.id}>{slot.slotNumber}</option>
            ))}
          </select>
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Vehicle Number</label>
          <input 
            type="text" 
            required
            value={vehicleNumber}
            onChange={e => setVehicleNumber(e.target.value)}
            className="mt-1 block w-full rounded border-gray-300 shadow-sm p-2 border focus:ring-primary focus:border-primary"
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Vehicle Type</label>
          <select 
            value={vehicleType}
            onChange={e => setVehicleType(e.target.value)}
            className="mt-1 block w-full rounded border-gray-300 shadow-sm p-2 border focus:ring-primary focus:border-primary"
          >
            <option value="CAR">Car</option>
            <option value="BIKE">Bike</option>
            <option value="TRUCK">Truck</option>
          </select>
        </div>
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">Start Time</label>
            <input 
              type="datetime-local" 
              required
              value={startTime}
              onChange={e => setStartTime(e.target.value)}
              className="mt-1 block w-full rounded border-gray-300 shadow-sm p-2 border focus:ring-primary focus:border-primary"
            />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700">End Time</label>
            <input 
              type="datetime-local" 
              required
              value={endTime}
              onChange={e => setEndTime(e.target.value)}
              className="mt-1 block w-full rounded border-gray-300 shadow-sm p-2 border focus:ring-primary focus:border-primary"
            />
          </div>
        </div>
        <button 
          type="submit" 
          disabled={loading || slots.length === 0}
          className="w-full flex justify-center py-2 px-4 border border-transparent rounded shadow-sm text-sm font-medium text-white bg-primary hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary disabled:opacity-50 mt-6"
        >
          {loading ? 'Booking...' : 'Confirm Booking'}
        </button>
      </form>
    </div>
  );
};
"""

files["src/pages/MyReservations.tsx"] = """import { useEffect, useState } from 'react';
import api from '../services/api';

interface Reservation {
  id: number;
  slotNumber: string;
  vehicleNumber: string;
  startTime: string;
  endTime: string;
  status: string;
}

export const MyReservations = () => {
  const [reservations, setReservations] = useState<Reservation[]>([]);
  const [loading, setLoading] = useState(true);

  const fetchReservations = async () => {
    try {
      const res = await api.get('/reservations');
      setReservations(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchReservations();
  }, []);

  const handleCancel = async (id: number) => {
    if (!confirm('Are you sure you want to cancel this reservation?')) return;
    try {
      await api.post(`/reservations/${id}/cancel`);
      fetchReservations();
    } catch (err: any) {
      alert(err.response?.data?.message || 'Failed to cancel reservation');
    }
  };

  if (loading) return <div className="text-center py-10">Loading...</div>;

  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">My Reservations</h2>
      <div className="bg-white rounded-lg shadow overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Slot</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Vehicle</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Time</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {reservations.map(res => (
              <tr key={res.id}>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">#{res.id}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm font-semibold">{res.slotNumber}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{res.vehicleNumber}</td>
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
                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                  {res.status === 'ACTIVE' && (
                    <button onClick={() => handleCancel(res.id)} className="text-red-600 hover:text-red-900">Cancel</button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {reservations.length === 0 && <div className="text-center text-gray-500 py-10">You have no reservations.</div>}
      </div>
    </div>
  );
};
"""

files["src/pages/AdminDashboard.tsx"] = """import { useEffect, useState } from 'react';
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
"""

files["src/pages/AdminUsers.tsx"] = """import { useEffect, useState } from 'react';
import api from '../services/api';

interface User {
  id: number;
  name: string;
  email: string;
  role: string;
  registeredAt: string;
}

export const AdminUsers = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const res = await api.get('/admin/users');
        setUsers(res.data);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchUsers();
  }, []);

  if (loading) return <div className="text-center py-10">Loading...</div>;

  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Registered Users</h2>
      <div className="bg-white rounded-lg shadow overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Role</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {users.map(u => (
              <tr key={u.id}>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{u.id}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm font-semibold">{u.name}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{u.email}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm">
                  <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                    u.role === 'ADMIN' ? 'bg-purple-100 text-purple-800' : 'bg-gray-100 text-gray-800'
                  }`}>
                    {u.role}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {users.length === 0 && <div className="text-center text-gray-500 py-10">No users found.</div>}
      </div>
    </div>
  );
};
"""

files["src/pages/AdminReservations.tsx"] = """import { useEffect, useState } from 'react';
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
"""

files["src/pages/AdminSlots.tsx"] = """import { useEffect, useState } from 'react';
import api from '../services/api';

interface Slot {
  id: number;
  slotNumber: string;
  isAvailable: boolean;
  isDisabled: boolean;
}

export const AdminSlots = () => {
  const [slots, setSlots] = useState<Slot[]>([]);
  const [loading, setLoading] = useState(true);
  const [newSlotNumber, setNewSlotNumber] = useState('');

  const fetchSlots = async () => {
    try {
      const res = await api.get('/admin/slots');
      setSlots(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSlots();
  }, []);

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post('/admin/slots', { slotNumber: newSlotNumber });
      setNewSlotNumber('');
      fetchSlots();
    } catch (err: any) {
      alert(err.response?.data?.message || 'Failed to create slot');
    }
  };

  const handleToggleDisable = async (id: number, currentDisabled: boolean) => {
    try {
      if (currentDisabled) {
        await api.put(`/admin/slots/${id}/enable`);
      } else {
        await api.put(`/admin/slots/${id}/disable`);
      }
      fetchSlots();
    } catch (err: any) {
      alert(err.response?.data?.message || 'Failed to update slot status');
    }
  };

  if (loading) return <div className="text-center py-10">Loading...</div>;

  return (
    <div>
      <h2 className="text-2xl font-bold mb-6">Manage Parking Slots</h2>
      
      <div className="bg-white p-6 rounded-lg shadow mb-8 max-w-md">
        <h3 className="text-lg font-semibold mb-4">Add New Slot</h3>
        <form onSubmit={handleCreate} className="flex space-x-2">
          <input
            type="text"
            required
            placeholder="e.g. A-10"
            value={newSlotNumber}
            onChange={e => setNewSlotNumber(e.target.value)}
            className="flex-1 rounded border-gray-300 shadow-sm p-2 border focus:ring-primary focus:border-primary"
          />
          <button type="submit" className="bg-primary text-white px-4 py-2 rounded shadow hover:bg-blue-600">
            Add Slot
          </button>
        </form>
      </div>

      <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-6 gap-4">
        {slots.map(slot => (
          <div 
            key={slot.id} 
            className={`p-4 rounded-lg shadow border-2 flex flex-col justify-between ${
              slot.isDisabled ? 'bg-gray-100 border-gray-300' :
              slot.isAvailable ? 'bg-green-50 border-success' : 'bg-red-50 border-danger'
            }`}
          >
            <div className="text-center mb-4">
              <div className="text-xl font-bold">{slot.slotNumber}</div>
              <div className="text-xs font-semibold uppercase tracking-wider text-gray-600 mt-1">
                {slot.isDisabled ? 'Disabled' : slot.isAvailable ? 'Available' : 'Reserved'}
              </div>
            </div>
            <button
              onClick={() => handleToggleDisable(slot.id, slot.isDisabled)}
              className={`w-full py-1 text-sm rounded text-white font-medium transition ${
                slot.isDisabled ? 'bg-success hover:bg-green-600' : 'bg-gray-600 hover:bg-gray-700'
              }`}
            >
              {slot.isDisabled ? 'Enable' : 'Disable'}
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};
"""

files["Dockerfile"] = """FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json ./
# Assuming we generated node_modules locally or run install
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
"""

files[".env"] = """VITE_API_URL=http://localhost:8080/api/v1
"""

for path, content in files.items():
    write_file(os.path.join(base_dir, path), content)

print("All files created successfully.")
