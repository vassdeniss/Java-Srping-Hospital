package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.model.Diagnosis;
import denis.f108349.hospital.data.repo.DiagnosisRepository;
import denis.f108349.hospital.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

// TODO: Test
@Service
@RequiredArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;
    
    @Override
    @PreAuthorize("hasRole('doctor')")
    public Mono<Diagnosis> createDiagnosis(Diagnosis diagnosis) {
        return this.diagnosisRepository.save(diagnosis);
    }
}
