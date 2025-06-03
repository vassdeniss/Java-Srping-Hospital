import keycloak from '../keycloak';

export default async function apiRequest(
  url,
  { method = 'GET', body = null, headers = {} } = {}
) {
  try {
    await keycloak.updateToken(5);
  } catch (err) {
    console.error('Token refresh failed', err);
    throw new Error('Unauthorized');
  }

  const defaultHeaders = {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${keycloak.token}`,

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
