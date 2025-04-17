package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.model.Specialty;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SpecialtyRepository extends ReactiveCrudRepository<Specialty, String> {
}
