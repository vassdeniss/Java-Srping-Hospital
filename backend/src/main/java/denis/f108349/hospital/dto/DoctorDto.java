package denis.f108349.hospital.dto;

import denis.f108349.hospital.data.model.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DoctorDto {
    private Doctor doctor;
    private KeycloakUser user;
}
