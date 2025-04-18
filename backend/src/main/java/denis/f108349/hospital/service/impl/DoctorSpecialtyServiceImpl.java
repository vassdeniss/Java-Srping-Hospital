package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.model.DoctorSpecialty;
import denis.f108349.hospital.data.repo.DoctorSpecialtyRepository;
import denis.f108349.hospital.service.DoctorSpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DoctorSpecialtyServiceImpl implements DoctorSpecialtyService {
    private final DoctorSpecialtyRepository doctorSpecialtyRepository;
    
    @Override
    public Mono<DoctorSpecialty> createDoctorSpecialty(String doctorId, String specialtyId) {
        DoctorSpecialty doctorSpecialty = new DoctorSpecialty(doctorId, specialtyId);
        return this.doctorSpecialtyRepository.save(doctorSpecialty);
    }
}
