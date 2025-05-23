import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import Root from './Pages/Root';
import HomePage from './Pages/HomePage';

import './App.css';
import PatientDashboard from './Pages/PatientDashboard';
import AdminDashboard from './Pages/AdminDashboard';
import DoctorDashboard from './Pages/DoctorDashboard';

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
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
