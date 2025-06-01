import { useEffect, useState } from 'react';
import { getAllGps } from '../api/doctorApi';
import { getPatientsByGp } from '../api/reportApi';

import PatientList from './PatientList';

import './GpPatients.css';

const GpPatients = () => {
  const [gpId, setGpId] = useState('');
  const [patients, setPatients] = useState([]);
  const [gps, setGps] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    const fetchAllGps = async () => {
      setIsLoading(true);
      try {
        const data = await getAllGps();
        setGps(data);
      } catch (error) {
        console.error('Error fetching GPs:', error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchAllGps();
  }, []);

  const fetchPatientsByGp = async () => {
    setIsLoading(true);
    try {
      const data = await getPatientsByGp(gpId);
      setPatients(data);
    } catch (error) {
      console.error('Error fetching GP patients:', error);
    } finally {
      setIsLoading(false);
    }
  };

  if (isLoading) return <p>Loading GP patients...</p>;

  return (
    <div className="report-content">
      <h2 className="section-title">Patients by GP</h2>
      <div className="report-controls">
        <select
          className="report-input"
          value={gpId}
          onChange={(e) => setGpId(e.target.value)}
        >
          <option value="">Select GP</option>
          {gps.map((gp) => (
            <option key={gp.doctor.id} value={gp.doctor.id}>
              Dr. {gp.user.firstName} {gp.user.lastName}
            </option>
          ))}
        </select>
        <button className="report-button" onClick={fetchPatientsByGp}>
          Get Patients
        </button>
      </div>

      {patients.length > 0 ? (
        <PatientList patients={patients} />
      ) : (
        <p className="no-results">
          {gpId ? 'No patients found for this GP' : 'Please select a GP first'}
        </p>
      )}
    </div>
  );
};

export default GpPatients;
