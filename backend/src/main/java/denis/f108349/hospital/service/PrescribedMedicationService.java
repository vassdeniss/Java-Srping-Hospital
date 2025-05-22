package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.PrescribedMedication;
import reactor.core.publisher.Mono;

public interface PrescribedMedicationService {
    Mono<PrescribedMedication> createPrescribedMedication(PrescribedMedication prescribedMedication);
}
