package denis.f108349.hospital.controller;

import denis.f108349.hospital.data.model.Diagnosis;
import denis.f108349.hospital.data.model.Doctor;
import denis.f108349.hospital.service.DiagnosisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

// TODO: postman
// TODO: test
@RestController
@RequestMapping("/api/diagnosis")
@RequiredArgsConstructor
@Tag(name = "Diagnosis", description = "Endpoints for managing diagnosis")
public class DiagnosisController {
    private final DiagnosisService diagnosisService;
    
    @Operation(
        summary = "Create a new diagnosis",
        description = "Registers a new diagnosis."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Diagnosis created successfully",
                     content = @Content(schema = @Schema(implementation = Doctor.class))),
        @ApiResponse(responseCode = "400", description = "Validation errors")
    })
    @PostMapping("/create")
    public Mono<ResponseEntity<Diagnosis>> createDiagnosis(@Valid @RequestBody Diagnosis diagnosis) {
        return this.diagnosisService.createDiagnosis(diagnosis)
                .map(diagnosis1 -> ResponseEntity
                        .created(URI.create("/api/diagnosis/" + diagnosis1.getId()))
                        .body(diagnosis1));
    }
}
