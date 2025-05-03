import apiRequest from './baseApi';

export async function getAllSpecialties() {
  return apiRequest('http://localhost:8080/api/specialties/all');
}
