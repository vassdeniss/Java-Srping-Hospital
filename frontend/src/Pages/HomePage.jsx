import './HomePage.css';

const HomePage = () => {
  return (
    <div className="home-container">
      <div className="hero-section">
        <h1>Welcome to MedCare</h1>
        <p>Your Complete Healthcare Solution</p>
      </div>

      <div className="features">
        <div className="feature-card">
          <h2>For Patients</h2>
          <p>
            Book appointments, view medical records, and manage prescriptions
          </p>
        </div>

        <div className="feature-card">
          <h2>For Doctors</h2>
          <p>
            Manage appointments, access patient history, and write prescriptions
          </p>
        </div>
      </div>
    </div>
  );
};

export default HomePage;
