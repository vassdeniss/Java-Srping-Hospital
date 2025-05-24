package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.model.Doctor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DoctorRepository extends ReactiveCrudRepository<Doctor, String>, DoctorJoinRepository {
    Mono<Doctor> findByKeycloakId(String id);
    
    Flux<Doctor> findAllByIsGpIs(boolean gp);
}
