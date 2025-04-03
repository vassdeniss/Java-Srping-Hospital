package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.repo.PatientRepository;
import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public Mono<Patient> getPatientById(String id) {
        return this.patientRepository.findById(id);
    }
}
