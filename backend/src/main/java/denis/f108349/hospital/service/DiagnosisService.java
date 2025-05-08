package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Diagnosis;
import reactor.core.publisher.Mono;

public interface DiagnosisService {
    Mono<Diagnosis> createDiagnosis(Diagnosis diagnosis);
}
