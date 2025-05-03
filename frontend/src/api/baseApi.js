export default async function apiRequest(
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
