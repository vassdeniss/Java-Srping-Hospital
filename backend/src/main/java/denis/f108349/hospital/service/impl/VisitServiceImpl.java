package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.model.Visit;
import denis.f108349.hospital.data.repo.VisitRepository;
import denis.f108349.hospital.data.projection.VisitProjection;
import denis.f108349.hospital.exception.EntityNotFoundException;
import denis.f108349.hospital.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {
    private final VisitRepository visitRepository;

    @Override
    @PreAuthorize("hasAnyRole('admin', 'patient', 'doctor')")
    public Flux<VisitProjection> getAllById(String id) {
        return this.visitRepository.findAllByPatientIdOrDoctorId(id, id);
    }

    @Override
    @PreAuthorize("hasRole('patient')")
    public Mono<Visit> createVisit(String patientId, String doctorId, LocalDateTime date) {
        Visit visit = new Visit(patientId, doctorId, date, false);
        return this.visitRepository.save(visit);
    }

    @Override
    @PreAuthorize("hasRole('doctor')")
    public Mono<Visit> updateVisit(String visitId, boolean isResolved) {
        return this.visitRepository.findById(visitId)
                .flatMap(visit1 -> {
                    visit1.setResolved(isResolved);
                    return this.visitRepository.save(visit1);
                }).switchIfEmpty(Mono.error(new EntityNotFoundException("Visit not found")));
    }
}
