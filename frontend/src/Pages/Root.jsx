import { Outlet } from 'react-router-dom';
import { useKeycloak } from '@react-keycloak/web';
import { createUserInBackend, updateUserRoleToPatient } from '../api/userApi';

import Nav from '../components/Nav';
import Footer from '../components/Footer';
import { useEffect } from 'react';

import './Root.css';

const Root = () => {
  const { keycloak, initialized } = useKeycloak();

  useEffect(() => {
    if (initialized && keycloak.authenticated && keycloak.tokenParsed) {
      const isNew =
        !keycloak.tokenParsed?.realm_access?.roles.includes('patient') &&
        !keycloak.tokenParsed?.realm_access?.roles.includes('doctor');

      const data = {
        keycloakId: keycloak.tokenParsed.sub,
        username: keycloak.tokenParsed.preferred_username,
        firstName: keycloak.tokenParsed.given_name,
        lastName: keycloak.tokenParsed.family_name,
        email: keycloak.tokenParsed.email,
        egn: keycloak.tokenParsed.egn,
      };

      const handleFirstTimeSetup = async () => {
        try {
          if (isNew) {
            await updateUserRoleToPatient(data.keycloakId);
            await createUserInBackend({
              id: data.keycloakId,
            });
          }
        } catch (error) {
          console.error('Error during user setup:', error);
        }
      };

      handleFirstTimeSetup();
    }
  }, [initialized, keycloak.authenticated]);

  if (!initialized) {
    return <div>Loading...</div>;
  }

  function handleLogin() {
    keycloak.login();
  }

  return (
    <div className="root-container">
      <Nav />
      <main className="content-wrapper">
        <Outlet
          context={{
            isAuth: keycloak.authenticated,
            id: keycloak.tokenParsed?.sub,
            redirectToLogin: handleLogin,
            roles: keycloak.tokenParsed?.realm_access?.roles,
          }}
        />
      </main>
      <Footer />
    </div>
  );
};

export default Root;
