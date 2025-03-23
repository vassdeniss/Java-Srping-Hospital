package denis.f108349.hospital.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@Table("Visit")
public class Visit extends BaseEntity {
    public Visit() {
        super();
    }

    public Visit(String id, String patientId, String doctorId, LocalDate visitDate) {
        super(id);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.visitDate = visitDate;
    }

    @NotBlank(message = "Patient ID is required")
    private String patientId;
    
    @Transient
    private Patient patient;

    @NotBlank(message = "Doctor ID is required")
    private String doctorId;
    
    @Transient
    private Doctor doctor;

    @NotNull(message = "Visit date is required")
    @PastOrPresent(message = "Visit date cannot be in the future")
    private LocalDate visitDate;
    
    public void setPatient(Patient patient) {
        this.patient = patient;
        this.patientId = patient != null ? patient.getId() : null;
    }
    
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        this.doctorId = doctor != null ? doctor.getId() : null;
    }
}
