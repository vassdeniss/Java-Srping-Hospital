package denis.f108349.hospital.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

@Getter
@Setter
public class DoctorRequest {
    @UUID
    private String id;
    
    private List<String> specialityIds;
}
