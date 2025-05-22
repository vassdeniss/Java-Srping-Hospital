package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.projection.PatientHistoryProjection;
import reactor.core.publisher.Flux;

public interface PatientJoinRepository {
    Flux<PatientHistoryProjection> findPatientHistoryById(String patientId);
}
