package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.model.Patient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PatientRepository extends ReactiveCrudRepository<Patient, String>, PatientJoinRepository {
    Mono<Patient> findByKeycloakId(String id);
    
    Mono<Void> deletePatientByKeycloakId(String id);
}
