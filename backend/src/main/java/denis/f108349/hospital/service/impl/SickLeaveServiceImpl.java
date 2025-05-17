package denis.f108349.hospital.service.impl;

import denis.f108349.hospital.data.model.SickLeave;
import denis.f108349.hospital.data.repo.SickLeaveRepository;
import denis.f108349.hospital.service.SickLeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

// TODO: Test
@Service
@RequiredArgsConstructor
public class SickLeaveServiceImpl implements SickLeaveService {
    private final SickLeaveRepository sickLeaveRepository;

    @Override
    public Mono<SickLeave> createSickLeave(SickLeave sickLeave) {
        return this.sickLeaveRepository.save(sickLeave);
    }
}
