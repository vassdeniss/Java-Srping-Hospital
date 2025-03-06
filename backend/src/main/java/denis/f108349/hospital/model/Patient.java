package denis.f108349.hospital.model;

import denis.f108349.hospital.model.validation.ValidEGN;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    
    public Patient(String id, String userId, String name, String egn, String gpDoctorId, LocalDate lastPaymentDate) {
        super(id);
        this.userId = userId;
        this.name = name;
        this.egn = egn;
        this.gpDoctorId = gpDoctorId;
        this.lastPaymentDate = lastPaymentDate;
    }

    @NotBlank(message = "User ID is required")
    private String userId;
    
    @Transient
    private User user;

    @NotBlank(message = "Patient name is required")
    @Size(max = 255, message = "Patient name must be at most 255 characters")
    private String name;

    @NotBlank(message = "EGN is required")
    @ValidEGN
    private String egn;

    @NotBlank(message = "GP doctor ID is required")
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
