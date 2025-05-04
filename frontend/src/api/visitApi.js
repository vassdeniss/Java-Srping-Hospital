import apiRequest from './baseApi';

export async function createVisit(patientId, doctorId, date) {
  return apiRequest('http://localhost:8080/api/visits/create', {
    method: 'POST',
    body: { patientId, doctorId, visitDate: date },
  });
}

export async function getVisits(id) {
  return apiRequest('http://localhost:8080/api/visits/all/' + id);
}
