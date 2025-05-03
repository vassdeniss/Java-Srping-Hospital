import apiRequest from './baseApi';

export async function getPatient(id) {
  return apiRequest(`http://localhost:8080/api/patients/${id}`);
}

export async function getAllPatients() {
  return apiRequest('http://localhost:8080/api/patients/all');
}

export async function updatePatientGp(id, doctorId) {
  return apiRequest('http://localhost:8080/api/patients/' + id, {
    method: 'PATCH',
    body: { keycloakId: id, gpDoctorId: doctorId },
  });
}

export async function deletePatient(id) {
  return apiRequest(`http://localhost:8080/api/patients/delete/${id}`, {
    method: 'DELETE',
  });
}
