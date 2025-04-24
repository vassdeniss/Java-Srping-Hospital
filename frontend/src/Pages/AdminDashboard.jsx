import { useOutletContext } from 'react-router-dom';
import { useEffect, useState } from 'react';
import {
  assignToDoctor,
  createDoctor,
  deletePatient,
  deleteUserFromKeycloak,
  getAllPatients,
  getAllSpecialties,
  updateUserRoleToDoctor,
} from '../api/userApi';

import './adminDashboard.css';
import PromoteModal from '../components/PromoteModal';

const AdminDashboard = () => {
  const { isAuth, redirectToLogin, roles } = useOutletContext();
  const [patients, setPatients] = useState([]);
  const [showPromoteModal, setShowPromoteModal] = useState(false);
  const [selectedPatientId, setSelectedPatientId] = useState(null);
  const [specialties, setSpecialties] = useState([]);
  const [selectedSpecialties, setSelectedSpecialties] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      if (!isAuth || !roles.includes('admin')) {
        return redirectToLogin();
      }

      try {
        const data = await getAllPatients();
        const specialties = await getAllSpecialties();
        setPatients(data);
        setSpecialties(specialties);
      } catch (error) {
        console.error('Error fetching patients:', error);
      }
    };

    fetchData();
  }, [isAuth, roles]);

  const handleDelete = async (patientId) => {
    if (!window.confirm('Are you sure you want to delete this patient?')) {
      return;
    }

    try {
      await deletePatient(patientId);
      await deleteUserFromKeycloak(patientId);
      setPatients((prev) =>
        prev.filter((patient) => patient.user.keycloakId !== patientId)
      );
      alert('Patient deleted successfully');
    } catch (error) {
      console.error('Delete error:', error);
      alert('Error deleting patient');
    }
  };

  const handlePromote = async () => {
    if (selectedSpecialties.length === 0) {
      alert('Please select at least one specialty');
      return;
    }

    try {
      await updateUserRoleToDoctor(selectedPatientId);
      await deletePatient(selectedPatientId);
      await createDoctor(selectedPatientId);
      await assignToDoctor(selectedPatientId, selectedSpecialties);
      setPatients((prev) =>
        prev.filter((patient) => patient.user.keycloakId !== selectedPatientId)
      );
      alert('Patient promoted to doctor with specialties successfully');
      setShowPromoteModal(false);
      setSelectedSpecialties([]);
    } catch (error) {
      console.error('Promotion error:', error);
      alert('Error promoting patient');
    }
  };

  if (!patients) {
    return <div>Loading...</div>;
  }

  return (
    <div className="admin-dashboard-container">
      <h1 className="admin-dashboard-title">Admin Dashboard</h1>
      <div className="admin-dashboard-section">
        <h2 className="admin-section-title">Manage Patients</h2>
        <div className="patients-list">
          {patients.map((patient) => (
            <div key={patient.user.keycloakId} className="patient-card">
              <div className="patient-info">
                <p>
                  <strong>Name:</strong> {patient.user.firstName}{' '}
                  {patient.user.lastName}
                </p>
                <p>
                  <strong>EGN:</strong> {patient.user.egn}
                </p>
                <p>
                  <strong>Email:</strong> {patient.user.email}
                </p>
              </div>
              <div className="button-group">
                <button
                  className="promote-button"
                  onClick={() => {
                    setSelectedPatientId(patient.user.keycloakId);
                    setShowPromoteModal(true);
                  }}
                >
                  Promote to Doctor
                </button>
                <button
                  className="delete-button"
                  onClick={() => handleDelete(patient.user.keycloakId)}
                >
                  Delete Patient
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
      <PromoteModal
        show={showPromoteModal}
        onClose={() => setShowPromoteModal(false)}
        specialties={specialties}
        selectedSpecialties={selectedSpecialties}
        onSpecialtyChange={setSelectedSpecialties}
        onPromote={handlePromote}
      />
    </div>
  );
};

export default AdminDashboard;
