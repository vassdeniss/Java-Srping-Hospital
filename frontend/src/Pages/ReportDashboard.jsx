import { useState } from 'react';
import GpCount from '../components/GpCount';
import GpPatients from '../components/GpPatients';
import VisitsPerDoctor from '../components/VisitsPerDoctor';
import CommonDiagnosis from '../components/CommonDiagnosis';
import VisitsByPeriod from '../components/VisitsByPeriod';
import BusiestSickLeaveMonth from '../components/BusiestSickLeaveMonth';
import TopDoctorsSickLeaves from '../components/TopDoctorsSickLeaves';
import PatientDiagnosis from '../components/PatientDiagnosis';

import './ReportDashboard.css';

export default function ReportsDashboard() {
  const [activeReport, setActiveReport] = useState(null);

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
        <div
          className="report-card"
          onClick={() => setActiveReport('visits-by-period')}
        >
          <h3>Visits by Period</h3>
          <p>View visit statistics for specific time periods</p>
        </div>
        <div
          className="report-card"
          onClick={() => setActiveReport('busiest-sick-leave')}
        >
          <h3>Busiest Sick Leave Month</h3>
          <p>View the month with highest sick leave issuance</p>
        </div>
        <div
          className="report-card"
          onClick={() => setActiveReport('top-doctors-sick-leaves')}
        >
          <h3>Top Doctors by Sick Leaves</h3>
          <p>View doctors who issue the most sick leaves</p>
        </div>
      </div>

      {activeReport === 'diagnosis-patients' && <PatientDiagnosis />}
      {activeReport === 'most-common-diagnoses' && <CommonDiagnosis />}
      {activeReport === 'gp-patients' && <GpPatients />}
      {activeReport === 'gp-patient-counts' && <GpCount />}
      {activeReport === 'visits-per-doctor' && <VisitsPerDoctor />}
      {activeReport === 'visits-by-period' && <VisitsByPeriod />}
      {activeReport === 'busiest-sick-leave' && <BusiestSickLeaveMonth />}
      {activeReport === 'top-doctors-sick-leaves' && <TopDoctorsSickLeaves />}
    </div>
  );
}
