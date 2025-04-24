package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Doctor;
import denis.f108349.hospital.data.repo.DoctorRepository;
import denis.f108349.hospital.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DoctorServiceTest {
    @Mock
    private DoctorRepository doctorRepository;
    
    @InjectMocks
    private DoctorServiceImpl doctorService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void getDoctorByKeycloakId_ShouldReturnDoctor_WhenValidId() {
        // Arrange
        String id = UUID.randomUUID().toString();
        Doctor doctor = new Doctor(id, false);
        
        when(this.doctorRepository.findByKeycloakId(id)).thenReturn(Mono.just(doctor));
        
        // Act
        Mono<Doctor> result = this.doctorService.getDoctorByKeycloakId(id);
        
        // Act
        StepVerifier.create(result)
                .expectNextMatches(createdUser -> createdUser.getKeycloakId().equals(id))
                .verifyComplete();
        
        verify(this.doctorRepository, times(1)).findByKeycloakId(id);
    }
    
    @Test
    void getDoctorByKeycloakId_ShouldReturnNull_WhenInvalidId() {
        // Arrange
        String id = UUID.randomUUID().toString();
        
        when(this.doctorRepository.findByKeycloakId(id)).thenReturn(Mono.empty());
        
        // Act
        Mono<Doctor> result = this.doctorService.getDoctorByKeycloakId(id);
        
        // Act
        StepVerifier.create(result)
                .expectComplete()
                .verify();
        
        verify(this.doctorRepository, times(1)).findByKeycloakId(id);
    }
    
    @Test
    void getAllDoctors_ShouldReturnAllDoctors() {
        // Arrange
        Doctor doctor = new Doctor(UUID.randomUUID().toString(), false);
        Doctor doctor2 = new Doctor(UUID.randomUUID().toString(), false);
        
        when(this.doctorRepository.findAll()).thenReturn(Flux.just(doctor, doctor2));
        
        // Act
        Flux<Doctor> result = this.doctorService.getAllDoctors();
        
        // Act
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
        
        verify(this.doctorRepository, times(1)).findAll();
    }
    
    @Test
    void createDoctor_ShouldReturnDoctor_WhenValidUserId() {
        // Arrange
        String userId = UUID.randomUUID().toString();
        Doctor doctor = new Doctor(userId, false);

        when(this.doctorRepository.save(any(Doctor.class))).thenReturn(Mono.just(doctor));

        // Act
        Mono<Doctor> result = this.doctorService.createDoctor(userId);

        // Assert
        StepVerifier.create(result)
            .expectNextMatches(createdDoctor -> createdDoctor.getKeycloakId().equals(userId))
            .verifyComplete();

        verify(this.doctorRepository, times(1)).save(any(Doctor.class));
    }
}
