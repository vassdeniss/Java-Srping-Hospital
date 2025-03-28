package denis.f108349.hospital.dto;

import denis.f108349.hospital.data.model.validation.ValidEGN;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "UserRegistrationRequest", description = "Request object for registering a new user")
public class UserRegistrationRequest {
    @Schema(description = "Keycloak user ID", example = "ff11aa22-bb33-cc44-dd55-ee66ff778899")
    @NotBlank
    private String keycloakId;
    
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    @Email
    private String email;
    
    @Schema(description = "Username of the user", example = "john_doe123", minLength = 4, maxLength = 22)
    @Size(min = 4, max = 22)
    private String username;

    @Schema(description = "First name of the user", example = "John")
    @NotBlank
    private String firstName;
    
    @Schema(description = "Last name of the user", example = "Doe")
    @NotBlank
    private String lastName;
    
    @Schema(description = "Unique national ID", example = "1234567890")
    @NotBlank
    @ValidEGN
    private String egn;
}
