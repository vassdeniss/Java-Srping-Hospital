package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.data.projection.PatientHistoryProjection;
import denis.f108349.hospital.dto.PatientHistoryDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PatientService {
    Mono<Patient> createPatient(String userId);
    
    Mono<Patient> getPatientByKeycloakId(String id);
    
    Flux<Patient> getAllPatients();
    
    Flux<PatientHistoryProjection> getPatientHistory(String patientId);
    
    Mono<Patient> updatePatient(String id, Patient patient);
    
    Mono<Void> deletePatientByKeycloakId(String id);
}
