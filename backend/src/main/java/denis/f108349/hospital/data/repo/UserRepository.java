package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
}
