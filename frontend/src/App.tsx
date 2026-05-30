import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { Home } from './pages/Home'
import { Login } from './pages/Login'
import { Register } from './pages/Register'
import { UserDashboard } from './pages/UserDashboard'
import { ParkingSlots } from './pages/ParkingSlots'
import { CreateReservation } from './pages/CreateReservation'
import { MyReservations } from './pages/MyReservations'
import { AdminDashboard } from './pages/AdminDashboard'
import { AdminUsers } from './pages/AdminUsers'
import { AdminReservations } from './pages/AdminReservations'
import { AdminSlots } from './pages/AdminSlots'
import { ProtectedRoute } from './components/ProtectedRoute'
import { AdminRoute } from './components/AdminRoute'
import { UserLayout } from './layouts/UserLayout'
import { AdminLayout } from './layouts/AdminLayout'

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* User Routes */}
        <Route element={<ProtectedRoute><UserLayout /></ProtectedRoute>}>
          <Route path="/dashboard" element={<UserDashboard />} />
          <Route path="/slots" element={<ParkingSlots />} />
          <Route path="/reservations/create" element={<CreateReservation />} />
          <Route path="/reservations" element={<MyReservations />} />
        </Route>

        {/* Admin Routes */}
        <Route element={<AdminRoute><AdminLayout /></AdminRoute>}>
          <Route path="/admin" element={<AdminDashboard />} />
          <Route path="/admin/users" element={<AdminUsers />} />
          <Route path="/admin/reservations" element={<AdminReservations />} />
          <Route path="/admin/slots" element={<AdminSlots />} />
        </Route>
      </Routes>
    </Router>
  )
}

export default App
