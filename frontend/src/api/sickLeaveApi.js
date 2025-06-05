import apiRequest from './baseApi';

export async function createSickLeave(visitId, startDate, days) {
  return apiRequest('/api/leaves/create', {
    method: 'POST',
    body: {
      visitId,
      startDate,
      days,
    },
  });
}
