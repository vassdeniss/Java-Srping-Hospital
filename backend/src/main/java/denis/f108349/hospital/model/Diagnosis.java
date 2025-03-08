package denis.f108349.hospital.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("Diagnosis")
public class Diagnosis extends BaseEntity {
    public Diagnosis() {
        super();
    }

    public Diagnosis(String id, String visitId, String code, String name) {
        super(id);
        this.visitId = visitId;
        this.code = code;
        this.name = name;
    }

    @NotBlank(message = "Visit ID is required")
    private String visitId;
    
    @Transient
    private Visit visit;

    @NotBlank(message = "Diagnosis code is required")
    @Size(max = 50, message = "Diagnosis code must be at most 50 characters")
    private String code;

    @NotBlank(message = "Diagnosis name is required")
    @Size(max = 255, message = "Diagnosis name must be at most 255 characters")
    private String name;
    
    public void setVisit(Visit visit) {
        this.visit = visit;
        this.visitId = visit != null ? visit.getId() : null;
    }
}
