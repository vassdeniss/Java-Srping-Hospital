export async function createUserInBackend(data) {
  return apiRequest('http://localhost:8080/api/patients/create', {
    method: 'POST',
    body: data,
  });
}

export async function updateUserRoleToPatient(keycloakId) {
  const token = await getAdminToken();
  return apiRequest(
    `http://localhost:8081/admin/realms/hospital/users/${keycloakId}/role-mappings/realm`,
    {
      method: 'POST',
      headers: { Authorization: `Bearer ${token}` },
      body: [{ id: '024aa226-cb4d-46ad-86a6-199ddcf7b86a', name: 'patient' }],
    }
  );
}

export async function updateUserRoleToDoctor(keycloakId) {
  const token = await getAdminToken();
  // Remove patient role
  await apiRequest(
    `http://localhost:8081/admin/realms/hospital/users/${keycloakId}/role-mappings/realm`,
    {
      method: 'DELETE',
      headers: { Authorization: `Bearer ${token}` },
      body: [{ id: '024aa226-cb4d-46ad-86a6-199ddcf7b86a', name: 'patient' }],
    }
  );
  // Add doctor role
  await apiRequest(
    `http://localhost:8081/admin/realms/hospital/users/${keycloakId}/role-mappings/realm`,
    {
      method: 'POST',
      headers: { Authorization: `Bearer ${token}` },
      body: [{ id: 'f5f6845a-d90d-4ed7-8252-97a8f50770f6', name: 'doctor' }],
    }
  );
}

export async function deleteUserFromKeycloak(keycloakId) {
  const token = await getAdminToken();
  return apiRequest(
    `http://localhost:8081/admin/realms/hospital/users/${keycloakId}`,
    {
      method: 'DELETE',
      headers: { Authorization: `Bearer ${token}` },
    }
  );
}

export async function getPatient(id) {
  return apiRequest(`http://localhost:8080/api/patients/${id}`);
}

export async function getAllPatients() {
  return apiRequest('http://localhost:8080/api/patients/all');
}

export async function deletePatient(id) {
  return apiRequest(`http://localhost:8080/api/patients/delete/${id}`, {
    method: 'DELETE',
  });
}

export async function getAllSpecialties() {
  return apiRequest('http://localhost:8080/api/specialties/all');
}

export async function createDoctor(patientId) {
  return apiRequest('http://localhost:8080/api/doctors/create', {
    method: 'POST',
    body: { id: patientId },
  });
}

export async function assignToDoctor(doctorId, specialties) {
  return apiRequest(`http://localhost:8080/api/doctors/assign/${doctorId}`, {
    method: 'POST',
    body: specialties,
  });
}

async function getAdminToken() {
  const clientId = 'hospital-react-client';
  const adminUsername = import.meta.env.VITE_ADMIN_USERNAME;
  const adminPassword = import.meta.env.VITE_ADMIN_PASSWORD;

  const response = await fetch(
    `http://localhost:8081/realms/hospital/protocol/openid-connect/token`,
    {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({
        client_id: clientId,
        username: adminUsername,
        password: adminPassword,
        grant_type: 'password',
      }),
    }
  );

  const data = await response.json();
  return data.access_token;
}

async function apiRequest(
  url,
  { method = 'GET', body = null, headers = {} } = {}
) {
  const defaultHeaders = {
    'Content-Type': 'application/json',
    ...headers,
  };

  const options = {
    method,
    headers: defaultHeaders,
    ...(body && { body: JSON.stringify(body) }),
  };

  const response = await fetch(url, options);

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`Error: ${response.status} - ${errorText}`);
  }

  if (response.status !== 204) {
    return response.json();
  }
}
