package denis.f108349.hospital.service;

import denis.f108349.hospital.dto.UserRegistrationRequest;
import denis.f108349.hospital.data.model.User;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {
    Mono<User> createUser(UserRegistrationRequest request);
    
    Mono<User> getUserById(UUID id);
}
