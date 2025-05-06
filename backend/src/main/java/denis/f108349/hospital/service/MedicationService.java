package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Medication;
import reactor.core.publisher.Flux;

public interface MedicationService {
    Flux<Medication> getAllMedications();
}
