package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.dto.KeycloakUser;
import denis.f108349.hospital.exception.EntityNotFoundException;
import denis.f108349.hospital.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsersResource usersResource;
    
    @Override
    public Mono<KeycloakUser> getUserById(String id) {
        return Mono.create(sink -> {
            try {
                UserRepresentation user = this.usersResource.get(id).toRepresentation();   
                
                KeycloakUser mappedUser = new KeycloakUser(
                    user.getId(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    extractEgn(user)
                );
                
                sink.success(mappedUser);
            } catch (Exception e) {
                sink.error(new EntityNotFoundException("User not found in Keycloak"));
            }
        });
    }
    
    private String extractEgn(UserRepresentation user) {
        if (user.getAttributes() != null && user.getAttributes().get("egn") != null) {
            return user.getAttributes().get("egn").stream().findFirst().orElse(null);
        }
        return null;
    }
}
