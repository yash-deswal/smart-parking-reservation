import { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

export const ProtectedRoute = ({ children }: { children: JSX.Element }) => {
  const { token, role } = useContext(AuthContext);

  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // If user tries to access /dashboard but is admin, redirect to admin
  if (role === 'ADMIN' && window.location.pathname.startsWith('/dashboard')) {
    return <Navigate to="/admin" replace />;
  }

  return children;
};
