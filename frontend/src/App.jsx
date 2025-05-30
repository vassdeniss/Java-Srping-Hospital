import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import Root from './Pages/Root';
import HomePage from './Pages/HomePage';

import './App.css';
import PatientDashboard from './Pages/PatientDashboard';
import AdminDashboard from './Pages/AdminDashboard';
import DoctorDashboard from './Pages/DoctorDashboard';
import ReportDashboard from './Pages/ReportDashboard';

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
        element: <PatientDashboard />,
      },
      {
        path: '/admin/dashboard',
        element: <AdminDashboard />,
      },
      {
        path: '/doctor/dashboard',
        element: <DoctorDashboard />,
      },
      {
        path: '/reports',
        element: <ReportDashboard />,
      },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
