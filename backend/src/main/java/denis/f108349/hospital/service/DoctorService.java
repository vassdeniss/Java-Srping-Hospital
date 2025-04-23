package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Doctor;
import reactor.core.publisher.Mono;

public interface DoctorService {
    Mono<Doctor> getDoctorByKeycloakId(String id);
    
    Mono<Doctor> createDoctor(String userId);
}
