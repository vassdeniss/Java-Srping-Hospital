package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.model.Medication;
import denis.f108349.hospital.data.repo.MedicationRepository;
import denis.f108349.hospital.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

// TODO: Test
@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;
    
    @Override
    @PreAuthorize("hasAnyRole('doctor', 'admin')")
    public Flux<Medication> getAllMedications() {
        return this.medicationRepository.findAll();
    }
}
