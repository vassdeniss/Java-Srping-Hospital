package denis.f108349.hospital.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("Doctor")
public class Doctor extends BaseEntity {
    public Doctor() {
        super();
    }
    
    public Doctor(String doctorId, String userId, boolean isGp) {
        super(doctorId);
        this.userId = userId;
        this.isGp = isGp;
    }

    @NotBlank(message = "User ID is required")
    private String userId;
    
    @Transient
    private User user;
    
    private boolean isGp;

    public void setUser(User user) {
        this.user = user;
        this.userId = user != null ? user.getId() : null;
    }
}
