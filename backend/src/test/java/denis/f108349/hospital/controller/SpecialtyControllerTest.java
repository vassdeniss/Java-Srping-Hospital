package denis.f108349.hospital.controller;

import denis.f108349.hospital.data.model.Specialty;
import denis.f108349.hospital.service.SpecialtyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.*;

@WebFluxTest(SpecialtyController.class)
@ExtendWith(MockitoExtension.class)
public class SpecialtyControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    
    @MockBean
    private SpecialtyService specialtyService;
    
    @Test
    void getAllSpecialties_ShouldReturnAllSpecialties_WhenValidRequest() {
        // Arrange
        Specialty specialty = new Specialty("Test");
        Specialty specialty2 = new Specialty("Test2");
        
        when(this.specialtyService.getAllSpecialties()).thenReturn(Flux.just(specialty, specialty2));

        // Act and Assert
        this.webTestClient.get()
                .uri("/api/specialties/all")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Specialty.class);

        verify(this.specialtyService, times(1)).getAllSpecialties();
    }
}
