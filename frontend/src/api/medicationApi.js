import apiRequest from './baseApi';

export async function getAllMedication() {
  return apiRequest('http://localhost:8080/api/medications/all');
}
