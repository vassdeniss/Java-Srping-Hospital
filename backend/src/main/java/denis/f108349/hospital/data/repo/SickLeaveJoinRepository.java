package denis.f108349.hospital.data.repo;

import denis.f108349.hospital.data.projection.DoctorSickLeaveCountProjection;
import denis.f108349.hospital.data.projection.MostIssuedMonthProjection;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SickLeaveJoinRepository {
    @Query("""
        SELECT EXTRACT(MONTH FROM v.visit_date) AS month,
               EXTRACT(YEAR FROM v.visit_date)  AS year,
               COUNT(sl.id)                     AS total_sick_leaves
        FROM   SickLeave sl
        JOIN   Visit v ON sl.visit_id = v.id
        GROUP  BY year, month
        ORDER  BY total_sick_leaves DESC
        LIMIT  1
    """)
    Mono<MostIssuedMonthProjection> findMonthWithMostSickLeaves();
    
    @Query("""
        WITH counts AS (
            SELECT d.keycloak_id AS doctor_id,
                   COUNT(sl.id) AS total_sick_leaves
            FROM SickLeave sl
            JOIN Visit v ON sl.visit_id = v.id
            JOIN Doctor d ON v.doctor_id = d.id
            GROUP BY v.doctor_id
        )
        SELECT doctor_id, total_sick_leaves
        FROM counts
        WHERE total_sick_leaves = (SELECT MAX(total_sick_leaves) FROM counts)
    """)
    Flux<DoctorSickLeaveCountProjection> findTopDoctorsBySickLeaves();
}
