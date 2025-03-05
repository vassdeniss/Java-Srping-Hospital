package denis.f108349.hospital.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@AllArgsConstructor
public abstract class BaseEntity {
    public BaseEntity() {
        this.id = UUID.randomUUID().toString();
    }
    
    @Id
    @NotBlank(message = "ID is required")
    protected String id;
}
