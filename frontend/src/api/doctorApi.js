import apiRequest from './baseApi';

export async function getAllDoctors() {
  return apiRequest('http://localhost:8080/api/doctors/all');
}

export async function getDoctor(id) {
  return apiRequest('http://localhost:8080/api/doctors/' + id);
}

export async function getAllGps() {
  return apiRequest('http://localhost:8080/api/doctors/all?gp=true');
}

export async function createDoctor(patientId) {
  return apiRequest('http://localhost:8080/api/doctors/create', {
    method: 'POST',
    body: { id: patientId },
  });
}

export async function assignToDoctor(doctorId, specialties) {
  return apiRequest(`http://localhost:8080/api/doctors/assign/${doctorId}`, {
    method: 'POST',
    body: specialties,
  });
}
