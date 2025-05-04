package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.model.Visit;
import denis.f108349.hospital.data.repo.VisitRepository;
import denis.f108349.hospital.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

// TODO: test
@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {
    private final VisitRepository visitRepository;

    @Override
    public Flux<Visit> getAllByPatientId(String patientId) {
        return this.visitRepository.findAllByPatientId(patientId);
    }

    @Override
    public Mono<Visit> createVisit(String patientId, String doctorId, LocalDateTime date) {
        Visit visit = new Visit(patientId, doctorId, date);
        return this.visitRepository.save(visit);
    }
}
