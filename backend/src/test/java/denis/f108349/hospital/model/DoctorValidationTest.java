package denis.f108349.hospital.model;

import denis.f108349.hospital.data.model.Doctor;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    void doctorValidation_WithValidFields_ShouldPassWithoutViolations() {
        // Arrange
        Doctor doctor = new Doctor(
            UUID.randomUUID().toString(),
            true
        );

        // Act
        Set<ConstraintViolation<Doctor>> violations = validator.validate(doctor);

        // Assert
        assertTrue(violations.isEmpty(), "Valid doctor should have no violations");
    }

    @Test
    void keycloakIdValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        Doctor doctor = new Doctor(
            "   ",  // Blank user ID
            false
        );

        // Act
        Set<ConstraintViolation<Doctor>> violations = validator.validate(doctor);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Doctor> violation = violations.iterator().next();
        assertEquals("Keycloak ID is required", violation.getMessage());
        assertEquals("keycloakId", violation.getPropertyPath().toString());
    }
}
