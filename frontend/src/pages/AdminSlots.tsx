import { useEffect, useState } from 'react';
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
