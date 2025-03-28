package denis.f108349.hospital.data.model;

import denis.f108349.hospital.data.model.validation.ValidEGN;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "User")
public class User extends BaseEntity {
    @NotBlank(message = "Keycloak ID is required")
    private String keycloakId;
    
    @Email
    private String email;
    
    @Size(min = 4, max = 22, message = "Username must be between 4 and 22 characters")
    private String username;
    
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @NotBlank(message = "EGN is required")
    @ValidEGN
    private String egn;
}
