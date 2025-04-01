package denis.f108349.hospital.service;

import denis.f108349.hospital.data.repo.PatientRepository;
import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.service.impl.PatientServiceImpl;
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

public class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;
    
    @InjectMocks
    private PatientServiceImpl patientService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void createPatient_ShouldReturnPatient_WhenValidUserId() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Patient patient = new Patient(userId, null, null);

        when(this.patientRepository.save(any(Patient.class))).thenReturn(Mono.just(patient));

        // Act
        Mono<Patient> result = this.patientService.createPatient(userId);

        // Assert
        StepVerifier.create(result)
            .expectNextMatches(createdPatient -> createdPatient.getUserId().equals(userId))
            .verifyComplete();

        verify(this.patientRepository, times(1)).save(any(Patient.class));
    }
}
