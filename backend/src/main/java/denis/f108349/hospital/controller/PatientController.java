package denis.f108349.hospital.controller;

import denis.f108349.hospital.dto.PatientRequest;
import denis.f108349.hospital.dto.PatientDto;
import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.exception.EntityNotFoundException;
import denis.f108349.hospital.service.DoctorService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

// TODO: fix postman endpoints
@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Tag(name = "Patients", description = "Endpoints for managing patients")
public class PatientController {
    private final UserService userService;
    private final PatientService patientService;
    private final DoctorService doctorService;  

    @Operation(
        summary = "Create a new patient",
        description = "Registers a new patient with a specified keycloak ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Patient created successfully",
                     content = @Content(schema = @Schema(implementation = Patient.class))),
        @ApiResponse(responseCode = "400", description = "Validation errors")
    })
    @PostMapping("/create")
    public Mono<ResponseEntity<Patient>> createPatient(@Valid @RequestBody PatientRequest request) {
        return this.patientService.createPatient(request.getId())
                .map(patient -> ResponseEntity
                        .created(URI.create("/api/patients/" + patient.getKeycloakId()))
                        .body(patient));
    }
    
    @Operation(
        summary = "Get patient by keycloak ID",
        description = "Retrieves the patient details and associated user by the patient ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Patient retrieved successfully",
                     content = @Content(schema = @Schema(implementation = PatientDto.class))),
        @ApiResponse(responseCode = "404", description = "Patient or User not found")
    })
    @GetMapping("/{id}")
    public Mono<PatientDto> getPatientById(@PathVariable String id) {
        return this.patientService.getPatientByKeycloakId(id)
            .switchIfEmpty(Mono.error(new EntityNotFoundException("Patient not found")))
            .flatMap(patient -> this.userService.getUserById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("User not found")))
                .flatMap(patientUser -> Mono.justOrEmpty(patient.getGpDoctorId())
                    .flatMap(this.doctorService::getDoctorById)
                    .flatMap(doctor -> this.userService.getUserById(doctor.getKeycloakId()))
                    .flatMap(doctorUser -> Mono.just(new PatientDto(patient, patientUser, doctorUser)))
                    .defaultIfEmpty(new PatientDto(patient, patientUser, null))
                )
            );
    }
    
    @Operation(
        summary = "Get all patients",
        description = "Retrieves all patients with their user details"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved patients list"),
    })
    @GetMapping("/all")
    public Flux<PatientDto> getAllPatients() {
        return this.patientService.getAllPatients()
            .flatMap(patient -> this.userService.getUserById(patient.getKeycloakId())
                .flatMap(user -> Mono.justOrEmpty(patient.getGpDoctorId())
                    .flatMap(this.userService::getUserById)
                    .map(doctor -> new PatientDto(patient, user, doctor))
                    .defaultIfEmpty(new PatientDto(patient, user, null))
                )
            );

    } 
    
    // TODO: unit test
    @Operation(
        summary = "Changes a patient's GP",
        description = "Updates a patients own GP by their Keycloak ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Patient updated successfully",
                     content = @Content(schema = @Schema(implementation = PatientDto.class))),
        @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @PatchMapping("/{keycloakId}")
    public Mono<Patient> patchPatient(
        @PathVariable String keycloakId,
        @Valid @RequestBody Patient patient
    ) {
        return patientService.updatePatient(keycloakId, patient);
    }
    
    @Operation(
        summary = "Delete a patient by Keycloak ID",
        description = "Deletes the patient record associated with the provided Keycloak ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Patient successfully deleted"),
    })
    @DeleteMapping("/delete/{keycloakId}")
    public Mono<ResponseEntity<Void>> deletePatientByKeycloakId(@PathVariable String keycloakId) {
        return this.patientService.deletePatientByKeycloakId(keycloakId)
            .thenReturn(ResponseEntity.noContent().build());
    }
}
