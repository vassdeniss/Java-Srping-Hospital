package denis.f108349.hospital.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@Table("Diagnosis")
public class Diagnosis extends BaseEntity {
    @NotBlank(message = "Visit ID is required")
    private String visitId;
    
    @NotBlank(message = "Diagnosis code is required")
    @Size(max = 50, message = "Diagnosis code must be at most 50 characters")
    private String code;

    @NotBlank(message = "Diagnosis name is required")
    @Size(max = 255, message = "Diagnosis name must be at most 255 characters")
    private String name;
}
