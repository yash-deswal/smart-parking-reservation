import { useEffect, useState } from 'react';
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
