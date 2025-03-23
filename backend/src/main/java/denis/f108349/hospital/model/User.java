package denis.f108349.hospital.model;

import denis.f108349.hospital.model.validation.ValidEGN;
import jakarta.validation.constraints.NotBlank;
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
    
    public User(String userId, String username, String firstName, String lastName, String egn) {
        super(userId);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.egn = egn;
    }
    
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
