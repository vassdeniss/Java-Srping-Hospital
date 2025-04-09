import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import Root from './Pages/Root';
import HomePage from './Pages/HomePage';

import './App.css';
import PatientDashboard from './Pages/PatientDashboard';

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
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
