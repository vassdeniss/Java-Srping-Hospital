package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.projection.HistoryProjection;
import reactor.core.publisher.Flux;

public interface DoctorJoinRepository {
    Flux<HistoryProjection> findDoctorHistoryById(String doctorId);
}
