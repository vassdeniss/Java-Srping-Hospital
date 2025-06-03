import { useState } from 'react';
import { getPatientsByDiagnosis } from '../api/reportApi';
import PatientList from './PatientList';
import './PatientDiagnosis.css';

const PatientDiagnosis = () => {
  const [patients, setPatients] = useState([]);
  const [diagnosisCode, setDiagnosisCode] = useState('');
  const [isLoading, setIsLoading] = useState(true);

  const fetchPatientsByDiagnosis = async () => {
    setIsLoading(true);
    try {
      const data = await getPatientsByDiagnosis(diagnosisCode);
      setPatients(data);
    } catch (error) {
      console.error('Error fetching patients:', error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="report-content">
      <h2 className="section-title">Patients by Diagnosis Code</h2>
      <div className="report-controls">
        <input
          type="text"
          className="report-input"
          placeholder="Enter diagnosis code (e.g., J45.901)"
          value={diagnosisCode}
          onChange={(e) => setDiagnosisCode(e.target.value)}
        />
        <button className="report-button" onClick={fetchPatientsByDiagnosis}>
          Generate Report
        </button>
      </div>

      {isLoading && (
        <div className="loading-state">Loading diagnosis statistics...</div>
      )}

      {!isLoading && patients.length > 0 ? (
        <PatientList patients={patients} />
      ) : (
        <p className="no-results">
          No patients found matching this diagnosis code
        </p>
      )}
    </div>
  );
};

export default PatientDiagnosis;
