package denis.f108349.hospital.controller;

import denis.f108349.hospital.data.projection.HistoryProjection;
import denis.f108349.hospital.data.projection.MostIssuedMonthProjection;
import denis.f108349.hospital.dto.DiagnosisCountDto;
import denis.f108349.hospital.dto.DoctorPatientCountDto;
import denis.f108349.hospital.dto.DoctorSickLeaveCountDto;
import denis.f108349.hospital.dto.PatientDto;
import denis.f108349.hospital.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

// TODO: Test
// TODO: postman
// TODO: Api descriptions
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Endpoints for managing reports")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/diagnoses/{code}/patients")
    public Flux<PatientDto> getPatientsByDiagnosis(@PathVariable String code) {
        return this.reportService.getAllPatientsByDiagnosis(code);
    }
    
    @GetMapping("/diagnoses/common")
    public Flux<DiagnosisCountDto> getMostCommonDiagnoses() {
        return this.reportService.getCommonDiagnosisCount();
    }
    
    @GetMapping("/gps/{id}/patients")
    public Flux<PatientDto> getPatientsByGp(@PathVariable String id) {
        return this.reportService.getPatientsByGpDoctorId(id);
    }
    
    @GetMapping("/gps/count")
    public Flux<DoctorPatientCountDto> getPatientsByGpCount() {
        return this.reportService.getCountPatientsPerGp();
    }
    
    @GetMapping("/doctors/visits")
    public Flux<DoctorPatientCountDto> getVisitsPerDoctor() {
        return this.reportService.getVisitsPerDoctor();
    }
    
    @GetMapping("/doctors/visits/period")
    public Flux<HistoryProjection> getVisitsInPeriod(@RequestParam Instant startDate,
                                                     @RequestParam Instant endDate) {
        return this.reportService.getVisitsInPeriod(startDate, endDate);
    }
    
    @GetMapping("/doctors/visits/period/{id}")
    public Flux<HistoryProjection> getVisitsInPeriodByDoctorId(@PathVariable String id,
                                                               @RequestParam Instant startDate,
                                                               @RequestParam Instant endDate) {
        return this.reportService.getVisitsByDoctorInPeriod(id, startDate, endDate);
    }
    
    @GetMapping("/leaves/most-issued-month")
    public Mono<MostIssuedMonthProjection> getMostIssuedSickLeaveMonth() {
        return this.reportService.getBusiestSickLeaveMonth();
    }
    
    @GetMapping("/leaves/top-doctors")
    public Flux<DoctorSickLeaveCountDto> getTopDoctorsBySickLeaves() {
        return this.reportService.getTopDoctorsBySickLeaves();
    }
}
