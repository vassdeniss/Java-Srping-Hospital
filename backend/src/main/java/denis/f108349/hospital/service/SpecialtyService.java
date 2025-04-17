package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Specialty;
import reactor.core.publisher.Flux;

public interface SpecialtyService {
    Flux<Specialty> getAllSpecialties();
}
