import { Outlet } from 'react-router-dom';
import { useKeycloak } from '@react-keycloak/web';

import Nav from '../components/Nav';
import Footer from '../components/Footer';

const Root = () => {
  const { keycloak, initialized } = useKeycloak();

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
      <Outlet />
      <Footer />
    </>
  );
};

export default Root;
