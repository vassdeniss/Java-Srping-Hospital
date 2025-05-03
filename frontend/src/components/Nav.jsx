import { useKeycloak } from '@react-keycloak/web';
import { useNavigate } from 'react-router-dom';

import './Nav.css';

const Nav = () => {
  const navigate = useNavigate();
  const { keycloak } = useKeycloak();
  const roles = keycloak.tokenParsed?.realm_access?.roles || [];

  function handleAdmin() {
    navigate('/admin/dashboard');
  }

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <a href="/" className="navbar-logo">
          MedCare
        </a>
        <div className="nav-menu">
          {keycloak.authenticated ? (
            <>
              {roles.includes('admin') && (
                <button onClick={handleAdmin} className="nav-link">
                  Admin Dashboard
                </button>
              )}
              {roles.includes('patient') && (
                <button
                  onClick={() => navigate('/dashboard')}
                  className="nav-link"
                >
                  Dashboard
                </button>
              )}
              <button
                onClick={() => keycloak.accountManagement()}
                className="nav-link"
              >
                Profile
              </button>
              <button onClick={() => keycloak.logout()} className="nav-link">
                Logout
              </button>
            </>
          ) : (
            <>
              <button onClick={() => keycloak.register()} className="nav-link">
                Register
              </button>
              <button onClick={() => keycloak.login()} className="nav-link">
                Login
              </button>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Nav;
