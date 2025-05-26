package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.data.projection.DoctorPatientCountProjection;
import denis.f108349.hospital.data.projection.HistoryProjection;
import reactor.core.publisher.Flux;

public interface PatientJoinRepository {
    Flux<HistoryProjection> findPatientHistoryById(String patientId);

    Flux<Patient> findPatientsByDiagnosis(String diagnosisCode);
    
    Flux<DoctorPatientCountProjection> countPatientsPerGp();
}
