package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Visit;
import denis.f108349.hospital.data.projection.VisitProjection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface VisitService {
    Flux<VisitProjection> getAllById(String id);
    
    Mono<Visit> createVisit(String patientId, String doctorId, LocalDateTime date);
    
    Mono<Visit> updateVisit(String id, boolean isResolved);
}
