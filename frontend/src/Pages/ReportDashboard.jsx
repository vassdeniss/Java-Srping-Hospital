import { useEffect, useState } from 'react';
import { useOutletContext } from 'react-router-dom';
import {
  getPatientsByDiagnosis,
  getMostCommonDiagnosis,
} from '../api/reportApi';
import './ReportDashboard.css';
import GpCount from '../components/GpCount';
import PatientList from '../components/PatientList';
import GpPatients from '../components/GpPatients';
import VisitsPerDoctor from '../components/VisitsPerDoctor';

export default function ReportsDashboard() {
  const { isAuth, redirectToLogin } = useOutletContext();
  const [activeReport, setActiveReport] = useState(null);
  const [diagnosisCode, setDiagnosisCode] = useState('');
  const [commonDiagnoses, setCommonDiagnoses] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    if (!isAuth) {
      redirectToLogin();
    }
  }, [isAuth]);

  useEffect(() => {
    if (activeReport && REPORTS[activeReport]) {
      REPORTS[activeReport]();
    }
  }, [activeReport]);

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

  const fetchMostCommonDiagnoses = async () => {
    setIsLoading(true);
    try {
      const data = await getMostCommonDiagnosis();
      setCommonDiagnoses(data);
    } catch (error) {
      console.error('Error fetching common diagnoses:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const REPORTS = {
    'most-common-diagnoses': fetchMostCommonDiagnoses,
  };

  return (
    <div className="reports-container">
      <header className="reports-header">
        <h1 className="reports-title">Medical Analytics Dashboard</h1>
        <p>Generate detailed medical reports and insights</p>
      </header>

      <div className="report-grid">
        <div
          className="report-card"
          onClick={() => setActiveReport('diagnosis-patients')}
        >
          <h3>Patients by Diagnosis</h3>
          <p>View patients with specific diagnosis codes</p>
        </div>
        <div
          className="report-card"
          onClick={() => setActiveReport('most-common-diagnoses')}
        >
          <h3>Common Diagnoses</h3>
          <p>View most frequently occurring diagnoses</p>
        </div>
        <div
          className="report-card"
          onClick={() => setActiveReport('gp-patients')}
        >
          <h3>GP Patient List</h3>
          <p>View all patients assigned to specific GP</p>
        </div>
        <div
          className="report-card"
          onClick={() => setActiveReport('gp-patient-counts')}
        >
          <h3>Patients per GP</h3>
          <p>View patient counts for each GP</p>
        </div>
        <div
          className="report-card"
          onClick={() => setActiveReport('visits-per-doctor')}
        >
          <h3>Visits per Doctor</h3>
          <p>View visit statistics by doctor</p>
        </div>
      </div>

      {isLoading && (
        <div className="loading-state">
          <p>Loading data...</p>
        </div>
      )}

      {activeReport === 'diagnosis-patients' && (
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
            <button
              className="report-button"
              onClick={fetchPatientsByDiagnosis}
            >
              Generate Report
            </button>
          </div>

          {patients.length > 0 ? (
            <PatientList patients={patients} />
          ) : (
            <p className="no-results">
              No patients found matching this diagnosis code
            </p>
          )}
        </div>
      )}

      {activeReport === 'most-common-diagnoses' && (
        <div className="report-content">
          <h2 className="section-title">Most Common Diagnoses</h2>
          <div className="diagnoses-list">
            {commonDiagnoses.map((diagnosis, index) => (
              <div key={diagnosis.code} className="diagnosis-card">
                <div className="diagnosis-rank">#{index + 1}</div>
                <div className="diagnosis-info">
                  <h3 className="diagnosis-name">{diagnosis.name}</h3>
                  <p className="diagnosis-code">{diagnosis.code}</p>
                </div>
                <div className="diagnosis-count">{diagnosis.count} cases</div>
              </div>
            ))}
          </div>
        </div>
      )}

      {activeReport === 'gp-patients' && <GpPatients />}
      {activeReport === 'gp-patient-counts' && <GpCount />}
      {activeReport === 'visits-per-doctor' && <VisitsPerDoctor />}
    </div>
  );
}
