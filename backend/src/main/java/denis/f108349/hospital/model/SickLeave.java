package denis.f108349.hospital.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@Table("SickLeave")
public class SickLeave extends BaseEntity {
    public SickLeave() {
        super();
    }

    public SickLeave(String id, String visitId, LocalDate startDate, int days) {
        super(id);
        this.visitId = visitId;
        this.startDate = startDate;
        this.days = days;
    }

    @NotBlank(message = "Visit ID is required")
    private String visitId;
    
    @Transient
    private Visit visit;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @Positive(message = "Days must be a positive number")
    private int days;
    
    public void setVisitId(Visit visit) {
        this.visit = visit;
        this.visitId = visit != null ? visit.getId() : null;
    }
}
