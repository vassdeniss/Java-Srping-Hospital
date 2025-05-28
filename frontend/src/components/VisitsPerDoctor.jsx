import { useEffect, useState } from 'react';
import { getVisitsPerDoctor } from '../api/reportApi';

import './VisitsPerDoctor.css';

export default function VisitsPerDoctor() {
  const [visitsData, setVisitsData] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const data = await getVisitsPerDoctor();
        setVisitsData(data);
      } catch (err) {
        console.error('Error fetching visits per doctor:', err);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  if (isLoading) return <p>Loading visits data...</p>;

  return (
    <div className="visits-per-doctor-container">
      <div className="visits-per-doctor-header">
        <h2 className="visits-per-doctor-title">Visits per Doctor</h2>
      </div>
      <div className="visits-list">
        {visitsData.map((doctor, index) => (
          <div key={doctor.doctor.keycloakId} className="visit-item">
            <div className="visit-rank">#{index + 1}</div>
            <div className="visit-doctor-info">
              <h3 className="visit-doctor-name">
                Dr. {doctor.doctor.firstName} {doctor.doctor.lastName}
              </h3>
            </div>
            <div className="visit-count">
              <div className="visit-count-number">{doctor.total}</div>
              <div className="visit-count-label">visits</div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
