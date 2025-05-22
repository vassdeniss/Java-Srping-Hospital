package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.model.PrescribedMedication;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PrescribedMedicationRepository extends ReactiveCrudRepository<PrescribedMedication, String> {
}
