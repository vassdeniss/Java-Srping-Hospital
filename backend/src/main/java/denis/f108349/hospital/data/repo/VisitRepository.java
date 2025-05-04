package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.model.Visit;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface VisitRepository extends ReactiveCrudRepository<Visit, String> {
    Flux<Visit> findAllByPatientIdOrDoctorId(String patientId, String doctorId);
}
