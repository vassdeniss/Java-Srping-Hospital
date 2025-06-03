import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import Root from './Pages/Root';
import HomePage from './Pages/HomePage';

import './App.css';
import PatientDashboard from './Pages/PatientDashboard';
import AdminDashboard from './Pages/AdminDashboard';
import DoctorDashboard from './Pages/DoctorDashboard';
import ReportDashboard from './Pages/ReportDashboard';
import ProtectedRoute from './ProtectedRoute';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Root />,
    children: [
      {
        path: '/',
        element: <HomePage />,
      },
      {
        path: '/dashboard',
        element: (
          <ProtectedRoute requiredRoles={['patient']}>
            <PatientDashboard />
          </ProtectedRoute>
        ),
      },
      {
        path: '/admin/dashboard',
        element: (
          <ProtectedRoute requiredRoles={['admin']}>
            <AdminDashboard />
          </ProtectedRoute>
        ),
      },
      {
        path: '/doctor/dashboard',
        element: (
          <ProtectedRoute requiredRoles={['doctor']}>
            <DoctorDashboard />
          </ProtectedRoute>
        ),
      },
      {
        path: '/reports',
        element: (
          <ProtectedRoute requiredRoles={['admin']}>
            <ReportDashboard />
          </ProtectedRoute>
        ),
      },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
