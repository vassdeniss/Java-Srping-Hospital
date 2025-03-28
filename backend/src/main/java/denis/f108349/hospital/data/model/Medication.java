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
@Table("Medication")
public class Medication extends BaseEntity {
    @NotBlank(message = "Medication name is required")
    @Size(max = 255, message = "Medication name must be at most 255 characters")
    private String name;
}
