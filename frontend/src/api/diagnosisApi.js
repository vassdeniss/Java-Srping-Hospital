import apiRequest from './baseApi';

export async function createDiagnosis(visitId, code, name) {
  return apiRequest('/api/diagnosis/create', {
    method: 'POST',
    body: { visitId, code, name },
  });
}
