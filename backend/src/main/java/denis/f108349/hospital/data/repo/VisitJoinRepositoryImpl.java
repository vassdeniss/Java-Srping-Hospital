package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.projection.VisitProjection;
import lombok.AllArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class VisitJoinRepositoryImpl implements VisitJoinRepository {
    private final DatabaseClient databaseClient;
    
    @Override
    public Flux<VisitProjection> findAllByPatientIdOrDoctorId(String patientId, String doctorId) {
        String sql = """
            SELECT
              v.id AS id,
              v.visit_date AS visitDate,
              d.keycloak_id AS doctorId,
              p.keycloak_id AS patientId
            FROM Visit v
            JOIN Doctor d ON v.doctor_id = d.id
            JOIN Patient p ON v.patient_id = p.id
            WHERE v.patient_id = :patientId
               OR v.doctor_id = :doctorId
        """;
    
        return this.databaseClient.sql(sql)
            .bind("patientId", patientId)
            .bind("doctorId", doctorId)
            .map((row, meta) -> new VisitProjection(
                row.get("id", String.class),
                row.get("doctorId", String.class),
                row.get("patientId", String.class),
                row.get("visitDate", LocalDateTime.class)
            )).all();
    }
}
