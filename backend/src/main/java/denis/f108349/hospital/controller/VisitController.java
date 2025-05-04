package denis.f108349.hospital.controller;

import denis.f108349.hospital.data.model.Visit;
import denis.f108349.hospital.dto.VisitDto;
import denis.f108349.hospital.dto.VisitRequest;
import denis.f108349.hospital.service.DoctorService;
import denis.f108349.hospital.service.UserService;
import denis.f108349.hospital.service.VisitService;
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

// TODO: test
@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
@Tag(name = "Visits", description = "Endpoints for managing visits")
public class VisitController {
    private final VisitService visitService;
    private final DoctorService doctorService;
    private final UserService userService;
    
    @Operation(summary = "Get all visits with doctor names")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved visits")
    })
    @GetMapping("/all/{patientId}")
    public Flux<VisitDto> getAllVisits(@PathVariable String patientId) {
        return this.visitService.getAllByPatientId(patientId)
            .flatMap(visit -> this.doctorService.getDoctorById(visit.getDoctorId())
                .flatMap(doctor -> this.userService.getUserById(doctor.getKeycloakId())
                    .map(user -> new VisitDto(
                        visit.getVisitDate(),
                        user.getFirstName(),
                        user.getLastName()
                    ))
                )
            );
    }
    
    @Operation(
        summary = "Create a new visit",
        description = "Registers a new visit."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Visit created successfully",
                     content = @Content(schema = @Schema(implementation = Visit.class))),
        @ApiResponse(responseCode = "400", description = "Validation errors")
    })
    @PostMapping("/create")
    public Mono<ResponseEntity<Visit>> createVisit(@Valid @RequestBody VisitRequest request) {
        return this.visitService.createVisit(request.getPatientId(), request.getDoctorId(), request.getVisitDate())
                .map(visit -> ResponseEntity
                        .created(URI.create("/api/visits/" + visit.getId()))
                        .body(visit));
    }
}
