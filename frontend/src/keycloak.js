import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: import.meta.env.VITE_KEYCLOAK_URL || 'http://localhost:8081',
  realm: 'hospital',
  clientId: 'hospital-react-client',
});

export default keycloak;
