package denis.f108349.hospital.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("Specialty")
public class Specialty extends BaseEntity {
    public Specialty() {
        super();
    }
    
    public Specialty(String specialtyId, String name) {
        super(specialtyId);
        this.name = name;
    }

    @NotBlank(message = "Specialty name is required")
    @Size(max = 255, message = "Specialty name must be at most 255 characters")
    private String name;
}
