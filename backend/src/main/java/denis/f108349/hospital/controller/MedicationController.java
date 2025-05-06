package denis.f108349.hospital.controller;

import denis.f108349.hospital.dto.MedicationDto;
import denis.f108349.hospital.service.MedicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

// TODO: test
// TODO: postman
@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
@Tag(name = "Mecications", description = "Endpoints for managing medications")
public class MedicationController {
    private final MedicationService medicationService;
    
    @Operation(
        summary = "Get all medications",
        description = "Retrieves all medications"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved medications list"),
    })
    @GetMapping("/all")
    public Flux<MedicationDto> getAllMedications() {
        return this.medicationService.getAllMedications()
                .map(medications -> new MedicationDto(medications.getId(), medications.getName()));
    }
}
