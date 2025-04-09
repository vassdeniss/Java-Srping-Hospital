import { Outlet } from 'react-router-dom';
import { useKeycloak } from '@react-keycloak/web';
import { createUserInBackend, updateUserRoleToPatient } from '../api/userApi';

import Nav from '../components/Nav';
import Footer from '../components/Footer';
import { useEffect } from 'react';

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
            await createUserInBackend(data);
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

  function handleRegister() {
    keycloak.register();
  }

  function handleLogin() {
    keycloak.login();
  }

  function handleLogout() {
    keycloak.logout();
  }

  function handleProfile() {
    keycloak.accountManagement();
  }

  return (
    <>
      <Nav
        onRegisterClick={handleRegister}
        onLoginClick={handleLogin}
        onLogoutClick={handleLogout}
        onProfileClick={handleProfile}
        isAuthenticated={keycloak.authenticated}
      />
      <Outlet
        context={{
          isAuth: keycloak.authenticated,
          redirectToLogin: handleLogin,
        }}
      />
      <Footer />
    </>
  );
};

export default Root;
