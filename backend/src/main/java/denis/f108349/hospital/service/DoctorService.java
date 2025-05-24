package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Doctor;
import denis.f108349.hospital.data.projection.HistoryProjection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DoctorService {
    Flux<Doctor> getAllDoctors(Boolean gp);
    
    Mono<Doctor> getDoctorByKeycloakId(String id);
    
    Mono<Doctor> getDoctorById(String id);
    
    Flux<HistoryProjection> getDoctorHistory(String doctorId);
    
    Mono<Doctor> createDoctor(String userId);
}
