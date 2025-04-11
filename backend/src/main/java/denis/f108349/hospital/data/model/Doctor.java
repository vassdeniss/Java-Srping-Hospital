package denis.f108349.hospital.data.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@Table("Doctor")
public class Doctor extends BaseEntity {
    @NotBlank(message = "Keycloak ID is required")
    private String keycloakId;
    
    private boolean isGp;
}
