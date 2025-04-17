package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.model.Specialty;
import denis.f108349.hospital.data.repo.SpecialtyRepository;
import denis.f108349.hospital.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyRepository specialtyRepository;
    
    @Override
    public Flux<Specialty> getAllSpecialties() {
        return this.specialtyRepository.findAll();
    }
}
