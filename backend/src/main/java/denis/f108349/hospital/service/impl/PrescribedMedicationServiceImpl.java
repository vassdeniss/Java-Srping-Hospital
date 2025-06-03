package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.model.PrescribedMedication;
import denis.f108349.hospital.data.repo.PrescribedMedicationRepository;
import denis.f108349.hospital.service.PrescribedMedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

// TODO: Test
@Service
@RequiredArgsConstructor
public class PrescribedMedicationServiceImpl implements PrescribedMedicationService {
    private final PrescribedMedicationRepository prescribedMedicationRepository;

    @Override
    @PreAuthorize("hasRole('doctor')")
    public Mono<PrescribedMedication> createPrescribedMedication(PrescribedMedication prescribedMedication) {
        return this.prescribedMedicationRepository.save(prescribedMedication);
    }
}
