package denis.f108349.hospital.controller;

import denis.f108349.hospital.data.model.Doctor;
import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.dto.DoctorRequest;
import denis.f108349.hospital.exception.EntityNotFoundException;
import denis.f108349.hospital.service.DoctorService;
import denis.f108349.hospital.service.DoctorSpecialtyService;
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

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctors", description = "Endpoints for managing doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final DoctorSpecialtyService doctorSpecialtyService;

    @Operation(
        summary = "Create a new doctor",
        description = "Registers a new doctor with a specified keycloak ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Doctor created successfully",
                     content = @Content(schema = @Schema(implementation = Patient.class))),
        @ApiResponse(responseCode = "400", description = "Validation errors")
    })
    @PostMapping("/create")
    public Mono<Doctor> createDoctor(@Valid @RequestBody DoctorRequest request) {
        return this.doctorService.createDoctor(request.getId());
    }
    
    @Operation(
        summary = "Assign specialties to a doctor",
        description = "Assigns a list of specialties to a doctor."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Specialties assigned successfully",
                     content = @Content(schema = @Schema(implementation = Patient.class))),
        @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @PostMapping("/assign/{keycloakId}")
    public Mono<ResponseEntity<Void>> assign(@PathVariable String keycloakId, @RequestBody List<String> specialties) {
        return this.doctorService.getDoctorByKeycloakId(keycloakId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Doctor not found")))
                .flatMap(doctor -> 
                        Flux.fromIterable(specialties).flatMap(
                                specialtyId -> this.doctorSpecialtyService.createDoctorSpecialty(doctor.getId(), specialtyId))
                                .then(Mono.empty())
                )
                .thenReturn(ResponseEntity.noContent().build());
    }
}
