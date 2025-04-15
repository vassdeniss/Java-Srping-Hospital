export async function createUserInBackend(data) {
  const response = await fetch('http://localhost:8080/api/patients/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`Backend error: ${response.status} - ${errorText}`);
  }

  return response.json();
}

export async function updateUserRoleToPatient(keycloakId) {
  const token = await getAdminToken();
  await fetch(
    `http://localhost:8081/admin/realms/hospital/users/${keycloakId}/role-mappings/realm`,
    {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify([
        {
          id: '024aa226-cb4d-46ad-86a6-199ddcf7b86a',
          name: 'patient',
        },
      ]),
    }
  );
}

export async function getPatient(id) {
  const response = await fetch(`http://localhost:8080/api/patients/${id}`);

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`Backend error: ${response.status} - ${errorText}`);
  }

  return response.json();
}

export async function getAllPatients() {
  const response = await fetch('http://localhost:8080/api/patients/all');

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`Backend error: ${response.status} - ${errorText}`);
  }

  return response.json();
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
