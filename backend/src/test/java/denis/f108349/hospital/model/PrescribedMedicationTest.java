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

public class PrescribedMedicationTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void prescribedMedicationValidation_WithValidFields_ShouldPassWithoutViolations() {
        // Arrange
        PrescribedMedication pm = new PrescribedMedication(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "500mg",
            "Once daily",
            "7 days",
            "Take with food"
        );

        // Act
        Set<ConstraintViolation<PrescribedMedication>> violations = validator.validate(pm);

        // Assert
        assertTrue(violations.isEmpty(), "Valid prescription should have no violations");
    }

    @Test
    void visitIdValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        PrescribedMedication pm = new PrescribedMedication(
            "   ",
            UUID.randomUUID().toString(),
            "500mg",
            "Once daily",
            "7 days",
            null
        );

        // Act
        Set<ConstraintViolation<PrescribedMedication>> violations = validator.validate(pm);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<PrescribedMedication> violation = violations.iterator().next();
        assertEquals("Visit ID is required", violation.getMessage());
        assertEquals("visitId", violation.getPropertyPath().toString());
    }

    @Test
    void medicationIdValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        PrescribedMedication pm = new PrescribedMedication(
            UUID.randomUUID().toString(),
            "   ",
            "500mg",
            "Once daily",
            "7 days",
            null
        );

        // Act
        Set<ConstraintViolation<PrescribedMedication>> violations = validator.validate(pm);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Medication ID is required", 
                    violations.iterator().next().getMessage());
    }

    @Test
    void dosageValidation_WhenExceeds100Characters_ShouldFailWithSizeViolation() {
        // Arrange
        String longDosage = "D".repeat(101);
        PrescribedMedication pm = new PrescribedMedication(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            longDosage,
            "Valid frequency",
            "Valid duration",
            null
        );

        // Act
        Set<ConstraintViolation<PrescribedMedication>> violations = validator.validate(pm);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Dosage must be at most 100 characters", 
                    violations.iterator().next().getMessage());
    }

    @Test
    void frequencyValidation_WhenExceeds100Characters_ShouldFailWithSizeViolation() {
        // Arrange
        String longFrequency = "F".repeat(101);
        PrescribedMedication pm = new PrescribedMedication(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "Valid dosage",
            longFrequency,
            "Valid duration",
            null
        );

        // Act
        Set<ConstraintViolation<PrescribedMedication>> violations = validator.validate(pm);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Frequency must be at most 100 characters", 
                    violations.iterator().next().getMessage());
    }

    @Test
    void durationValidation_WhenExceeds100Characters_ShouldFailWithSizeViolation() {
        // Arrange
        String longDuration = "D".repeat(101);
        PrescribedMedication pm = new PrescribedMedication(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "Valid dosage",
            "Valid frequency",
            longDuration,
            null
        );

        // Act
        Set<ConstraintViolation<PrescribedMedication>> violations = validator.validate(pm);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Duration must be at most 100 characters", 
                    violations.iterator().next().getMessage());
    }

    @Test
    void instructionsValidation_WhenExceeds1000Characters_ShouldFailWithSizeViolation() {
        // Arrange
        String longInstructions = "I".repeat(1001);
        PrescribedMedication pm = new PrescribedMedication(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "Valid dosage",
            "Valid frequency",
            "Valid duration",
            longInstructions
        );

        // Act
        Set<ConstraintViolation<PrescribedMedication>> violations = validator.validate(pm);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Instructions must be at most 1000 characters", 
                    violations.iterator().next().getMessage());
    }

    @Test
    void instructionsValidation_WhenNull_ShouldPassValidation() {
        // Arrange
        PrescribedMedication pm = new PrescribedMedication(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "500mg",
            "Once daily",
            "7 days",
            null
        );

        // Act
        Set<ConstraintViolation<PrescribedMedication>> violations = validator.validate(pm);

        // Assert
        assertTrue(violations.isEmpty(), "Null instructions should be valid");
    }

    @Test
    void boundaryValues_WhenAtMaxLength_ShouldPassValidation() {
        // Arrange
        String maxDosage = "D".repeat(100);
        String maxFrequency = "F".repeat(100);
        String maxDuration = "D".repeat(100);
        String maxInstructions = "I".repeat(1000);

        PrescribedMedication pm = new PrescribedMedication(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            maxDosage,
            maxFrequency,
            maxDuration,
            maxInstructions
        );

        // Act
        Set<ConstraintViolation<PrescribedMedication>> violations = validator.validate(pm);

        // Assert
        assertTrue(violations.isEmpty(), "Max length values should be valid");
    }
}
