package denis.f108349.hospital.data.projection;

import java.time.LocalDateTime;

public record VisitProjection(
    String id,
    String doctorId,
    String patientId,
    LocalDateTime visitDate) { } 
