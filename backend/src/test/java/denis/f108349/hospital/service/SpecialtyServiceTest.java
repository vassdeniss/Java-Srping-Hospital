package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Specialty;
import denis.f108349.hospital.data.repo.SpecialtyRepository;
import denis.f108349.hospital.service.impl.SpecialtyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class SpecialtyServiceTest {
    @Mock
    private SpecialtyRepository specialtyRepository;
    
    @InjectMocks
    private SpecialtyServiceImpl specialtyService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void getAllSpecialties_ShouldReturnAllSpecialties() {
        // Arrange
        Specialty specialty = new Specialty("Test");
        Specialty specialty2 = new Specialty("Test2");
        
        when(this.specialtyRepository.findAll()).thenReturn(Flux.just(specialty, specialty2));
        
        // Act
        Flux<Specialty> result = this.specialtyService.getAllSpecialties();
        
        // Assert
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
        
        verify(this.specialtyRepository, times(1)).findAll();
    }
}
