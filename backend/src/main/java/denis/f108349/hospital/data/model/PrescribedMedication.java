package denis.f108349.hospital.data.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@Table("PrescribedMedication")
public class PrescribedMedication extends BaseEntity {
    @NotBlank(message = "Visit ID is required")
    private String visitId;
    
    @NotBlank(message = "Medication ID is required")
    private String medicationId;

    @NotBlank(message = "Dosage is required")
    @Size(max = 100, message = "Dosage must be at most 100 characters")
    private String dosage;

    @NotBlank(message = "Frequency is required")
    @Size(max = 100, message = "Frequency must be at most 100 characters")
    private String frequency;

    @NotBlank(message = "Duration is required")
    @Size(max = 100, message = "Duration must be at most 100 characters")
    private String duration;

    @Size(max = 1000, message = "Instructions must be at most 1000 characters")
    private String instructions;
}
