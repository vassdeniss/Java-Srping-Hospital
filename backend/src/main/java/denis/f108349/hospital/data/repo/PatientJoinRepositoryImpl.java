package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.projection.HistoryProjection;
import lombok.AllArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class PatientJoinRepositoryImpl implements PatientJoinRepository {
    private final DatabaseClient databaseClient;
    
    @Override
    public Flux<HistoryProjection> findPatientHistoryById(String patientId) {
        String sql = """
            SELECT
              do.keycloak_id AS id,
              d.name AS diagnosis,
              m.name AS treatment,
              pm.dosage AS dosage,
              pm.frequency AS frequency,
              pm.duration AS duration,
              s.days AS sick_leave_days,
              v.visit_date AS visit_date
            FROM Visit v
            LEFT JOIN PrescribedMedication pm ON v.id = pm.visit_id
            LEFT JOIN Medication m ON pm.medication_id = m.id
            LEFT JOIN Diagnosis d ON v.id = d.visit_id
            LEFT JOIN SickLeave s ON v.id = s.visit_id
            LEFT JOIN Patient p ON v.patient_id = p.id
            LEFT JOIN Doctor do ON v.doctor_id = do.id
            WHERE p.id = :patientId AND v.is_resolved = true
            ORDER BY v.visit_date DESC
        """;
        
        return this.databaseClient.sql(sql)
                .bind("patientId", patientId)
                .map((row, meta) -> new HistoryProjection(
                        row.get("id", String.class),
                        "",
                        row.get("diagnosis", String.class),
                        row.get("treatment", String.class),
                        row.get("dosage", String.class),
                        row.get("frequency", String.class),
                        row.get("duration", String.class),
                        row.get("sick_leave_days", Integer.class),
                        row.get("visit_date", LocalDateTime.class)
                ))
                .all();
    }
}
