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
            UUID.randomUUID().toString(),
            "Dr. Sarah Smith",
            true
        );

        // Act
        Set<ConstraintViolation<Doctor>> violations = validator.validate(doctor);

        // Assert
        assertTrue(violations.isEmpty(), "Valid doctor should have no violations");
    }

    @Test
    void userIdValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        Doctor doctor = new Doctor(
            UUID.randomUUID().toString(),
            "   ",  // Blank user ID
            "Dr. John Doe",
            false
        );

        // Act
        Set<ConstraintViolation<Doctor>> violations = validator.validate(doctor);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Doctor> violation = violations.iterator().next();
        assertEquals("User ID is required", violation.getMessage());
        assertEquals("userId", violation.getPropertyPath().toString());
    }

    @Test
    void nameValidation_WhenExceeds255Characters_ShouldFailWithSizeViolation() {
        // Arrange
        String longName = "Dr. " + "X".repeat(252); // 256 characters
        Doctor doctor = new Doctor(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            longName,
            true
        );

        // Act
        Set<ConstraintViolation<Doctor>> violations = validator.validate(doctor);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Doctor> violation = violations.iterator().next();
        assertEquals("Doctor name must be at most 255 characters", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
    }

    @Test
    void nameValidation_WhenExactly255Characters_ShouldPassValidation() {
        // Arrange
        String maxLengthName = "Dr. " + "Y".repeat(251); // 255 characters
        Doctor doctor = new Doctor(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            maxLengthName,
            false
        );

        // Act
        Set<ConstraintViolation<Doctor>> violations = validator.validate(doctor);

        // Assert
        assertTrue(violations.isEmpty(), "255 character name should be valid");
    }

    @Test
    void nameValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        Doctor doctor = new Doctor(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            "   ",  // Blank name
            true
        );

        // Act
        Set<ConstraintViolation<Doctor>> violations = validator.validate(doctor);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Doctor> violation = violations.iterator().next();
        assertEquals("Doctor name is required", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
    }
}
