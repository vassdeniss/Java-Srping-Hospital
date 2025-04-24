package denis.f108349.hospital.controller;

import denis.f108349.hospital.data.model.Doctor;
import denis.f108349.hospital.dto.DoctorRequest;
import denis.f108349.hospital.dto.KeycloakUser;
import denis.f108349.hospital.dto.PatientWithUser;
import denis.f108349.hospital.service.DoctorService;
import denis.f108349.hospital.service.DoctorSpecialtyService;
import denis.f108349.hospital.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@WebFluxTest(DoctorController.class)
public class DoctorControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    
    @MockBean
    private DoctorSpecialtyService doctorSpecialtyService;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private DoctorService doctorService;

    @Test
    void createDoctor_ShouldReturnCreated_WhenValidRequest() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Doctor mockDoctor = new Doctor(uuid.toString(), false);
        DoctorRequest mockDoctorRequest = new DoctorRequest(uuid.toString(), List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        when(this.doctorService.createDoctor(anyString())).thenReturn(Mono.just(mockDoctor));

        // Act and Assert
        this.webTestClient.post()
                .uri("/api/doctors/create")
                .body(BodyInserters.fromValue(mockDoctorRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Doctor.class)
                .isEqualTo(mockDoctor);

        verify(this.doctorService, times(1)).createDoctor(anyString());
    }

    @Test
    void createDoctor_ShouldReturnBadRequest_WhenInvalidRequest() {
        // Arrange
        String invalid = "aaaaaa";

        this.webTestClient.post()
                .uri("/api/doctors/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalid)
                .exchange()
                .expectStatus().isBadRequest();
    }
    
    @Test
    void getAllDoctors_ShouldReturnOk_WhenValidRequest() {
        // Arrange
        KeycloakUser mockUser = new KeycloakUser(
                "keycloakId", "test@email.com", "testUser", "First", "Last", "7501020018");
        KeycloakUser mockUser2 = new KeycloakUser(
                "keycloakId2", "test2@email.com", "test2User", "First2", "Last2", "7501020019");
        Doctor mockDoctor = new Doctor("keycloakId", false);
        mockDoctor.setId(UUID.randomUUID().toString());
        Doctor mockDoctor2 = new Doctor("keycloakId2", false);
        mockDoctor2.setId(UUID.randomUUID().toString());
        
        when(this.userService.getUserById(mockDoctor.getKeycloakId())).thenReturn(Mono.just(mockUser));
        when(this.userService.getUserById(mockDoctor2.getKeycloakId())).thenReturn(Mono.just(mockUser2));
        when(this.doctorService.getAllDoctors()).thenReturn(Flux.just(mockDoctor, mockDoctor2));

        // Act and Assert
        this.webTestClient.get()
                .uri("/api/doctors/all")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PatientWithUser.class);

        verify(this.userService, times(1)).getUserById(mockUser.getKeycloakId());
        verify(this.userService, times(1)).getUserById(mockUser2.getKeycloakId());
        verify(this.doctorService, times(1)).getAllDoctors();
    }
    
    @Test
    void assign_ShouldReturnNoContent_WhenValidRequest() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Doctor mockDoctor = new Doctor(uuid.toString(), false);
        mockDoctor.setId(uuid.toString());
        List<String> list = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        when(this.doctorSpecialtyService.createDoctorSpecialty(anyString(), anyString())).thenReturn(Mono.empty());
        when(this.doctorService.getDoctorByKeycloakId(anyString())).thenReturn(Mono.just(mockDoctor));

        // Act and Assert
        this.webTestClient.post()
                .uri("/api/doctors/assign/" + uuid)
                .body(BodyInserters.fromValue(list))
                .exchange()
                .expectStatus().isNoContent();

        verify(this.doctorSpecialtyService, times(2)).createDoctorSpecialty(anyString(), anyString());
        verify(this.doctorService, times(1)).getDoctorByKeycloakId(anyString());
    }
    
    @Test
    void assign_ShouldReturnNotFound_WhenInvalidRequest() {
        // Arrange
        List<String> list = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        when(this.doctorService.getDoctorByKeycloakId(anyString())).thenReturn(Mono.empty());

        // Act and Assert
        this.webTestClient.post()
                .uri("/api/doctors/assign/aaaaaaa")
                .body(BodyInserters.fromValue(list))
                .exchange()
                .expectStatus().isNotFound();
    }
}
