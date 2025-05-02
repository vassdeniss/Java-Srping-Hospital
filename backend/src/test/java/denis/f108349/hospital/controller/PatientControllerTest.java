package denis.f108349.hospital.controller;

import denis.f108349.hospital.dto.KeycloakUser;
import denis.f108349.hospital.dto.PatientRequest;
import denis.f108349.hospital.dto.PatientDto;
import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.service.DoctorService;
import denis.f108349.hospital.service.PatientService;
import denis.f108349.hospital.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebFluxTest(PatientController.class)
public class PatientControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;
    
    @MockitoBean
    private DoctorService doctorService;

    @MockBean
    private PatientService patientService;

    @Test
    void createPatient_ShouldReturnCreated_WhenValidRequest() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Patient mockPatient = new Patient(uuid.toString(), null, null);
        PatientRequest mockRequest = new PatientRequest(uuid.toString());

        when(this.patientService.createPatient(uuid.toString())).thenReturn(Mono.just(mockPatient));

        // Act and Assert
        this.webTestClient.post()
                .uri("/api/patients/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(mockRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Patient.class)
                .isEqualTo(mockPatient);

        verify(this.patientService, times(1)).createPatient(any(String.class));
    }  

    @Test
    void createPatient_ShouldReturnBadRequest_WhenInvalidRequest() {
        // Arrange
        String invalid = "aaaaaa";

        this.webTestClient.post()
            .uri("/api/patients/create")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invalid)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    void getPatientById_ShouldReturnOk_WhenValidRequest() {
        // Arrange
        KeycloakUser mockUser = new KeycloakUser(
                "keycloakId", "test@email.com", "testUser", "First", "Last", "7501020018");
        Patient mockPatient = new Patient("keycloakId", null, null);
        mockPatient.setId(UUID.randomUUID().toString());

        when(this.userService.getUserById(mockPatient.getKeycloakId())).thenReturn(Mono.just(mockUser));
        when(this.patientService.getPatientByKeycloakId(mockPatient.getKeycloakId())).thenReturn(Mono.just(mockPatient));

        // Act and Assert
        this.webTestClient.get()
                .uri("/api/patients/" + mockPatient.getKeycloakId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(PatientDto.class);

        verify(this.userService, times(1)).getUserById(mockUser.getKeycloakId());
        verify(this.patientService, times(1)).getPatientByKeycloakId(mockPatient.getKeycloakId());
    } 

    @Test
    void getPatientById_ShouldReturnNotFound_WhenInvalidRequest() {
        // Arrange
        Patient mockPatient = new Patient(UUID.randomUUID().toString(), null, null);
        mockPatient.setId(UUID.randomUUID().toString());

        when(this.userService.getUserById(any(String.class))).thenReturn(Mono.empty());
        when(this.patientService.getPatientByKeycloakId(any(String.class))).thenReturn(Mono.just(mockPatient));

        // Act and Assert
        this.webTestClient.get()
                .uri("/api/patients/" + mockPatient.getId())
                .exchange()
                .expectStatus().isNotFound();

        verify(this.userService, times(1)).getUserById(any(String.class));
        verify(this.patientService, times(1)).getPatientByKeycloakId(any(String.class));
    }
    
    @Test
    void getAllPatients_ShouldReturnOk_WhenValidRequest() {
        // Arrange
        KeycloakUser mockUser = new KeycloakUser(
                "keycloakId", "test@email.com", "testUser", "First", "Last", "7501020018");
        KeycloakUser mockUser2 = new KeycloakUser(
                "keycloakId2", "test2@email.com", "test2User", "First2", "Last2", "7501020019");
        Patient mockPatient = new Patient("keycloakId", null, null);
        mockPatient.setId(UUID.randomUUID().toString());
        Patient mockPatient2 = new Patient("keycloakId2", null, null);
        mockPatient2.setId(UUID.randomUUID().toString());
        
        when(this.userService.getUserById(mockPatient.getKeycloakId())).thenReturn(Mono.just(mockUser));
        when(this.userService.getUserById(mockPatient2.getKeycloakId())).thenReturn(Mono.just(mockUser2));
        when(this.patientService.getAllPatients()).thenReturn(Flux.just(mockPatient, mockPatient2));

        // Act and Assert
        this.webTestClient.get()
                .uri("/api/patients/all")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PatientDto.class);

        verify(this.userService, times(1)).getUserById(mockUser.getKeycloakId());
        verify(this.userService, times(1)).getUserById(mockUser2.getKeycloakId());
        verify(this.patientService, times(1)).getAllPatients();
    }
    
    @Test
    void deletePatientByKeycloakId_ShouldReturnNoContent_WhenValidRequest() {
        // Arrange
        Patient mockPatient = new Patient("keycloakId", null, null);
        
        when(this.patientService.deletePatientByKeycloakId(mockPatient.getKeycloakId())).thenReturn(Mono.empty());

        // Act and Assert
        this.webTestClient.delete()
                .uri("/api/patients/delete/" + mockPatient.getKeycloakId())
                .exchange()
                .expectStatus().isNoContent();

        verify(this.patientService, times(1)).deletePatientByKeycloakId(mockPatient.getKeycloakId());
    }
}
