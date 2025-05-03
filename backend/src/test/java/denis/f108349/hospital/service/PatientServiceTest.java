package denis.f108349.hospital.service;

import denis.f108349.hospital.data.repo.PatientRepository;
import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.exception.EntityNotFoundException;
import denis.f108349.hospital.service.impl.PatientServiceImpl;
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
        Patient patient = new Patient(userId, null, false);

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
        Patient patient = new Patient(id, null, false);
        
        when(this.patientRepository.findByKeycloakId(id)).thenReturn(Mono.just(patient));
        
        // Act
        Mono<Patient> result = this.patientService.getPatientByKeycloakId(id);
        
        // Assert
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
        
        // Assert
        StepVerifier.create(result)
                .expectComplete()
                .verify();
        
        verify(this.patientRepository, times(1)).findByKeycloakId(id);
    }
    
    @Test
    void getAllPatients_ShouldReturnAllPatients() {
        // Arrange
        Patient patient = new Patient(UUID.randomUUID().toString(), null, false);
        Patient patient2 = new Patient(UUID.randomUUID().toString(), null, false);
        
        when(this.patientRepository.findAll()).thenReturn(Flux.just(patient, patient2));
        
        // Act
        Flux<Patient> result = this.patientService.getAllPatients();
        
        // Assert
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
        
        verify(this.patientRepository, times(1)).findAll();
    }
    
    @Test
    void updatePatient_ShouldUpdatePatient_WhenExist() {
        // Arrange
        String keycloakId = UUID.randomUUID().toString();
        Patient patient = new Patient(keycloakId, null, false);
        patient.setId(UUID.randomUUID().toString());
        String gpId = UUID.randomUUID().toString();
        Patient newPatient = new Patient(keycloakId, gpId, false);
        
        when(this.patientRepository.findByKeycloakId(keycloakId)).thenReturn(Mono.just(patient));
        when(patientRepository.save(any(Patient.class)))
            .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        
        // Act
        Mono<Patient> result = this.patientService.updatePatient(keycloakId, newPatient);
        
        // Assert
        StepVerifier.create(result)
                .expectNextMatches(updatedPatient -> updatedPatient.getGpDoctorId().equals(gpId))
                .verifyComplete();
        
        verify(this.patientRepository, times(1)).findByKeycloakId(keycloakId);
        verify(this.patientRepository, times(1)).save(any(Patient.class));
    }
    
    @Test
    void updatePatient_ShouldReturnError_WhenPatientNotFound() {
        // Arrange
        String keycloakId = UUID.randomUUID().toString();
        Patient newPatient = new Patient(keycloakId, UUID.randomUUID().toString(), false);
    
        when(this.patientRepository.findByKeycloakId(keycloakId)).thenReturn(Mono.empty());
    
        // Act
        Mono<Patient> result = this.patientService.updatePatient(keycloakId, newPatient);
    
        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof EntityNotFoundException
                        && throwable.getMessage().equals("Patient not found"))
                .verify();
        
        verify(this.patientRepository, times(1)).findByKeycloakId(keycloakId);
        verify(this.patientRepository, times(0)).save(any(Patient.class));
    }
    
    @Test
    void deletePatientByKeycloakId_ShouldDeletePatient_WhenValidId() {
        // Arrange
        Patient patient = new Patient(UUID.randomUUID().toString(), null, false);
        
        when(this.patientRepository.deletePatientByKeycloakId(patient.getKeycloakId())).thenReturn(Mono.empty());
        
        // Act
        Mono<Void> result = this.patientService.deletePatientByKeycloakId(patient.getKeycloakId());
        
        // Assert
        StepVerifier.create(result)
                .expectComplete()
                .verify();
        
        verify(this.patientRepository, times(1)).deletePatientByKeycloakId(patient.getKeycloakId());
    }
}
