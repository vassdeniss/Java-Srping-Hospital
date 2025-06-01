package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.projection.*;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;

import java.time.Instant;

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
    
    @Query("""
        SELECT p.keycloak_id  AS patient_id,
               do.keycloak_id AS doctor_id,
               d.name         AS diagnosis,
               m.name         AS treatment,
               pm.dosage      AS dosage,
               pm.frequency   AS frequency,
               pm.duration    AS duration,
               s.days         AS sick_leave_days,
               v.visit_date   AS visit_date
        FROM   Visit v
        LEFT   JOIN PrescribedMedication pm ON v.id = pm.visit_id
        LEFT   JOIN Medication m            ON pm.medication_id = m.id
        LEFT   JOIN Diagnosis d             ON v.id = d.visit_id
        LEFT   JOIN SickLeave s             ON v.id = s.visit_id
        LEFT   JOIN Patient p               ON v.patient_id = p.id
        LEFT   JOIN Doctor do               ON v.doctor_id = do.id
        WHERE  v.visit_date BETWEEN :startDate AND :endDate
        ORDER  BY v.visit_date DESC
    """)
    Flux<HistoryProjection> findVisitsInPeriod(Instant startDate, Instant endDate);
    
    @Query("""
        SELECT p.keycloak_id  AS patient_id,
               do.keycloak_id AS doctor_id,
               d.name         AS diagnosis,
               m.name         AS treatment,
               pm.dosage      AS dosage,
               pm.frequency   AS frequency,
               pm.duration    AS duration,
               s.days         AS sick_leave_days,
               v.visit_date   AS visit_date
        FROM   Visit v
        LEFT   JOIN PrescribedMedication pm ON v.id = pm.visit_id
        LEFT   JOIN Medication m            ON pm.medication_id = m.id
        LEFT   JOIN Diagnosis d             ON v.id = d.visit_id
        LEFT   JOIN SickLeave s             ON v.id = s.visit_id
        LEFT   JOIN Patient p               ON v.patient_id = p.id
        LEFT   JOIN Doctor do               ON v.doctor_id = do.id
        WHERE  v.visit_date BETWEEN :startDate AND :endDate
          AND  v.doctor_id = :doctorId
        ORDER  BY v.visit_date DESC
    """)
    Flux<HistoryProjection> findVisitsByDoctorInPeriod(String doctorId, Instant startDate, Instant endDate);
}
