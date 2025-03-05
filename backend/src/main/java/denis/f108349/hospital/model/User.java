package denis.f108349.hospital.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "User")
public class User extends BaseEntity {
    public User() {
        super();
    }
    
    public User(String userId, String username, String passwordHash, Role role) {
        super(userId);
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }
    
    @Size(min = 4, max = 22, message = "Username must be between 4 and 22 characters")
    private String username;
    
    @NotBlank(message = "Password hash is required")
    private String passwordHash;
    
    @NotNull(message = "Role is required")
    private Role role;
    
    public enum Role {
        ADMIN, DOCTOR, PATIENT
    }
}
