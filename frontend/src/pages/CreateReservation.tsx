import { useState, useEffect } from 'react';
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
