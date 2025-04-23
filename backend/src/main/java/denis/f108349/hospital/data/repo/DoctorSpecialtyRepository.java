package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.model.DoctorSpecialty;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DoctorSpecialtyRepository extends ReactiveCrudRepository<DoctorSpecialty, String> {
}
