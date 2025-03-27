import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: 'http://localhost:8081',
  realm: 'hospital',
  clientId: 'hospital-react-client',
});

export default keycloak;
