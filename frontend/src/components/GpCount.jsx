import { useEffect, useState } from 'react';

import './GpCount.css';
import { getPatientsByGpCount } from '../api/patientApi';

const GpCount = () => {
  const [gpPatientCounts, setGpPatientCounts] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    const fetchGpPatientCounts = async () => {
      setIsLoading(true);
      try {
        const data = await getPatientsByGpCount();
        setGpPatientCounts(data);
      } catch (error) {
        console.error('Error fetching GP patient counts:', error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchGpPatientCounts();
  }, []);

  if (isLoading) return <p>Loading GP patient counts...</p>;

  return (
    <div className="report-content">
      <h2 className="section-title">Patients per GP</h2>
      <div className="gp-counts-grid">
        {gpPatientCounts.map((gp, index) => (
          <div key={gp.doctor.keycloakId} className="gp-count-card">
            <div className="gp-rank">#{index + 1}</div>
            <div className="gp-info">
              <h3 className="gp-name">
                Dr. {gp.doctor.firstName} {gp.doctor.lastName}
              </h3>
            </div>
            <div className="patient-count">
              <span className="count-number">{gp.total}</span>
              <span className="count-label">patients</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default GpCount;
