import './Nav.css';

const Nav = ({
  onRegisterClick,
  onLoginClick,
  onLogoutClick,
  onProfileClick,
  isAuthenticated,
}) => {
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <a href="/" className="navbar-logo">
          MedCare
        </a>
        <div className="nav-menu">
          {isAuthenticated ? (
            <>
              <button onClick={onProfileClick} className="nav-link">
                Profile
              </button>
              <button onClick={onLogoutClick} className="nav-link">
                Logout
              </button>
            </>
          ) : (
            <>
              <button onClick={onRegisterClick} className="nav-link">
                Register
              </button>
              <button onClick={onLoginClick} className="nav-link">
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
