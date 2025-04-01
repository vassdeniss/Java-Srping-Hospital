package denis.f108349.hospital.dto;

import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.data.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatientWithUser {
    private Patient patient;
    private User user;
}
