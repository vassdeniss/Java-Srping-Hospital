package denis.f108349.hospital.data.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Table("SickLeave")
public class SickLeave extends BaseEntity {
    @NotBlank(message = "Visit ID is required")
    private String visitId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @Positive(message = "Days must be a positive number")
    private int days;
}
