import { useOutletContext } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { getPatient } from '../api/userApi';

import './patientDashboard.css';

const PatientDashboard = () => {
  const { isAuth, id, redirectToLogin } = useOutletContext();
  const [patient, setPatient] = useState(null);

  useEffect(() => {
    const fetchPatientData = async () => {
      if (!isAuth) {
        return redirectToLogin();
      }

      try {
        const data = await getPatient(id);
        setPatient(data);
      } catch (error) {
        console.error(error.message);
      }
    };

    fetchPatientData();
  }, []);

  if (!patient) {
    return <div>Loading...</div>;
  }

  return (
    <div className="dashboard-container">
      <h1 className="dashboard-title">Welcome, {patient.user.username}!</h1>
      <div className="dashboard-section">
        <h2 className="section-title">Your Profile</h2>
        <div className="profile-card">
          <p>
            <strong>Name:</strong> {patient.user.firstName}{' '}
            {patient.user.lastName}
          </p>
          <p>
            <strong>EGN:</strong> {patient.user.egn}
          </p>
          <p>
            <strong>Insurance Status:</strong> Paid
          </p>
          <p>
            <strong>Personal Doctor:</strong> Dr. Smith
          </p>
        </div>
      </div>

      <div className="dashboard-section">
        <h2 className="section-title">Upcoming Appointments</h2>
        <div className="appointment-list">
          <div className="appointment-card">
            <p>
              <strong>Doctor:</strong> Dr. Smith
            </p>
            <p>
              <strong>Date:</strong> 10th April 2025
            </p>
            <p>
              <strong>Time:</strong> 3:00 PM
            </p>
          </div>
        </div>
      </div>

      <div className="dashboard-section">
        <h2 className="section-title">Medical History</h2>
        <div className="history-list">
          <div className="history-card">
            <p>
              <strong>Diagnosis:</strong> Common Cold
            </p>
            <p>
              <strong>Treatment:</strong> Rest and hydration
            </p>
            <p>
              <strong>Sick Leave:</strong> 5 days
            </p>
            <p>
              <strong>Date:</strong> 1st March 2025
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PatientDashboard;
