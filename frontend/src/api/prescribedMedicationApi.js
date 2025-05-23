import apiRequest from './baseApi';

export async function createPrescribedMedication(
  visitId,
  medicationId,
  dosage,
  frequency,
  duration,
  instructions
) {
  return apiRequest('http://localhost:8080/api/prescriptions/create', {
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
