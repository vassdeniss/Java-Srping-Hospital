package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.projection.DoctorPatientCountProjection;
import denis.f108349.hospital.data.projection.HistoryProjection;
import denis.f108349.hospital.data.repo.PatientRepository;
import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.exception.EntityNotFoundException;
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
        Patient patient = new Patient(userId, null, false);
        return this.patientRepository.save(patient);
    }

    @Override
    public Mono<Patient> getPatientByKeycloakId(String id) {
        return this.patientRepository.findByKeycloakId(id);
    }

    @Override
    public Flux<Patient> getPatientsByGpDoctorId(String id) {
        return this.patientRepository.findByGpDoctorId(id);
    }

    @Override
    public Flux<Patient> getAllPatients() {
        return this.patientRepository.findAll();
    }

    @Override
    public Flux<HistoryProjection> getPatientHistory(String patientId) {
        return this.patientRepository.findPatientHistoryById(patientId);
    }

    @Override
    public Flux<DoctorPatientCountProjection> getCountPatientsPerGp() {
        return this.patientRepository.countPatientsPerGp();
    }

    @Override
    public Mono<Patient> updatePatient(String id, Patient patient) {
        return this.patientRepository.findByKeycloakId(id)
                .flatMap(patient1 -> {
                    patient1.setGpDoctorId(patient.getGpDoctorId());
                    patient1.setHealthInsurance(patient.isHealthInsurance());
                    return this.patientRepository.save(patient1);
                }).switchIfEmpty(Mono.error(new EntityNotFoundException("Patient not found")));
    }

    @Override
    public Mono<Void> deletePatientByKeycloakId(String id) {
        return this.patientRepository.deletePatientByKeycloakId(id);
    }
}
