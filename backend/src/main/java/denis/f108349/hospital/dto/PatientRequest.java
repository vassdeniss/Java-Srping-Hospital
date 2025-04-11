package denis.f108349.hospital.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.UUID;

@Getter
public class PatientRequest {
    @UUID
    private String id;
}
