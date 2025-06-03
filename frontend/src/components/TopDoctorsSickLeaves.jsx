import { useState, useEffect } from 'react';
import { getTopDoctorsBySickLeaves } from '../api/reportApi';
import './TopDoctorsSickLeaves.css';

const TopDoctorsSickLeaves = () => {
  const [doctors, setDoctors] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getTopDoctorsBySickLeaves();
        setDoctors(data);
      } catch (err) {
        console.error('Error fetching top doctors:', err);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  return (
    <div className="top-doctors-container">
      <div className="top-doctors-header">
        <h2 className="top-doctors-title">Top Doctors by Sick Leaves Issued</h2>
        <p>Doctors who have issued the most sick leave certificates</p>
      </div>

      {isLoading && (
        <div className="loading-state">Loading doctor statistics...</div>
      )}

      {!isLoading && (
        <div className="doctors-leaderboard">
          {doctors.map((doctor, index) => (
            <div key={doctor.doctor.id} className="doctor-leader-card">
              <div className="leader-rank">{index + 1}</div>
              <div className="doctor-leader-info">
                <h3 className="doctor-leader-name">
                  {doctor.doctor.firstName} {doctor.doctor.lastName}
                </h3>
                <p className="doctor-leader-email">{doctor.doctor.email}</p>
              </div>
              <div className="sick-leave-count">
                <div className="count-number">{doctor.totalSickLeaves}</div>
                <div className="count-label">sick leaves</div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default TopDoctorsSickLeaves;
