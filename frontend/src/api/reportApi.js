import apiRequest from './baseApi';

export const getPatientsByDiagnosis = async (code) => {
  return apiRequest(
    `/api/reports/diagnoses/${code}/patients`
  );
};

export const getMostCommonDiagnosis = async () => {
  return apiRequest('/api/reports/diagnoses/common');
};

export const getPatientsByGp = async (gpId) => {
  return apiRequest(`/api/reports/gps/${gpId}/patients`);
};

export const getPatientsByGpCount = async () => {
  return apiRequest('/api/reports/gps/count');
};

export const getVisitsPerDoctor = async () => {
  return apiRequest('/api/reports/doctors/visits');
};

export const getVisitsInPeriod = async (startDate, endDate) => {
  const queryParams = new URLSearchParams({
    startDate: startDate.toISOString(),
    endDate: endDate.toISOString(),
  });

  return apiRequest(`/api/reports/doctors/visits/period?${queryParams}`);
};

export const getVisitsInPeriodByDoctorId = async (id, startDate, endDate) => {
  const queryParams = new URLSearchParams({
    startDate: startDate.toISOString(),
    endDate: endDate.toISOString(),
  });

  return apiRequest(`/api/reports/doctors/visits/period/${id}?${queryParams}`);
};

export const getBusiestSickLeaveMonth = async () => {
  return apiRequest('/leaves/most-issued-month');
};

export const getTopDoctorsBySickLeaves = async () => {
  return apiRequest('/api/reports/leaves/top-doctors');
};
