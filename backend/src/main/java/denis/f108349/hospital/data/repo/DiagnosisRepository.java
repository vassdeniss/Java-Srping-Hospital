package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.model.Diagnosis;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DiagnosisRepository extends ReactiveCrudRepository<Diagnosis, String> {
}
