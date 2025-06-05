import apiRequest from './baseApi';

export async function getPatient(id) {
  return apiRequest(`/api/patients/${id}`);
}

export async function getPatientHistory(id) {
  return apiRequest(`/api/patients/${id}/history`);
}

export async function getAllPatients() {
  return apiRequest('/api/patients/all');
}

export async function updatePatient(id, doctorId, healthInsurance) {
  return apiRequest('/api/patients/' + id, {
    method: 'PATCH',
    body: { keycloakId: id, gpDoctorId: doctorId, healthInsurance },
  });
}

export async function deletePatient(id) {
  return apiRequest(`/api/patients/delete/${id}`, {
    method: 'DELETE',
  });
}
