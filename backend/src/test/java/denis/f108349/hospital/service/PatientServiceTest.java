package denis.f108349.hospital.service;

import denis.f108349.hospital.data.repo.PatientRepository;
import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.service.impl.PatientServiceImpl;
import net.bytebuddy.agent.builder.AgentBuilder;
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
        String userId = UUID.randomUUID().toString();
        Patient patient = new Patient(userId, null, null);

        when(this.patientRepository.save(any(Patient.class))).thenReturn(Mono.just(patient));

        // Act
        Mono<Patient> result = this.patientService.createPatient(userId);

        // Assert
        StepVerifier.create(result)
            .expectNextMatches(createdPatient -> createdPatient.getKeycloakId().equals(userId))
            .verifyComplete();

        verify(this.patientRepository, times(1)).save(any(Patient.class));
    }
    
    @Test
    void getPatientByKeycloakId_ShouldReturnPatient_WhenValidId() {
        // Arrange
        String id = UUID.randomUUID().toString();
        Patient patient = new Patient(id, null, null);
        
        when(this.patientRepository.findByKeycloakId(id)).thenReturn(Mono.just(patient));
        
        // Act
        Mono<Patient> result = this.patientService.getPatientByKeycloakId(id);
        
        // Act
        StepVerifier.create(result)
                .expectNextMatches(createdUser -> createdUser.getKeycloakId().equals(id))
                .verifyComplete();
        
        verify(this.patientRepository, times(1)).findByKeycloakId(id);
    }
    
    @Test
    void getPatientByKeycloakId_ShouldReturnNull_WhenInvalidId() {
        // Arrange
        String id = UUID.randomUUID().toString();
        
        when(this.patientRepository.findByKeycloakId(id)).thenReturn(Mono.empty());
        
        // Act
        Mono<Patient> result = this.patientService.getPatientByKeycloakId(id);
        
        // Act
        StepVerifier.create(result)
                .expectComplete()
                .verify();
        
        verify(this.patientRepository, times(1)).findByKeycloakId(id);
    }
    
    @Test
    void getAllPatients_ShouldReturnAllPatients() {
        // Arrange
        Patient patient = new Patient(UUID.randomUUID().toString(), null, null);
        Patient patient2 = new Patient(UUID.randomUUID().toString(), null, null);
        
        when(this.patientRepository.findAll()).thenReturn(Flux.just(patient, patient2));
        
        // Act
        Flux<Patient> result = this.patientService.getAllPatients();
        
        // Act
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
        
        verify(this.patientRepository, times(1)).findAll();
    }
    
    @Test
    void deletePatientByKeycloakId_ShouldDeletePatient_WhenValidId() {
        // Arrange
        Patient patient = new Patient(UUID.randomUUID().toString(), null, null);
        
        when(this.patientRepository.deletePatientByKeycloakId(patient.getKeycloakId())).thenReturn(Mono.empty());
        
        // Act
        Mono<Void> result = this.patientService.deletePatientByKeycloakId(patient.getKeycloakId());
        
        // Act
        StepVerifier.create(result)
                .expectComplete()
                .verify();
        
        verify(this.patientRepository, times(1)).deletePatientByKeycloakId(patient.getKeycloakId());
    }
}
