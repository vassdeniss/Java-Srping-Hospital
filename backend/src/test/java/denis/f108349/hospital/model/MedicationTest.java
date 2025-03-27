package denis.f108349.hospital.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MedicationTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void medicationValidation_WithValidFields_ShouldPassWithoutViolations() {
        // Arrange
        Medication medication = new Medication("Ibuprofen");

        // Act
        Set<ConstraintViolation<Medication>> violations = validator.validate(medication);

        // Assert
        assertTrue(violations.isEmpty(), "Valid medication should have no violations");
    }

    @Test
    void nameValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        Medication medication = new Medication("   ");

        // Act
        Set<ConstraintViolation<Medication>> violations = validator.validate(medication);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Medication> violation = violations.iterator().next();
        assertEquals("Medication name is required", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
    }

    @Test
    void nameValidation_WhenExceeds255Characters_ShouldFailWithSizeViolation() {
        // Arrange
        String longName = "M".repeat(256);
        Medication medication = new Medication(longName);

        // Act
        Set<ConstraintViolation<Medication>> violations = validator.validate(medication);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Medication name must be at most 255 characters", 
                    violations.iterator().next().getMessage());
    }

    @Test
    void nameValidation_WhenExactly255Characters_ShouldPassValidation() {
        // Arrange
        String validName = "A".repeat(255);
        Medication medication = new Medication(validName);

        // Act
        Set<ConstraintViolation<Medication>> violations = validator.validate(medication);

        // Assert
        assertTrue(violations.isEmpty(), "255 character name should be valid");
    }
}
