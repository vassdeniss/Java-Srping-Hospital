import apiRequest from './baseApi';

export async function createPrescribedMedication(
  visitId,
  medicationId,
  dosage,
  frequency,
  duration,
  instructions
) {
  return apiRequest('/api/prescriptions/create', {
    method: 'POST',
    body: {
      visitId,
      medicationId,
      dosage,
      frequency,
      duration,
      instructions,
    },
  });
}
