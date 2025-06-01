package denis.f108349.hospital.data.projection;

import java.time.LocalDateTime;

public record HistoryProjection(
    String patientId,
    String doctorId,
    String patientName,
    String doctorName,
    String diagnosis,
    String treatment,
    String dosage,
    String frequency,
    String duration,
    Integer sickLeaveDays,
    LocalDateTime visitDate
) { }
