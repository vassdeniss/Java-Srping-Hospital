package denis.f108349.hospital.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("PrescribedMedication")
public class PrescribedMedication extends BaseEntity {
    public PrescribedMedication() {
        super();
    }

    public PrescribedMedication(String id, String visitId, String medicationId, String dosage, String frequency, 
                                String duration, String instructions) {
        super(id);
        this.visitId = visitId;
        this.medicationId = medicationId;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.instructions = instructions;
    }

    @NotBlank(message = "Visit ID is required")
    private String visitId;
    
    @Transient
    private Visit visit;

    @NotBlank(message = "Medication ID is required")
    private String medicationId;
    
    @Transient
    private Medication medication;

    @NotBlank(message = "Dosage is required")
    @Size(max = 100, message = "Dosage must be at most 100 characters")
    private String dosage;

    @NotBlank(message = "Frequency is required")
    @Size(max = 100, message = "Frequency must be at most 100 characters")
    private String frequency;

    @NotBlank(message = "Duration is required")
    @Size(max = 100, message = "Duration must be at most 100 characters")
    private String duration;

    @Size(max = 1000, message = "Instructions must be at most 1000 characters")
    private String instructions;
    
    public void setVisitId(Visit visit) {
        this.visit = visit;
        this.visitId = visit != null ? visit.getId() : null;
    }
    
    public void setMedicationId(Medication medication) {
        this.medication = medication;
        this.medicationId = medication != null ? medication.getId() : null;
    }
}
