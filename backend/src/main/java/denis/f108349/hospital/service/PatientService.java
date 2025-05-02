package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Patient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PatientService {
    Mono<Patient> createPatient(String userId);
    
    Mono<Patient> getPatientByKeycloakId(String id);
    
    Flux<Patient> getAllPatients();    
    
    Mono<Patient> updatePatient(String id, Patient patient);
    
    Mono<Void> deletePatientByKeycloakId(String id);
}
