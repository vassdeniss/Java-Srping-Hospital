package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.data.projection.DiagnosisCountProjection;
import denis.f108349.hospital.data.repo.PatientRepository;
import denis.f108349.hospital.data.repo.VisitRepository;
import denis.f108349.hospital.dto.DoctorPatientCountDto;
import denis.f108349.hospital.service.ReportService;
import denis.f108349.hospital.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// TODO: Test
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;
    private final UserService userService;
    
    @Override
    public Flux<Patient> getAllPatientsByDiagnosis(String diagnosisCode) {
        return this.patientRepository.findPatientsByDiagnosis(diagnosisCode);
    }
    
    @Override
    public Flux<DiagnosisCountProjection> getCommonDiagnosisCount() {
        return this.visitRepository.findMostCommonDiagnoses();
    }

    @Override
    public Flux<DoctorPatientCountDto> getVisitsPerDoctor() {
        return this.visitRepository.countVisitsPerDoctor()
                .flatMap(doctor -> this.userService.getUserById(doctor.id())
                        .flatMap(doctorUser -> Mono.just(new DoctorPatientCountDto(doctorUser, doctor.total()))));
    }
}
