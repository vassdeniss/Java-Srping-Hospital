import { useEffect, useState } from 'react';
import { getVisits, updateVisit } from '../api/visitApi';
import { useOutletContext } from 'react-router-dom';
import { getDoctor } from '../api/doctorApi';

import './DoctorDashboard.css';
import { getAllMedication } from '../api/medicationApi';
import { createDiagnosis } from '../api/diagnosisApi';
import { createPrescribedMedication } from '../api/prescribedMedicationApi';
import { createSickLeave } from '../api/sickLeaveApi';

export default function DoctorDashboard() {
  const { isAuth, id, redirectToLogin } = useOutletContext();
  const [doctor, setDoctor] = useState(null);
  const [visits, setVisits] = useState([]);
  const [medication, setMedication] = useState([]);
  const [editingVisitId, setEditingVisitId] = useState(null);
  const [formData, setFormData] = useState({
    diagnosis: { code: '', name: '' },
    medications: [
      {
        medicationId: '',
        dosage: '',
        frequency: '',
        duration: '',
        instructions: '',
      },
    ],
    sickLeave: { startDate: '', days: '' },
  });

  useEffect(() => {
    const fetchData = async () => {
      if (!isAuth) {
        return redirectToLogin();
      }

      try {
        const data = await getDoctor(id);
        setDoctor(data);
        setVisits(await getVisits(data.doctor.id));
        setMedication(await getAllMedication());
      } catch (error) {
        console.error(error.message);
      }
    };

    fetchData();
  }, []);

  const startEdit = (visit) => {
    setEditingVisitId(visit.id);
    setFormData({
      diagnosis: { code: '', name: '' },
      medications: [],
      sickLeave: { startDate: '', days: '' },
    });
  };

  const handleAddMed = () => {
    setFormData((f) => ({
      ...f,
      medications: [
        ...f.medications,
        {
          medicationId: '',
          dosage: '',
          frequency: '',
          duration: '',
          instructions: '',
        },
      ],
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await createDiagnosis(
      editingVisitId,
      formData.diagnosis.code,
      formData.diagnosis.name
    );
    await updateVisit(editingVisitId, true);
    for (const med of formData.medications) {
      if (!med.medicationId) continue;

      await createPrescribedMedication(
        editingVisitId,
        med.medicationId,
        med.dosage,
        med.frequency,
        med.duration,
        med.instructions
      );
    }
    if (formData.sickLeave.startDate && formData.sickLeave.days) {
      await createSickLeave(
        editingVisitId,
        formData.sickLeave.startDate,
        formData.sickLeave.days
      );
    }
    setEditingVisitId(null);
    setVisits(await getVisits(doctor.doctor.id));
  };

  return (
    <div className="dashboard-container">
      <h1 className="dashboard-title">Doctor Dashboard</h1>
      <div className="dashboard-section">
        <h2 className="section-title">Pending Visits</h2>
        {visits.map((v) => (
          <div key={v.id} className="appointment-card">
            <p>
              <strong>Patient:</strong> {v.patientFirstName} {v.patientLastName}
            </p>
            <p>
              <strong>Date:</strong> {new Date(v.visitDate).toLocaleString()}
            </p>
            <button onClick={() => startEdit(v)}>Resolve</button>
            {editingVisitId === v.id && (
              <form onSubmit={handleSubmit} className="appointment-modal">
                <h3>Complete Visit</h3>
                <div className="form-group">
                  <label>Diagnosis Code</label>
                  <input
                    value={formData.diagnosis.code}
                    onChange={(e) =>
                      setFormData((f) => ({
                        ...f,
                        diagnosis: { ...f.diagnosis, code: e.target.value },
                      }))
                    }
                    required
                  />
                  <label>Diagnosis Name</label>
                  <input
                    value={formData.diagnosis.name}
                    onChange={(e) =>
                      setFormData((f) => ({
                        ...f,
                        diagnosis: { ...f.diagnosis, name: e.target.value },
                      }))
                    }
                    required
                  />
                </div>
                {formData.medications.map((med, i) => (
                  <div key={i} className="form-group">
                    <label>Medication #{i + 1}</label>
                    <select
                      value={med.medicationId}
                      onChange={(e) => {
                        const meds = [...formData.medications];
                        meds[i].medicationId = e.target.value;
                        setFormData((f) => ({ ...f, medications: meds }));
                      }}
                    >
                      <option value="">Select medicine</option>
                      {medication.map((med) => (
                        <option key={med.id} value={med.id}>
                          {med.name}
                        </option>
                      ))}
                    </select>
                    {med.medicationId && (
                      <>
                        <input
                          placeholder="Dosage"
                          value={med.dosage}
                          onChange={(e) => {
                            const meds = [...formData.medications];
                            meds[i].dosage = e.target.value;
                            setFormData((f) => ({ ...f, medications: meds }));
                          }}
                          required
                        />
                        <input
                          placeholder="Frequency"
                          value={med.frequency}
                          onChange={(e) => {
                            const meds = [...formData.medications];
                            meds[i].frequency = e.target.value;
                            setFormData((f) => ({ ...f, medications: meds }));
                          }}
                        />
                        <input
                          placeholder="Duration"
                          value={med.duration}
                          onChange={(e) => {
                            const meds = [...formData.medications];
                            meds[i].duration = e.target.value;
                            setFormData((f) => ({ ...f, medications: meds }));
                          }}
                        />
                        <input
                          placeholder="Instructions"
                          value={med.instructions}
                          onChange={(e) => {
                            const meds = [...formData.medications];
                            meds[i].instructions = e.target.value;
                            setFormData((f) => ({ ...f, medications: meds }));
                          }}
                        />
                        <button
                          type="button"
                          onClick={() => {
                            const meds = formData.medications.filter(
                              (_, index) => index !== i
                            );
                            setFormData((f) => ({ ...f, medications: meds }));
                          }}
                        >
                          Remove Medication
                        </button>
                      </>
                    )}
                  </div>
                ))}
                <button type="button" onClick={handleAddMed}>
                  Add Medication
                </button>
                <div className="form-group">
                  <label>Sick Leave Start</label>
                  <input
                    type="date"
                    value={formData.sickLeave.startDate}
                    onChange={(e) =>
                      setFormData((f) => ({
                        ...f,
                        sickLeave: {
                          ...f.sickLeave,
                          startDate: e.target.value,
                        },
                      }))
                    }
                  />
                  <label>Days</label>
                  <input
                    type="number"
                    min="0"
                    value={formData.sickLeave.days}
                    onChange={(e) =>
                      setFormData((f) => ({
                        ...f,
                        sickLeave: { ...f.sickLeave, days: e.target.value },
                      }))
                    }
                  />
                </div>
                <button type="submit" className="confirm-btn">
                  Submit
                </button>
                <button
                  type="button"
                  className="cancel-btn"
                  onClick={() => setEditingVisitId(null)}
                >
                  Cancel
                </button>
              </form>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}
