package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Visit;
import denis.f108349.hospital.data.projection.VisitProjection;
import denis.f108349.hospital.data.repo.VisitRepository;
import denis.f108349.hospital.exception.EntityNotFoundException;
import denis.f108349.hospital.service.impl.VisitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class VisitServiceTest {
    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitServiceImpl visitService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllById_ShouldReturnVisitsForPatientOrDoctor() {
        // Arrange
        String id = "patient123";
        VisitProjection visit1 = mock(VisitProjection.class);
        VisitProjection visit2 = mock(VisitProjection.class);

        when(this.visitRepository.findAllByPatientIdOrDoctorId(id, id))
            .thenReturn(Flux.just(visit1, visit2));

        // Act
        Flux<VisitProjection> result = this.visitService.getAllById(id);

        // Assert
        StepVerifier.create(result)
            .expectNextCount(2)
            .verifyComplete();

        verify(this.visitRepository, times(1)).findAllByPatientIdOrDoctorId(id, id);
    }

    @Test
    void createVisit_ShouldSaveAndReturnVisit() {
        // Arrange
        String patientId = "patient123";
        String doctorId = "doctor456";
        LocalDateTime date = LocalDateTime.now();

        Visit visit = new Visit(patientId, doctorId, date, false);

        when(this.visitRepository.save(any(Visit.class))).thenReturn(Mono.just(visit));

        // Act
        Mono<Visit> result = this.visitService.createVisit(patientId, doctorId, date);

        // Assert
        StepVerifier.create(result)
            .expectNextMatches(v -> 
                v.getPatientId().equals(patientId) &&
                v.getDoctorId().equals(doctorId) &&
                v.getVisitDate().equals(date) &&
                !v.isResolved()
            )
            .verifyComplete();

        verify(this.visitRepository, times(1)).save(any(Visit.class));
    }

    @Test
    void updateVisit_WhenVisitExists_ShouldUpdateAndReturnVisit() {
        // Arrange
        String visitId = "visit789";
        boolean resolved = true;

        Visit existingVisit = new Visit("patient123", "doctor456", LocalDateTime.now(), false);
        existingVisit.setId(visitId);

        Visit updatedVisit = new Visit(existingVisit.getPatientId(), existingVisit.getDoctorId(), existingVisit.getVisitDate(), resolved);
        updatedVisit.setId(visitId);

        when(this.visitRepository.findById(visitId)).thenReturn(Mono.just(existingVisit));
        when(this.visitRepository.save(any(Visit.class))).thenReturn(Mono.just(updatedVisit));

        // Act
        Mono<Visit> result = this.visitService.updateVisit(visitId, resolved);

        // Assert
        StepVerifier.create(result)
            .expectNextMatches(v -> v.isResolved() == resolved && v.getId().equals(visitId))
            .verifyComplete();

        verify(this.visitRepository, times(1)).findById(visitId);
        verify(this.visitRepository, times(1)).save(existingVisit);
    }

    @Test
    void updateVisit_WhenVisitDoesNotExist_ShouldReturnError() {
        // Arrange
        String visitId = "nonexistent";

        when(this.visitRepository.findById(visitId)).thenReturn(Mono.empty());

        // Act
        Mono<Visit> result = this.visitService.updateVisit(visitId, true);

        // Assert
        StepVerifier.create(result)
            .expectError(EntityNotFoundException.class)
            .verify();

        verify(this.visitRepository, times(1)).findById(visitId);
        verify(this.visitRepository, times(0)).save(any());
    }
}
