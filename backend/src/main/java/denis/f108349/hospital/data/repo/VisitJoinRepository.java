package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.projection.VisitProjection;
import reactor.core.publisher.Flux;

public interface VisitJoinRepository {
    Flux<VisitProjection> findAllByPatientIdOrDoctorId(String patientId, String doctorId);
}
