import apiRequest from './baseApi';

export async function getAllMedication() {
  return apiRequest('/api/medications/all');
}
