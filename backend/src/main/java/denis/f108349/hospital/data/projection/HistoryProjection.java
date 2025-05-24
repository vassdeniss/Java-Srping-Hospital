package denis.f108349.hospital.data.projection;

import java.time.LocalDateTime;

public record HistoryProjection(
    String id,
    String name,
    String diagnosis,
    String treatment,
    String dosage,
    String frequency,
    String duration,
    Integer sickLeaveDays,
    LocalDateTime visitDate
) { }
