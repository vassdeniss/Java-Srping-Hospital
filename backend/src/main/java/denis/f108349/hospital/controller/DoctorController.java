package denis.f108349.hospital.controller;

import denis.f108349.hospital.data.model.Doctor;
import denis.f108349.hospital.data.model.Patient;
import denis.f108349.hospital.data.projection.HistoryProjection;
import denis.f108349.hospital.dto.DoctorRequest;
import denis.f108349.hospital.dto.DoctorDto;
import denis.f108349.hospital.dto.PatientDto;
import denis.f108349.hospital.exception.EntityNotFoundException;
import denis.f108349.hospital.service.DoctorService;
import denis.f108349.hospital.service.DoctorSpecialtyService;
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
import java.util.List;

// TODO: Test
// TODO: postman
@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctors", description = "Endpoints for managing doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final DoctorSpecialtyService doctorSpecialtyService;
    private final UserService userService;

    @Operation(
        summary = "Create a new doctor",
        description = "Registers a new doctor with a specified keycloak ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Doctor created successfully",
                     content = @Content(schema = @Schema(implementation = Doctor.class))),
        @ApiResponse(responseCode = "400", description = "Validation errors")
    })
    @PostMapping("/create")
    public Mono<ResponseEntity<Doctor>> createDoctor(@Valid @RequestBody DoctorRequest request) {
        return this.doctorService.createDoctor(request.getId())
                .map(doctor -> ResponseEntity
                        .created(URI.create("/api/doctors/" + doctor.getKeycloakId()))
                        .body(doctor));
    }
    
    @Operation(
        summary = "Get doctor by keycloak ID",
        description = "Retrieves the doctor details and associated user by the doctor ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Patient retrieved successfully",
                     content = @Content(schema = @Schema(implementation = PatientDto.class))),
        @ApiResponse(responseCode = "404", description = "Doctor or User not found")
    })
    @GetMapping("/{id}")
    public Mono<DoctorDto> getDoctorById(@PathVariable String id) {
        return this.doctorService.getDoctorByKeycloakId(id)
            .switchIfEmpty(Mono.error(new EntityNotFoundException("Doctor not found")))
            .flatMap(doctor -> this.userService.getUserById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("User not found")))
                .flatMap(user -> Mono.just(new DoctorDto(doctor, user)))
            );
    }
    
    @Operation(
        summary = "Get all doctors",
        description = "Retrieves all doctors with their user details"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved doctors list"),
    })
    @GetMapping("/all")
    public Flux<DoctorDto> getAllDoctors(@RequestParam(value = "gp", required = false) String gpParam) {
        Boolean gp = gpParam != null ? Boolean.parseBoolean(gpParam) : null;
        
        return this.doctorService.getAllDoctors(gp)
            .flatMap(doctor -> this.userService.getUserById(doctor.getKeycloakId())
                .map(user -> new DoctorDto(doctor, user)));
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
    
    // TODO: api info
    @GetMapping("/{doctorId}/history")
    public Flux<HistoryProjection> getDoctorHistory(@PathVariable String doctorId) {
        return this.doctorService.getDoctorHistory(doctorId)
                .flatMap(history -> this.userService.getUserById(history.patientId())
                        .flatMap(user -> Mono.just(new HistoryProjection(
                                history.patientId(),
                                history.doctorId(),
                                user.getFirstName() + " " + user.getLastName(),
                                "",
                                history.diagnosis(),
                                history.treatment(),
                                history.dosage(),
                                history.frequency(),
                                history.duration(),
                                history.sickLeaveDays(),
                                history.visitDate()
                        ))));
    }
}
