import apiRequest from './baseApi';

export const getPatientsByDiagnosis = async (code) => {
  return apiRequest(
    `http://localhost:8080/api/reports/diagnoses/${code}/patients`
  );
};

export const getMostCommonDiagnosis = async () => {
  return apiRequest('http://localhost:8080/api/reports/diagnoses/common');
};
