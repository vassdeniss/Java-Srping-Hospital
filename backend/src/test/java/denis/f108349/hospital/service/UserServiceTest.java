package denis.f108349.hospital.service;

import denis.f108349.hospital.data.repo.UserRepository;
import denis.f108349.hospital.dto.UserRegistrationRequest;
import denis.f108349.hospital.data.model.User;
import denis.f108349.hospital.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldReturnUser_WhenValidRequest() {
        // Arrange
        UserRegistrationRequest request = new UserRegistrationRequest("keycloakId",
            "test@email.com", "testUser", "First", "Last", "7501020018");
        User user = new User("keycloakId", "test@email.com", "testUser", "First", "Last", "7501020018");

        when(this.userRepository.save(any(User.class))).thenReturn(Mono.just(user));
        
        // Act
        Mono<User> result = this.userService.createUser(request);

        // Assert
        StepVerifier.create(result)
            .expectNextMatches(createdUser -> createdUser.getKeycloakId().equals("keycloakId"))
            .verifyComplete();

        verify(this.userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    void getUserById_ShouldReturnUser_WhenValidId() {
        // Arrange
        User user = new User("keycloakId", "test@email.com", "testUser", "First", "Last", "7501020018");
        UUID id = UUID.randomUUID();
        
        when(this.userRepository.findById(id)).thenReturn(Mono.just(user));
        
        // Act
        Mono<User> result = this.userService.getUserById(id);
        
        // Act
        StepVerifier.create(result)
                .expectNextMatches(createdUser -> createdUser.getKeycloakId().equals("keycloakId"))
                .verifyComplete();
        
        verify(this.userRepository, times(1)).findById(id);
    }
    
    @Test
    void getUserById_ShouldReturnNull_WhenInvalidId() {
        // Arrange
        UUID id = UUID.randomUUID();
        
        when(this.userRepository.findById(id)).thenReturn(Mono.empty());
        
        // Act
        Mono<User> result = this.userService.getUserById(id);
        
        // Act
        StepVerifier.create(result)
                .expectComplete()
                .verify();
        
        verify(this.userRepository, times(1)).findById(id);
    }
}
