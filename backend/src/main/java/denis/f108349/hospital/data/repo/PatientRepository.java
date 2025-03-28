package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.model.Patient;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface PatientRepository extends ReactiveCrudRepository<Patient, UUID> {
}
