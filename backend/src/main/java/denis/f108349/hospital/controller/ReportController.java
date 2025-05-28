package denis.f108349.hospital.controller;

import denis.f108349.hospital.dto.DiagnosisCountDto;
import denis.f108349.hospital.dto.DoctorPatientCountDto;
import denis.f108349.hospital.dto.PatientDto;
import denis.f108349.hospital.service.ReportService;
import denis.f108349.hospital.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

// TODO: Test
// TODO: postman
// TODO: Api descriptions
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Endpoints for managing reports")
public class ReportController {
    private final UserService userService;
    private final ReportService reportService;

    @GetMapping("/diagnoses/{code}/patients")
    public Flux<PatientDto> getPatientsByDiagnosis(@PathVariable String code) {
        return this.reportService.getAllPatientsByDiagnosis(code)
                .flatMap(patient ->
                        userService.getUserById(patient.getKeycloakId())
                                .map(user -> new PatientDto(patient, user, null))
                );
    }
    
    @GetMapping("/diagnoses/common")
    public Flux<DiagnosisCountDto> getMostCommonDiagnoses() {
        return this.reportService.getCommonDiagnosisCount()
                .map(proj -> new DiagnosisCountDto(proj.code(), proj.name(), proj.total()));
    }
    
    @GetMapping("/doctors/visits")
    public Flux<DoctorPatientCountDto> getVisitsPerDoctor() {
        return this.reportService.getVisitsPerDoctor();
    }
}
