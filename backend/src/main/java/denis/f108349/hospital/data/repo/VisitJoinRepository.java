package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.projection.DiagnosisCountProjection;
import denis.f108349.hospital.data.projection.DoctorPatientCountProjection;
import denis.f108349.hospital.data.projection.VisitProjection;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;

public interface VisitJoinRepository {
    @Query("""
        SELECT
          v.id AS id,
          v.visit_date AS visitDate,
          d.keycloak_id AS doctorId,
          p.keycloak_id AS patientId
        FROM Visit v
        JOIN Doctor d ON v.doctor_id = d.id
        JOIN Patient p ON v.patient_id = p.id
        WHERE v.patient_id = :patientId AND v.is_resolved = false
           OR v.doctor_id  = :doctorId  AND v.is_resolved = false
    """)
    Flux<VisitProjection> findAllByPatientIdOrDoctorId(String patientId, String doctorId);
    
    @Query("""
        WITH freq AS (
            SELECT d.code   AS code,
                   d.name   AS name,
                   COUNT(*) AS total
            FROM   Diagnosis d
            GROUP  BY d.code
        )
        SELECT code, name, total
        FROM   freq
        WHERE  total = (SELECT MAX(total) FROM freq)
    """)
    Flux<DiagnosisCountProjection> findMostCommonDiagnoses();

    @Query("""
        SELECT d.keycloak_id AS id,
               COUNT(*)      AS total
        FROM   Visit v
        JOIN   Doctor d on d.id = v.doctor_id
        GROUP  BY d.keycloak_id
        ORDER  BY total DESC
    """)
    Flux<DoctorPatientCountProjection> countVisitsPerDoctor();
}
