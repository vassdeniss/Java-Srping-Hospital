import { useEffect, useState } from 'react';
import {
  getVisitsInPeriod,
  getVisitsInPeriodByDoctorId,
} from '../api/reportApi';
import './VisitsByPeriod.css';
import { getAllDoctors } from '../api/doctorApi';

const VisitsByPeriod = () => {
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [visits, setVisits] = useState([]);
  const [doctors, setDoctors] = useState([]);
  const [doctorId, setDoctorId] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    const fetchAllDoctors = async () => {
      setIsLoading(true);
      try {
        const data = await getAllDoctors();
        setDoctors(data);
      } catch (error) {
        console.error('Error fetching doctors:', error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchAllDoctors();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!startDate || !endDate) {
      alert('Please select both dates');
      return;
    }

    setIsLoading(true);

    try {
      const start = new Date(startDate);
      const end = new Date(endDate);

      let data;
      if (doctorId) {
        data = await getVisitsInPeriodByDoctorId(doctorId, start, end);
      } else {
        data = await getVisitsInPeriod(start, end);
      }

      setVisits(data);
    } catch (err) {
      console.error('Error fetching visits:', err);
    } finally {
      setIsLoading(false);
    }
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return (
      date.toLocaleDateString() +
      ' ' +
      date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    );
  };

  return (
    <div className="visits-by-period-container">
      <div className="visits-by-period-header">
        <h2 className="visits-by-period-title">Visit History by Period</h2>
        <form onSubmit={handleSubmit}>
          <div className="date-range-selector">
            <div className="date-input-group">
              <label htmlFor="startDate">Start Date</label>
              <input
                type="date"
                id="startDate"
                value={startDate}
                onChange={(e) => setStartDate(e.target.value)}
                max={endDate || undefined}
              />
            </div>
            <div className="date-input-group">
              <label htmlFor="endDate">End Date</label>
              <input
                type="date"
                id="endDate"
                value={endDate}
                onChange={(e) => setEndDate(e.target.value)}
                min={startDate || undefined}
              />
            </div>
            <div className="date-input-group">
              <label>Doctor</label>
              <select
                className="input-list"
                value={doctorId}
                onChange={(e) => setDoctorId(e.target.value)}
              >
                <option value="">Select Doctor</option>
                {doctors.map((doctor) => (
                  <option key={doctor.doctor.id} value={doctor.doctor.id}>
                    Dr. {doctor.user.firstName} {doctor.user.lastName}
                  </option>
                ))}
              </select>
            </div>
            <button type="submit" className="generate-btn">
              Generate Report
            </button>
          </div>
        </form>
      </div>

      {isLoading && (
        <div className="loading-state">Loading visit history...</div>
      )}

      {!isLoading && visits.length === 0 && (
        <div className="no-results">
          No visits found for the selected period
        </div>
      )}

      {!isLoading && visits.length > 0 && (
        <div className="table-container">
          <table className="visits-history-table">
            <thead>
              <tr>
                <th>Date</th>
                <th>Patient</th>
                <th>Doctor</th>
                <th>Diagnosis</th>
                <th>Treatment</th>
                <th>Medication</th>
                <th>Sick Leave</th>
              </tr>
            </thead>
            <tbody>
              {visits.map((visit) => (
                <tr key={`${visit.patientId}-${visit.visitDate}`}>
                  <td className="visit-date">{formatDate(visit.visitDate)}</td>
                  <td>{visit.patientName}</td>
                  <td>{visit.doctorName}</td>
                  <td className="diagnosis-cell" title={visit.diagnosis}>
                    {visit.diagnosis}
                  </td>
                  <td className="treatment-cell">{visit.treatment}</td>
                  <td>
                    {visit.dosage && (
                      <div>
                        <strong>Dosage:</strong> {visit.dosage}
                        <br />
                        <strong>Frequency:</strong> {visit.frequency}
                        <br />
                        <strong>Duration:</strong> {visit.duration}
                      </div>
                    )}
                  </td>
                  <td className="sick-leave-days">
                    {visit.sickLeaveDays ? `${visit.sickLeaveDays} days` : '-'}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default VisitsByPeriod;
