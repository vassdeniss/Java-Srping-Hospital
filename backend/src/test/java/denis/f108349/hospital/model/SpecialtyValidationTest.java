package denis.f108349.hospital.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SpecialtyValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void specialtyValidation_WithValidFields_ShouldPassWithoutViolations() {
        // Arrange
        Specialty specialty = new Specialty(
            UUID.randomUUID().toString(),
            "Cardiology"
        );

        // Act
        Set<ConstraintViolation<Specialty>> violations = validator.validate(specialty);

        // Assert
        assertTrue(violations.isEmpty(), "Valid specialty should have no violations");
    }

    @Test
    void nameValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        Specialty specialty = new Specialty(
            UUID.randomUUID().toString(),
            "   "  // Blank name
        );

        // Act
        Set<ConstraintViolation<Specialty>> violations = validator.validate(specialty);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Specialty> violation = violations.iterator().next();
        assertEquals("Specialty name is required", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
    }

    @Test
    void nameValidation_WhenExceeds255Characters_ShouldFailWithSizeViolation() {
        // Arrange
        String longName = "A".repeat(256);
        Specialty specialty = new Specialty(
            UUID.randomUUID().toString(),
            longName
        );

        // Act
        Set<ConstraintViolation<Specialty>> violations = validator.validate(specialty);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Specialty> violation = violations.iterator().next();
        assertEquals("Specialty name must be at most 255 characters", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
    }

    @Test
    void nameValidation_WhenExactly255Characters_ShouldPassValidation() {
        // Arrange
        String maxLengthName = "C".repeat(255);
        Specialty specialty = new Specialty(
            UUID.randomUUID().toString(),
            maxLengthName
        );

        // Act
        Set<ConstraintViolation<Specialty>> violations = validator.validate(specialty);

        // Assert
        assertTrue(violations.isEmpty(), "255 character name should be valid");
    }

    @Test
    void nameValidation_WhenNameIsNull_ShouldFailWithRequiredMessage() {
        // Arrange
        Specialty specialty = new Specialty(
            UUID.randomUUID().toString(),
            null  // Null name
        );

        // Act
        Set<ConstraintViolation<Specialty>> violations = validator.validate(specialty);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Specialty> violation = violations.iterator().next();
        assertEquals("Specialty name is required", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
    }
}
