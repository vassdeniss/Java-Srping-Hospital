package denis.f108349.hospital.controller;

import denis.f108349.hospital.data.model.Doctor;
import denis.f108349.hospital.dto.DoctorRequest;
import denis.f108349.hospital.service.DoctorService;
import denis.f108349.hospital.service.DoctorSpecialtyService;
import denis.f108349.hospital.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
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
    private DoctorService doctorService;

    @Test
    void createDoctor_ShouldReturnOk_WhenValidRequest() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Doctor mockDoctor = new Doctor(uuid.toString(), false);
        DoctorRequest mockDoctorRequest = new DoctorRequest();
        mockDoctorRequest.setId(uuid.toString());
        mockDoctorRequest.setSpecialityIds(List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        when(this.doctorSpecialtyService.createDoctorSpecialty(anyString(), anyString())).thenReturn(Mono.empty());
        when(this.doctorService.createDoctor(anyString())).thenReturn(Mono.just(mockDoctor));

        // Act and Assert
        this.webTestClient.post()
                .uri("/api/doctors/create")
                .body(BodyInserters.fromValue(mockDoctorRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Doctor.class)
                .isEqualTo(mockDoctor);

        //verify(this.doctorSpecialtyService, times(2)).createDoctorSpecialty(anyString(), anyString());
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
