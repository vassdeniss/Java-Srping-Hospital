import apiRequest from './baseApi';

export async function createDiagnosis(visitId, code, name) {
  return apiRequest('http://localhost:8080/api/diagnosis/create', {
    method: 'POST',
    body: { visitId, code, name },
  });
}
