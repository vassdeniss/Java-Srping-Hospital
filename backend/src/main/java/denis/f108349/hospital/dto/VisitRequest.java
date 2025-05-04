package denis.f108349.hospital.dto;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class VisitRequest {
    @UUID
    private String patientId;
    
    @UUID
    private String doctorId;
    
    @FutureOrPresent
    private LocalDateTime visitDate;
}
