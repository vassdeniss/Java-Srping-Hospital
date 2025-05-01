package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Doctor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DoctorService {
    Flux<Doctor> getAllDoctors(Boolean gp);
    
    Mono<Doctor> getDoctorByKeycloakId(String id);
    
    Mono<Doctor> getDoctorById(String id);
    
    Mono<Doctor> createDoctor(String userId);
}
