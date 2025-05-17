package denis.f108349.hospital.controller;

import denis.f108349.hospital.data.model.SickLeave;
import denis.f108349.hospital.service.SickLeaveService;
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
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
@Tag(name = "Sick Leave", description = "Endpoints for managing sick leaves medications")
public class SickLeaveController {
    private final SickLeaveService sickLeaveService;
    
    @Operation(
        summary = "Create a new sick leave",
        description = "Registers a new sick leave."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sick leave created successfully",
                     content = @Content(schema = @Schema(implementation = SickLeave.class))),
        @ApiResponse(responseCode = "400", description = "Validation errors")
    })
    @PostMapping("/create")
    public Mono<ResponseEntity<SickLeave>> createSickLeave(@Valid @RequestBody SickLeave sickLeave) {
        return this.sickLeaveService.createSickLeave(sickLeave)
                .map(sickLeave1 -> ResponseEntity
                        .created(URI.create("/api/leaves/" + sickLeave1.getId()))
                        .body(sickLeave1));
    }
}
