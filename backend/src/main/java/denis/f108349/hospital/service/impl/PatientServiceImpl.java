package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.projection.HistoryProjection;
import denis.f108349.hospital.data.repo.PatientRepository;
import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.exception.EntityNotFoundException;
import denis.f108349.hospital.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Override
    public Mono<Patient> createPatient(String userId) {
        Patient patient = new Patient(userId, null, false);
        return this.patientRepository.save(patient);
    }

    @Override
    @PreAuthorize("hasAnyRole('admin', 'patient')")
    public Mono<Patient> getPatientByKeycloakId(String id) {
        return this.patientRepository.findByKeycloakId(id);
    }

    @Override
    @PreAuthorize("hasAnyRole('admin')")
    public Flux<Patient> getAllPatients() {
        return this.patientRepository.findAll();
    }

    @Override
    @PreAuthorize("hasAnyRole('patient')")
    public Flux<HistoryProjection> getPatientHistory(String patientId) {
        return this.patientRepository.findPatientHistoryById(patientId);
    }

    @Override
    @PreAuthorize("hasAnyRole('admin', 'patient')")
    public Mono<Patient> updatePatient(String id, Patient patient) {
        return this.patientRepository.findByKeycloakId(id)
                .flatMap(patient1 -> {
                    patient1.setGpDoctorId(patient.getGpDoctorId());
                    patient1.setHealthInsurance(patient.isHealthInsurance());
                    return this.patientRepository.save(patient1);
                }).switchIfEmpty(Mono.error(new EntityNotFoundException("Patient not found")));
    }

    @Override
    @PreAuthorize("hasAnyRole('admin')")
    public Mono<Void> deletePatientByKeycloakId(String id) {
        return this.patientRepository.deletePatientByKeycloakId(id);
    }
}
