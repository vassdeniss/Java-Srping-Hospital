package denis.f108349.hospital.service;

import denis.f108349.hospital.data.projection.HistoryProjection;
import denis.f108349.hospital.data.projection.MostIssuedMonthProjection;
import denis.f108349.hospital.dto.DiagnosisCountDto;
import denis.f108349.hospital.dto.DoctorPatientCountDto;
import denis.f108349.hospital.dto.DoctorSickLeaveCountDto;
import denis.f108349.hospital.dto.PatientDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

public interface ReportService {
      Flux<PatientDto> getAllPatientsByDiagnosis(String diagnosisCode);

      Flux<DiagnosisCountDto> getCommonDiagnosisCount();

      Flux<PatientDto> getPatientsByGpDoctorId(String id);
      
      Flux<DoctorPatientCountDto> getCountPatientsPerGp();
    
      Flux<DoctorPatientCountDto> getVisitsPerDoctor();

      Flux<HistoryProjection> getVisitsInPeriod(Instant from, Instant to);

      Flux<HistoryProjection> getVisitsByDoctorInPeriod(String id, Instant from, Instant to);

      Mono<MostIssuedMonthProjection> getBusiestSickLeaveMonth();

      Flux<DoctorSickLeaveCountDto> getTopDoctorsBySickLeaves();
}
