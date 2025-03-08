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

public class DiagnosisTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void diagnosisValidation_WithValidFields_ShouldPassWithoutViolations() {
        // Arrange
        Diagnosis diagnosis = new Diagnosis(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "J45",
            "Bronchial asthma"
        );

        // Act
        Set<ConstraintViolation<Diagnosis>> violations = validator.validate(diagnosis);

        // Assert
        assertTrue(violations.isEmpty(), "Valid diagnosis should have no violations");
    }

    @Test
    void visitIdValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        Diagnosis diagnosis = new Diagnosis(
            UUID.randomUUID().toString(),
            "   ",
            "E11",
            "Type 2 diabetes mellitus"
        );

        // Act
        Set<ConstraintViolation<Diagnosis>> violations = validator.validate(diagnosis);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Diagnosis> violation = violations.iterator().next();
        assertEquals("Visit ID is required", violation.getMessage());
        assertEquals("visitId", violation.getPropertyPath().toString());
    }

    @Test
    void codeValidation_WhenExceeds50Characters_ShouldFailWithSizeViolation() {
        // Arrange
        String longCode = "C".repeat(51);
        Diagnosis diagnosis = new Diagnosis(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            longCode,
            "Valid name"
        );

        // Act
        Set<ConstraintViolation<Diagnosis>> violations = validator.validate(diagnosis);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Diagnosis code must be at most 50 characters", 
                    violations.iterator().next().getMessage());
    }

    @Test
    void nameValidation_WhenExceeds255Characters_ShouldFailWithSizeViolation() {
        // Arrange
        String longName = "N".repeat(256);
        Diagnosis diagnosis = new Diagnosis(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "M54",
            longName
        );

        // Act
        Set<ConstraintViolation<Diagnosis>> violations = validator.validate(diagnosis);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Diagnosis name must be at most 255 characters", 
                    violations.iterator().next().getMessage());
    }

    @Test
    void codeValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        Diagnosis diagnosis = new Diagnosis(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "   ",
            "Valid name"
        );

        // Act
        Set<ConstraintViolation<Diagnosis>> violations = validator.validate(diagnosis);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Diagnosis code is required", 
                    violations.iterator().next().getMessage());
    }

    @Test
    void nameValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        Diagnosis diagnosis = new Diagnosis(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "R51",
            "   "
        );

        // Act
        Set<ConstraintViolation<Diagnosis>> violations = validator.validate(diagnosis);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Diagnosis name is required", 
                    violations.iterator().next().getMessage());
    }

    @Test
    void codeValidation_WhenExactly50Characters_ShouldPassValidation() {
        // Arrange
        String validCode = "C".repeat(50);
        Diagnosis diagnosis = new Diagnosis(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            validCode,
            "Valid name"
        );

        // Act
        Set<ConstraintViolation<Diagnosis>> violations = validator.validate(diagnosis);

        // Assert
        assertTrue(violations.isEmpty(), "50 character code should be valid");
    }

    @Test
    void nameValidation_WhenExactly255Characters_ShouldPassValidation() {
        // Arrange
        String validName = "N".repeat(255);
        Diagnosis diagnosis = new Diagnosis(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "Z00",
            validName
        );

        // Act
        Set<ConstraintViolation<Diagnosis>> violations = validator.validate(diagnosis);

        // Assert
        assertTrue(violations.isEmpty(), "255 character name should be valid");
    }
}
