import './MedicalHistory.css';

const MedicalHistory = ({ history }) => {
  return (
    <div className="history-list">
      {history.map((entry, index) => (
        <div className="history-card" key={index}>
          <p>
            <strong>Name:</strong> {entry.name}
          </p>
          <p>
            <strong>Diagnosis:</strong> {entry.diagnosis}
          </p>
          <p>
            <strong>Treatment:</strong> {entry.treatment}
          </p>
          {entry.dosage && (
            <p>
              <strong>Dosage:</strong> {entry.dosage}
            </p>
          )}
          {entry.frequency && (
            <p>
              <strong>Frequency:</strong> {entry.frequency}
            </p>
          )}
          {entry.duration && (
            <p>
              <strong>Duration:</strong> {entry.duration}
            </p>
          )}
          {entry.sickLeaveDays && (
            <p>
              <strong>Sick Leave:</strong> {entry.sickLeaveDays} days
            </p>
          )}
          <p>
            <strong>Date:</strong>{' '}
            {new Date(entry.visitDate).toLocaleDateString()}{' '}
            {new Date(entry.visitDate).toLocaleTimeString()}
          </p>
        </div>
      ))}
    </div>
  );
};

export default MedicalHistory;
