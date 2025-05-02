package denis.f108349.hospital.dto;

import denis.f108349.hospital.data.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatientDto {
    private Patient patient;
    private KeycloakUser user;
    private KeycloakUser doctor;
}
