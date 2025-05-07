package denis.f108349.hospital.data.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Table("Visit")
public class Visit extends BaseEntity {
    @NotBlank(message = "Patient ID is required")
    private String patientId;
    
    @NotBlank(message = "Doctor ID is required")
    private String doctorId;

    @NotNull(message = "Visit date is required")
    @FutureOrPresent(message = "Visit date cannot be in the past")
    private LocalDateTime visitDate;
    
    private boolean isResolved;
}
