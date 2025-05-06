package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.model.Medication;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MedicationRepository extends ReactiveCrudRepository<Medication, String> {
}
