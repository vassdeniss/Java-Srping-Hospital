package denis.f108349.hospital.service;

import denis.f108349.hospital.dto.KeycloakUser;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<KeycloakUser> getUserById(String id);
}
