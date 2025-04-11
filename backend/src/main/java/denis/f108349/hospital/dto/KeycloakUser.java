package denis.f108349.hospital.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class KeycloakUser {
    private String keycloakId;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String egn;
}
