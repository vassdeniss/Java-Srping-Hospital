package denis.f108349.hospital.repository;

import denis.f108349.hospital.data.model.Doctor;
import denis.f108349.hospital.data.repo.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoctorRepositoryTest {
    @Mock
    private DoctorRepository doctorRepository;
    
    private Doctor doctor;
    
    @BeforeEach
    void setUp() {
        this.doctor = new Doctor("keycloak123", true);
    }
    
    @Test
    void findByKeycloakId() {
        // Arrange
        when(this.doctorRepository.findByKeycloakId("keycloak123")).thenReturn(Mono.just(this.doctor));
        
        // Act
        Mono<Doctor> result = this.doctorRepository.findByKeycloakId("keycloak123");

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(doctor -> doctor.getKeycloakId().equals("keycloak123"))
                .expectComplete()
                .verify();
    }
    
    @Test
    void findByKeycloakId_NotFound() {
        // Arrange:
        when(this.doctorRepository.findByKeycloakId("keycloak999")).thenReturn(Mono.empty());

        // Act
        Mono<Doctor> result = this.doctorRepository.findByKeycloakId("keycloak999");

        // Assert
        StepVerifier.create(result)
                .expectNextCount(0)  // No items should be emitted
                .expectComplete()
                .verify();
    }
    
    @Test
    void findAllByIsGpIs() {
        // Arrange
        when(this.doctorRepository.findAllByIsGpIs(true)).thenReturn(Flux.just(this.doctor));

        // Act
        Flux<Doctor> result = this.doctorRepository.findAllByIsGpIs(true);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(Doctor::isGp)
                .expectComplete()
                .verify();
    }

    @Test
    void findAllByIsGpIs_NoResults() {
        // Arrange
        when(this.doctorRepository.findAllByIsGpIs(false)).thenReturn(Flux.empty());

        // Act
        Flux<Doctor> result = this.doctorRepository.findAllByIsGpIs(false);

        // Assert
        StepVerifier.create(result)
                .expectNextCount(0)  // No items should be emitted
                .expectComplete()
                .verify();
    }

    @Test
    void findAllByIsGpIs_MultipleDoctors() {
        // Arrange
        Doctor doctor2 = new Doctor("keycloak124", false);
        when(this.doctorRepository.findAllByIsGpIs(true)).thenReturn(Flux.just(this.doctor, doctor2));

        // Act
        Flux<Doctor> result = this.doctorRepository.findAllByIsGpIs(true);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(doctor -> doctor.getKeycloakId().equals("keycloak123"))
                .expectNextMatches(doctor -> doctor.getKeycloakId().equals("keycloak124"))
                .expectComplete()
                .verify();
    }

    @Test
    void findAllByIsGpIs_MultipleDoctors_NoGp() {
        // Arrange
        Doctor doctor2 = new Doctor("keycloak125", false);
        when(this.doctorRepository.findAllByIsGpIs(false)).thenReturn(Flux.just(doctor2));

        // Act
        Flux<Doctor> result = this.doctorRepository.findAllByIsGpIs(false);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(doctor -> doctor.getKeycloakId().equals("keycloak125"))
                .expectComplete()
                .verify();
    }
}
