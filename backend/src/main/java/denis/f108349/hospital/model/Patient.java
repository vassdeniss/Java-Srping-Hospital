package denis.f108349.hospital.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@Table("Patient")
public class Patient extends BaseEntity {
    public Patient() {
        super();
    }
    
    public Patient(String userId) {
        super();
        this.userId = userId;
    }

    @NotBlank(message = "User ID is required")
    private String userId;
    
    @Transient
    private User user;

    private String gpDoctorId;
    
    @Transient
    private Doctor gpDoctor;

    @PastOrPresent(message = "Last payment date cannot be in the future")
    private LocalDate lastPaymentDate;
    
    public void setUser(User user) {
        this.user = user;
        this.userId = user != null ? user.getId() : null;
    }
    
    public void setGpDoctor(Doctor gpDoctor) {
        this.gpDoctor = gpDoctor;
        this.gpDoctorId = gpDoctor != null ? gpDoctor.getId() : null;
    }
}
