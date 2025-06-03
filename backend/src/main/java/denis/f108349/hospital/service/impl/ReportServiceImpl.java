package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.projection.HistoryProjection;
import denis.f108349.hospital.data.projection.MostIssuedMonthProjection;
import denis.f108349.hospital.data.repo.PatientRepository;
import denis.f108349.hospital.data.repo.SickLeaveRepository;
import denis.f108349.hospital.data.repo.VisitRepository;
import denis.f108349.hospital.dto.DiagnosisCountDto;
import denis.f108349.hospital.dto.DoctorPatientCountDto;
import denis.f108349.hospital.dto.DoctorSickLeaveCountDto;
import denis.f108349.hospital.dto.PatientDto;
import denis.f108349.hospital.service.ReportService;
import denis.f108349.hospital.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

// TODO: Test
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;
    private final UserService userService;
    private final SickLeaveRepository sickLeaveRepository;
    
    @Override
    @PreAuthorize("hasRole('admin')")
    public Flux<PatientDto> getAllPatientsByDiagnosis(String diagnosisCode) {
        return this.patientRepository.findPatientsByDiagnosis(diagnosisCode)
                .flatMap(patient ->
                        userService.getUserById(patient.getKeycloakId())
                                .map(user -> new PatientDto(patient, user, null))
                );
    }
    
    @Override
    @PreAuthorize("hasRole('admin')")
    public Flux<DiagnosisCountDto> getCommonDiagnosisCount() {
        return this.visitRepository.findMostCommonDiagnoses()
                .map(proj -> new DiagnosisCountDto(proj.code(), proj.name(), proj.total()));
    }
    
    @Override
    @PreAuthorize("hasRole('admin')")
    public Flux<PatientDto> getPatientsByGpDoctorId(String id) {
        return this.patientRepository.findByGpDoctorId(id)
            .flatMap(patient -> this.userService.getUserById(patient.getKeycloakId())
                .flatMap(patientUser -> Mono.just(new PatientDto(patient, patientUser, null)))
            );
    }
    
    @Override
    @PreAuthorize("hasRole('admin')")
    public Flux<DoctorPatientCountDto> getCountPatientsPerGp() {
        return this.patientRepository.countPatientsPerGp()
            .flatMap(doctor -> this.userService.getUserById(doctor.id())
                .flatMap(doctorUser -> Mono.just(new DoctorPatientCountDto(doctorUser, doctor.total())))
            );
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    public Flux<DoctorPatientCountDto> getVisitsPerDoctor() {
        return this.visitRepository.countVisitsPerDoctor()
                .flatMap(doctor -> this.userService.getUserById(doctor.id())
                        .flatMap(doctorUser -> Mono.just(new DoctorPatientCountDto(doctorUser, doctor.total()))));
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    public Flux<HistoryProjection> getVisitsInPeriod(Instant from, Instant to) {
        return this.flatMapVisit(this.visitRepository.findVisitsInPeriod(from, to));
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    public Flux<HistoryProjection> getVisitsByDoctorInPeriod(String id, Instant from, Instant to) {
        return this.flatMapVisit(this.visitRepository.findVisitsByDoctorInPeriod(id, from, to));    
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    public Mono<MostIssuedMonthProjection> getBusiestSickLeaveMonth() {
        return this.sickLeaveRepository.findMonthWithMostSickLeaves();
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    public Flux<DoctorSickLeaveCountDto> getTopDoctorsBySickLeaves() {
        return this.sickLeaveRepository.findTopDoctorsBySickLeaves()
                .flatMap(visit -> this.userService.getUserById(visit.doctorId())
                        .flatMap(doctor -> Mono.just(new DoctorSickLeaveCountDto(doctor, visit.totalSickLeaves()))));
    }

    private Flux<HistoryProjection> flatMapVisit(Flux<HistoryProjection> visits) {
        return visits.flatMap(visit -> this.userService.getUserById(visit.patientId())
                    .flatMap(patient -> this.userService.getUserById(visit.doctorId())
                        .flatMap(doctor -> Mono.just(new HistoryProjection(
                            visit.patientId(),
                            visit.doctorId(),
                            patient.getFirstName() + " " + patient.getLastName(),
                            doctor.getFirstName() + " " + doctor.getLastName(),
                            visit.diagnosis(),
                            visit.treatment(),
                            visit.dosage(),
                            visit.frequency(),
                            visit.duration(),
                            visit.sickLeaveDays(),
                            visit.visitDate()
            )))));    
    }
}
