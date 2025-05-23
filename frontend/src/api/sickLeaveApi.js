import apiRequest from './baseApi';

export async function createSickLeave(visitId, startDate, days) {
  return apiRequest('http://localhost:8080/api/leaves/create', {
    method: 'POST',
    body: {
      visitId,
      startDate,
      days,
    },
  });
}
