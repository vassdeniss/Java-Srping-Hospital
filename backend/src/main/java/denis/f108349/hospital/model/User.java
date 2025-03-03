package denis.f108349.hospital.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@AllArgsConstructor
@Table(name = "User")
public class User {
    public User() {
        this.userId = UUID.randomUUID().toString();
    }
    
    @Id
    @Column("user_id")
    private String userId;
    
    @Size(min = 4, max = 22, message = "Username must be between 4 and 22 characters")
    private String username;
    
    @NotBlank(message = "Password hash is required")
    @Column("password_hash")
    private String passwordHash;
    
    @NotNull(message = "Role is required")
    private Role role;
    
    public enum Role {
        ADMIN, DOCTOR, PATIENT
    }
}
