package denis.f108349.hospital.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("Medication")
public class Medication extends BaseEntity {
    public Medication() {
        super();
    }

    public Medication(String id, String name) {
        super(id);
        this.name = name;
    }

    @NotBlank(message = "Medication name is required")
    @Size(max = 255, message = "Medication name must be at most 255 characters")
    private String name;
}
