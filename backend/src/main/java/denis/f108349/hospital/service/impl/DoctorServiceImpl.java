package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.model.Doctor;
import denis.f108349.hospital.data.repo.DoctorRepository;
import denis.f108349.hospital.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Override
    public Mono<Doctor> getDoctorByKeycloakId(String id) {
        return this.doctorRepository.findByKeycloakId(id);
    }

    @Override
    public Mono<Doctor> createDoctor(String userId) {
        Doctor doctor = new Doctor(userId, false);
        return this.doctorRepository.save(doctor);
    }
}
