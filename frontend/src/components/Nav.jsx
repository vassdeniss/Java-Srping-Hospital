import { Link } from 'react-router-dom';

import './Nav.css';

const Nav = () => {
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <a href="/" className="navbar-logo">
          MedCare
        </a>
        <div className="nav-menu">
          <Link to="/register" className="nav-link">
            Register
          </Link>
          <Link to="/login" className="nav-link">
            Login
          </Link>
        </div>
      </div>
    </nav>
  );
};

export default Nav;
