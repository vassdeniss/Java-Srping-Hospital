import apiRequest from './baseApi';

export async function createVisit(patientId, doctorId, date) {
  return apiRequest('/api/visits/create', {
    method: 'POST',
    body: { patientId, doctorId, visitDate: date },
  });
}

export async function getVisits(id) {
  return apiRequest('/api/visits/all/' + id);
}

export async function updateVisit(id, isResolved) {
  return apiRequest('/api/visits/' + id, {
    method: 'PATCH',
    body: JSON.stringify(isResolved),
  });
}
