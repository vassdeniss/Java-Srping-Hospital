package denis.f108349.hospital.controller;

import denis.f108349.hospital.data.model.PrescribedMedication;
import denis.f108349.hospital.service.PrescribedMedicationService;
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
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
@Tag(name = "Prescribed Medication", description = "Endpoints for managing prescribed medications")
public class PrescribedMedicationController {
    private final PrescribedMedicationService prescribedMedicationService;
    
    @Operation(
        summary = "Create a new prescription",
        description = "Registers a new prescription."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Prescription created successfully",
                     content = @Content(schema = @Schema(implementation = PrescribedMedication.class))),
        @ApiResponse(responseCode = "400", description = "Validation errors")
    })
    @PostMapping("/create")
    public Mono<ResponseEntity<PrescribedMedication>> createPrescribedMedication(
            @Valid @RequestBody PrescribedMedication prescribedMedication) {
        return this.prescribedMedicationService.createPrescribedMedication(prescribedMedication)
                .map(prescribedMedication1 -> ResponseEntity
                        .created(URI.create("/api/prescriptions/" + prescribedMedication1.getId()))
                        .body(prescribedMedication1));
    }
}
