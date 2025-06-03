import { useKeycloak } from '@react-keycloak/web';
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ children, requiredRoles = [] }) => {
  const { keycloak } = useKeycloak();
  const isAuth = keycloak.authenticated;

  if (!isAuth) {
    return keycloak.login();
  }

  const roles = keycloak.tokenParsed?.realm_access?.roles || [];

  if (
    requiredRoles.length > 0 &&
    !requiredRoles.some((role) => roles.includes(role))
  ) {
    return <Navigate to="/unauthorized" replace />;
  }

  return children;
};

export default ProtectedRoute;
