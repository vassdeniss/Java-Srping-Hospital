package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.data.repo.PatientRepository;
import denis.f108349.hospital.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

// TODO: Test
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final PatientRepository patientRepository;
    
    @Override
    public Flux<Patient> getAllPatientsByDiagnosis(String diagnosisCode) {
        return this.patientRepository.findPatientsByDiagnosis(diagnosisCode);
    }
}
