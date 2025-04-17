package denis.f108349.hospital.controller;

import denis.f108349.hospital.data.model.Specialty;
import denis.f108349.hospital.service.SpecialtyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/specialties")
@RequiredArgsConstructor
@Tag(name = "Specialties", description = "Endpoints for managing specialties")
public class SpecialtyController {
    private final SpecialtyService specialtyService;
    
    @Operation(
        summary = "Get all specialties",
        description = "Retrieves all specialties"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved specialties list")
    })
    @GetMapping("/all")
    public Flux<Specialty> getAllSpecialties() {
        return this.specialtyService.getAllSpecialties();
    }
}
