package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.dto.PatientWithUser;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PatientService {
    Mono<Patient> createPatient(UUID userId);
    
    Mono<Patient> getPatientById(UUID id);
}
