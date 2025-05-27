import './PatientList.css';

const PatientList = ({ patients }) => {
  if (patients.length === 0) {
    return <p className="no-results">No patients found</p>;
  }

  return (
    <div className="patients-grid">
      {patients.map((patient) => (
        <div key={patient.patient.id} className="patient-card">
          <div className="patient-header">
            <div className="patient-avatar">
              {patient.user.firstName[0]}
              {patient.user.lastName[0]}
            </div>
            <div className="patient-info">
              <h4>
                {patient.user.firstName} {patient.user.lastName}
              </h4>
              <p>{patient.user.email}</p>
            </div>
          </div>
          <div className="patient-details">
            <div className="detail-row">
              <span className="detail-label">EGN:</span>
              <span className="detail-value">{patient.user.egn}</span>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default PatientList;
