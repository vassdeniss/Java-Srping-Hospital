package denis.f108349.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

@Getter
@AllArgsConstructor
public class DoctorRequest {
    @UUID
    private String id;
    
    private List<String> specialityIds;
}
