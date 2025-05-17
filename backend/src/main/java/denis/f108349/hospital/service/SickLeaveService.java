package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.SickLeave;
import reactor.core.publisher.Mono;

public interface SickLeaveService {
    Mono<SickLeave> createSickLeave(SickLeave sickLeave);
}
