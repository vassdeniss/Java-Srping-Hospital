package denis.f108349.hospital.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("DoctorSpecialty")
public class DoctorSpecialty {
    private String doctorId;
    private String specialtyId;
}
