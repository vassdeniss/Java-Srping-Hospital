package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Doctor;
import denis.f108349.hospital.data.model.DoctorSpecialty;
import denis.f108349.hospital.data.repo.DoctorSpecialtyRepository;
import denis.f108349.hospital.service.impl.DoctorSpecialtyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DoctorSpecialtyServiceTest {
    @Mock
    private DoctorSpecialtyRepository doctorSpecialtyRepository;
    
    @InjectMocks
    private DoctorSpecialtyServiceImpl doctorSpecialtyService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void createDoctorSpecialty_ShouldReturnDoctorSpecialty_WhenValidUserId() {
        // Arrange
        String doctorId = UUID.randomUUID().toString();
        String specialtyId = UUID.randomUUID().toString();
        DoctorSpecialty doctorSpecialty = new DoctorSpecialty(doctorId, specialtyId);

        when(this.doctorSpecialtyRepository.save(any(DoctorSpecialty.class))).thenReturn(Mono.just(doctorSpecialty));

        // Act
        Mono<DoctorSpecialty> result = this.doctorSpecialtyService.createDoctorSpecialty(doctorId, specialtyId);

        // Assert
        StepVerifier.create(result)
            .expectNextMatches(createdDoctorSpecialty -> createdDoctorSpecialty.getDoctorId().equals(doctorId) &&
                createdDoctorSpecialty.getSpecialtyId().equals(specialtyId))
            .verifyComplete();

        verify(this.doctorSpecialtyRepository, times(1)).save(any(DoctorSpecialty.class));
    }
}
