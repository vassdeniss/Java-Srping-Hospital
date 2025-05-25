package denis.f108349.hospital.controller;

import denis.f108349.hospital.data.model.Visit;
import denis.f108349.hospital.dto.VisitDto;
import denis.f108349.hospital.data.projection.VisitProjection;
import denis.f108349.hospital.dto.VisitRequest;
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
    private final UserService userService;
    
    @Operation(summary = "Get all visits for a patient- or doctor-id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved visits")
    })
    @GetMapping("/all/{id}")
    public Flux<VisitDto> getAllVisits(@PathVariable String id) {
        return this.visitService.getAllById(id).flatMap(this::toVisitDto);
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
    
    @Operation(
        summary = "Updates a visit",
        description = "Updates a visit by the ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Visit updated successfully"),
        @ApiResponse(responseCode = "404", description = "Visit not found")
    })
    @PatchMapping("/{id}")
    public Mono<ResponseEntity<Void>> patchVisit(
        @PathVariable String id,
        @RequestBody boolean visit
    ) {
        return this.visitService.updateVisit(id, visit)
                .thenReturn(ResponseEntity.noContent().build());
    }
    
    private Mono<VisitDto> toVisitDto(VisitProjection visit) {
        return this.userService.getUserById(visit.doctorId())
              .flatMap(doctor -> this.userService.getUserById(visit.patientId())
              .map(patient -> new VisitDto(
                      visit.id(),
                      visit.visitDate(),
                      doctor.getFirstName(),
                      doctor.getLastName(),
                      patient.getFirstName(),
                      patient.getLastName()
              ))
        );
    }
}
