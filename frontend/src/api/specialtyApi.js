import apiRequest from './baseApi';

export async function getAllSpecialties() {
  return apiRequest('/api/specialties/all');
}
