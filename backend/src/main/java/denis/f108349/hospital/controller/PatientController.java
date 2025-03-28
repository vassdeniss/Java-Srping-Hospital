package denis.f108349.hospital.controller;

import denis.f108349.hospital.dto.UserRegistrationRequest;
import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.service.PatientService;
import denis.f108349.hospital.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Tag(name = "Patients", description = "Endpoints for managing patients")
public class PatientController {
    private final UserService userService;
    private final PatientService patientService;

    @Operation(
        summary = "Create a new patient",
        description = "Registers a new patient by first creating a user and then assigning them as a patient."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Patient created successfully",
                     content = @Content(schema = @Schema(implementation = Patient.class))),
        @ApiResponse(responseCode = "400", description = "Validation errors")
    })
    @PostMapping("/create")
    public Mono<Patient> createPatient(@Valid @RequestBody UserRegistrationRequest request) {
        return this.userService.createUser(request)
            .flatMap(user -> this.patientService.createPatient(user.getId()));
    }
}
