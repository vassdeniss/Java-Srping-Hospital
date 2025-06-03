package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.model.Doctor;
import denis.f108349.hospital.data.projection.HistoryProjection;
import denis.f108349.hospital.data.repo.DoctorRepository;
import denis.f108349.hospital.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Override
    @PreAuthorize("hasAnyRole('admin', 'patient', 'doctor')")
    public Flux<Doctor> getAllDoctors(Boolean gp) {
        if (gp == null) {
            return this.doctorRepository.findAll();
        } else {
            return this.doctorRepository.findAllByIsGpIs(gp);
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('admin', 'doctor', 'patient')")
    public Mono<Doctor> getDoctorByKeycloakId(String id) {
        return this.doctorRepository.findByKeycloakId(id);
    }

    @Override
    @PreAuthorize("hasAnyRole('admin', 'doctor', 'patient')")
    public Mono<Doctor> getDoctorById(String id) {
        return this.doctorRepository.findById(id);
    }

    @Override
    @PreAuthorize("hasAnyRole('admin', 'doctor')")
    public Flux<HistoryProjection> getDoctorHistory(String doctorId) {
        return this.doctorRepository.findDoctorHistoryById(doctorId);
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    public Mono<Doctor> createDoctor(String userId) {
        Doctor doctor = new Doctor(userId, false);
        return this.doctorRepository.save(doctor);
    }
}
