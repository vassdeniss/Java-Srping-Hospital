package denis.f108349.hospital.data.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Table("Patient")
public class Patient extends BaseEntity {
    @NotNull(message = "User ID is required")
    private String userId;

    private String gpDoctorId;

    @PastOrPresent(message = "Last payment date cannot be in the future")
    private LocalDate lastPaymentDate;
}
