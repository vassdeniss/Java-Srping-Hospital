import apiRequest from './baseApi';

export async function createUserInBackend(data) {
  return apiRequest('/api/patients/create', {
    method: 'POST',
    body: data,
  });
}

export async function updateUserRoleToPatient(keycloakId) {
  const token = await getAdminToken();
  return apiRequest(
    `/admin/realms/hospital/users/${keycloakId}/role-mappings/realm`,
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
    `/admin/realms/hospital/users/${keycloakId}/role-mappings/realm`,
    {
      method: 'DELETE',
      headers: { Authorization: `Bearer ${token}` },
      body: [{ id: '024aa226-cb4d-46ad-86a6-199ddcf7b86a', name: 'patient' }],
    }
  );
  // Add doctor role
  await apiRequest(
    `/admin/realms/hospital/users/${keycloakId}/role-mappings/realm`,
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
    `/admin/realms/hospital/users/${keycloakId}`,
    {
      method: 'DELETE',
      headers: { Authorization: `Bearer ${token}` },
    }
  );
}

async function getAdminToken() {
  const clientId = 'hospital-react-client';
  const adminUsername = import.meta.env.VITE_ADMIN_USERNAME;
  const adminPassword = import.meta.env.VITE_ADMIN_PASSWORD;

  const response = await fetch(
    `/realms/hospital/protocol/openid-connect/token`,
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
