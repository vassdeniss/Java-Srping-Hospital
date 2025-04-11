package denis.f108349.hospital.configuration;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;
    
    @Value("${keycloak.client-secret}")
    private String clientSecret;
    
    @Value("${keycloak.username}")
    private String keycloakUsername;

    @Value("${keycloak.password}")
    private String keycloakPassword;

    @Bean
    public Keycloak keycloak() {
        return Keycloak.getInstance(
                this.keycloakServerUrl,
                this.realm,
                this.keycloakUsername,
                this.keycloakPassword,
                this.clientId,
                this.clientSecret
        );
    }

    @Bean
    public UsersResource usersResource(Keycloak keycloak) {
        return keycloak.realm(this.realm).users();
    }
}
