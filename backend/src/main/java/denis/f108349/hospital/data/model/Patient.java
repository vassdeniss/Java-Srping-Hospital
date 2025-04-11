package denis.f108349.hospital.data.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Table("Patient")
public class Patient extends BaseEntity {
    @NotBlank(message = "Keycloak ID is required")
    private String keycloakId;

    private String gpDoctorId;

    @PastOrPresent(message = "Last payment date cannot be in the future")
    private LocalDate lastPaymentDate;
}
