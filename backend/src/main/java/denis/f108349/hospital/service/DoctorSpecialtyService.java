package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.DoctorSpecialty;
import reactor.core.publisher.Mono;

public interface DoctorSpecialtyService {
    Mono<DoctorSpecialty> createDoctorSpecialty(String doctorId, String specialtyId);
}
