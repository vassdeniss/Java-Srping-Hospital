package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Patient;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PatientService {
    Mono<Patient> createPatient(UUID userId);
}
