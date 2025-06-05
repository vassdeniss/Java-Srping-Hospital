import apiRequest from './baseApi';

export async function getAllDoctors() {
  return apiRequest('/api/doctors/all');
}

export async function getDoctor(id) {
  return apiRequest('/api/doctors/' + id);
}

export async function getAllGps() {
  return apiRequest('/api/doctors/all?gp=true');
}

export async function getDoctorHistory(id) {
  return apiRequest(`/api/doctors/${id}/history`);
}

export async function createDoctor(patientId) {
  return apiRequest('/api/doctors/create', {
    method: 'POST',
    body: { id: patientId },
  });
}

export async function assignToDoctor(doctorId, specialties) {
  return apiRequest(`/api/doctors/assign/${doctorId}`, {
    method: 'POST',
    body: specialties,
  });
}
