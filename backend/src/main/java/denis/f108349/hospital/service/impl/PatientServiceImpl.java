package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.repo.PatientRepository;
import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Override
    public Mono<Patient> createPatient(String userId) {
        Patient patient = new Patient(userId, null, null);
        return this.patientRepository.save(patient);
    }

    @Override
    public Mono<Patient> getPatientByKeycloakId(String id) {
        return this.patientRepository.findByKeycloakId(id);
    }

    @Override
    public Flux<Patient> getAllPatients() {
        return this.patientRepository.findAll();
    }

    @Override
    public Mono<Void> deletePatientByKeycloakId(String id) {
        return this.patientRepository.deletePatientByKeycloakId(id);
    }
}
