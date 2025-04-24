package denis.f108349.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.UUID;

@Getter
@AllArgsConstructor
public class PatientRequest {
    @UUID
    private String id;
}
