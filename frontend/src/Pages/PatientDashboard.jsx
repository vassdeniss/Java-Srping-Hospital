import { useOutletContext } from 'react-router-dom';
import { useEffect, useState } from 'react';

import './patientDashboard.css';
import { getAllGps } from '../api/doctorApi';
import { getPatient, updatePatient } from '../api/patientApi';
import { createVisit, getVisits } from '../api/visitApi';

const PatientDashboard = () => {
  const { isAuth, id, redirectToLogin } = useOutletContext();
  const [patient, setPatient] = useState(null);
  const [showDoctorsList, setShowDoctorsList] = useState(false);
  const [doctors, setDoctors] = useState([]);
  const [showAppointmentModal, setShowAppointmentModal] = useState(false);
  const [selectedDoctorId, setSelectedDoctorId] = useState('');
  const [selectedDateTime, setSelectedDateTime] = useState('');
  const [appointments, setAppointments] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      if (!isAuth) {
        return redirectToLogin();
      }

      try {
        const data = await getPatient(id);
        setPatient(data);
        setDoctors(await getAllGps());
        setAppointments(await getVisits(data.patient.id));
      } catch (error) {
        console.error(error.message);
      }
    };

    fetchData();
  }, []);

  const handleSelectDoctor = async (doctorId) => {
    try {
      await updatePatient(id, doctorId, patient.patient.healthInsurance);
      setPatient(await getPatient(id));
      setShowDoctorsList(false);
    } catch (error) {
      console.error('Failed to update doctor:', error);
    }
  };

  const handleBookAppointment = async (e) => {
    e.preventDefault();
    try {
      await createVisit(patient.patient.id, selectedDoctorId, selectedDateTime);
      setAppointments(await getVisits(patient.patient.id));
      setShowAppointmentModal(false);
    } catch (error) {
      console.error('Failed to create appointment:', error);
    }
  };

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
            <strong>Insurance Status:</strong>{' '}
            {patient.patient.healthInsurance ? 'Paid' : 'Not Paid'}
          </p>
          <p>
            <strong>Personal Doctor:</strong>
            {patient.doctor
              ? ` Dr. ${patient.doctor.firstName} ${patient.doctor.lastName}`
              : ' Not assigned'}
            <button
              className="change-doctor-btn"
              onClick={() => setShowDoctorsList(!showDoctorsList)}
            >
              Change
            </button>
          </p>
          {showDoctorsList && (
            <div className="doctors-list">
              {doctors.map((doctor) => (
                <div
                  key={doctor.doctor.keycloakId}
                  className="doctor-item"
                  onClick={() => handleSelectDoctor(doctor.doctor.id)}
                >
                  Dr. {doctor.user.firstName} {doctor.user.lastName}
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
      <div className="dashboard-section">
        <h2 className="section-title">
          Upcoming Appointments
          <button
            className="book-appointment-btn"
            onClick={() => setShowAppointmentModal(true)}
          >
            Book New Appointment
          </button>
        </h2>
        <div className="appointment-list">
          {appointments.map((appointment, idx) => (
            <div className="appointment-card" key={idx}>
              <p>
                <strong>Doctor:</strong> Dr. {appointment.doctorFirstName}{' '}
                {appointment.doctorLastName}
              </p>
              <p>
                <strong>Date:</strong>{' '}
                {new Date(appointment.visitDate).toLocaleDateString()}
              </p>
              <p>
                <strong>Time:</strong>{' '}
                {new Date(appointment.visitDate).toLocaleTimeString()}
              </p>
            </div>
          ))}
        </div>
        {showAppointmentModal && (
          <div className="modal-backdrop">
            <div className="appointment-modal">
              <h3>Book New Appointment</h3>
              <form onSubmit={handleBookAppointment}>
                <div className="form-group">
                  <label>Select Doctor:</label>
                  <select
                    value={selectedDoctorId}
                    onChange={(e) => setSelectedDoctorId(e.target.value)}
                    required
                  >
                    <option value="">Choose a doctor</option>
                    {doctors.map((doctor) => (
                      <option key={doctor.doctor.id} value={doctor.doctor.id}>
                        Dr. {doctor.user.firstName} {doctor.user.lastName}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="form-group">
                  <label>Select Date & Time:</label>
                  <input
                    type="datetime-local"
                    value={selectedDateTime}
                    onChange={(e) => setSelectedDateTime(e.target.value)}
                    required
                  />
                </div>
                <div className="modal-actions">
                  <button
                    type="button"
                    className="cancel-btn"
                    onClick={() => setShowAppointmentModal(false)}
                  >
                    Cancel
                  </button>
                  <button type="submit" className="confirm-btn">
                    Book Appointment
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
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
