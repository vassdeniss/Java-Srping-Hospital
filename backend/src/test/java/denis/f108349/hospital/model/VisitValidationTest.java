package denis.f108349.hospital.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VisitValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void visitValidation_WithValidFields_ShouldPassWithoutViolations() {
        // Arrange
        Visit visit = new Visit(UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                LocalDate.now().minusDays(1));

        // Act
        Set<ConstraintViolation<Visit>> violations = validator.validate(visit);

        // Assert
        assertTrue(violations.isEmpty(), "Valid visit should have no violations");
    }

    @Test
    void patientIdValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        Visit visit = new Visit("   ",
            UUID.randomUUID().toString(),
            LocalDate.now());

        // Act
        Set<ConstraintViolation<Visit>> violations = validator.validate(visit);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Visit> violation = violations.iterator().next();
        assertEquals("Patient ID is required", violation.getMessage());
        assertEquals("patientId", violation.getPropertyPath().toString());
    }

    @Test
    void doctorIdValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        Visit visit = new Visit(UUID.randomUUID().toString(),
            "   ",
            LocalDate.now());

        // Act
        Set<ConstraintViolation<Visit>> violations = validator.validate(visit);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Visit> violation = violations.iterator().next();
        assertEquals("Doctor ID is required", violation.getMessage());
        assertEquals("doctorId", violation.getPropertyPath().toString());
    }

    @Test
    void visitDateValidation_WhenNull_ShouldFailWithRequiredMessage() {
        // Arrange
        Visit visit = new Visit(UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                null);

        // Act
        Set<ConstraintViolation<Visit>> violations = validator.validate(visit);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Visit> violation = violations.iterator().next();
        assertEquals("Visit date is required", violation.getMessage());
        assertEquals("visitDate", violation.getPropertyPath().toString());
    }

    @Test
    void visitDateValidation_WhenFutureDate_ShouldFailWithPastOrPresentViolation() {
        // Arrange
        Visit visit = new Visit(UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            LocalDate.now().plusDays(1));

        // Act
        Set<ConstraintViolation<Visit>> violations = validator.validate(visit);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Visit> violation = violations.iterator().next();
        assertEquals("Visit date cannot be in the future", violation.getMessage());
        assertEquals("visitDate", violation.getPropertyPath().toString());
    }

    @Test
    void visitDateValidation_WhenCurrentDate_ShouldPassValidation() {
        // Arrange
        Visit visit = new Visit(UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            LocalDate.now());

        // Act
        Set<ConstraintViolation<Visit>> violations = validator.validate(visit);

        // Assert
        assertTrue(violations.isEmpty(), "Current date should be valid");
    }
}
