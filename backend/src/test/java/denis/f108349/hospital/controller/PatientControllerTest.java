package denis.f108349.hospital.controller;

import denis.f108349.hospital.dto.UserRegistrationRequest;
import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.data.model.User;
import denis.f108349.hospital.service.PatientService;
import denis.f108349.hospital.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebFluxTest(PatientController.class)
@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @MockBean
    private PatientService patientService;
    
    @Test
    void createPatient_ShouldReturnPatient_WhenValidRequest() {
        // Arrange
        User mockUser = new User(
                "keycloakId", "test@email.com", "testUser", "First", "Last", "7501020018");
        mockUser.setId(UUID.randomUUID().toString());
        UserRegistrationRequest request = new UserRegistrationRequest(
                "keycloakId", "test@email.com", "testUser", "First", "Last", "7501020018");
        Patient mockPatient = new Patient(UUID.randomUUID().toString(), null, null);

        when(this.userService.createUser(request)).thenReturn(Mono.just(mockUser));
        when(this.patientService.createPatient(mockUser.getId())).thenReturn(Mono.just(mockPatient));

        // Act and Assert
        this.webTestClient.post()
                .uri("/api/patients/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Patient.class)
                .isEqualTo(mockPatient);

        verify(this.userService, times(1)).createUser(any(UserRegistrationRequest.class));
        verify(this.patientService, times(1)).createPatient(any(String.class));
    }       
    @Test
    void createPatient_ShouldReturnValidationError_WhenInvalidRequest() {
        // Arrange
        UserRegistrationRequest invalidRequest = new UserRegistrationRequest(
            "keycloakId", "testemail.com", "testUser", "First", "Last", "7501020018");
        
        this.webTestClient.post()
            .uri("/api/patients/create")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invalidRequest)
            .exchange()
            .expectStatus().isBadRequest();
    }
}
