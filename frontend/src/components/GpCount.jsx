import './GpCount.css';

const GpCount = ({ gpPatientCounts }) => {
  return (
    <div className="report-content">
      <h2 className="section-title">Patients per GP</h2>
      <div className="gp-counts-grid">
        {gpPatientCounts.map((gp, index) => (
          <div key={gp.doctor.doctorId} className="gp-count-card">
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
