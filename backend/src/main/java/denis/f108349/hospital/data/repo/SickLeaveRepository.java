package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.model.SickLeave;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SickLeaveRepository extends ReactiveCrudRepository<SickLeave, String>, SickLeaveJoinRepository {
}
