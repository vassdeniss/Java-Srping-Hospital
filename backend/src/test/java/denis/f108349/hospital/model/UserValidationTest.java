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

public class UserValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void userValidation_WithValidFields_ShouldPassWithoutViolations() {
        // Arrange
        User user = new User(
            UUID.randomUUID().toString(),
            "john_doe",
            "secureHash123",
            User.Role.DOCTOR
        );

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertTrue(violations.isEmpty(), "Valid user should have no violations");
    }

    @Test
    void usernameValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        User user = new User(
            UUID.randomUUID().toString(),
            "",  // Blank username
            "secureHash123",
            User.Role.ADMIN
        );

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Username must be between 4 and 22 characters", violations.iterator().next().getMessage());
    }

    @Test
    void usernameValidation_WhenExceeds22Characters_ShouldFailWithSizeViolation() {
        // Arrange
        String longUsername = "a".repeat(23);
        User user = new User(
            UUID.randomUUID().toString(),
            longUsername,
            "secureHash123",
            User.Role.PATIENT
        );

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Username must be between 4 and 22 characters", 
                    violations.iterator().next().getMessage());
    }

    @Test
    void passwordValidation_WhenHashEmpty_ShouldFailWithRequiredMessage() {
        // Arrange
        User user = new User(
            UUID.randomUUID().toString(),
            "jane_doe",
            "",  // Empty password hash
            User.Role.DOCTOR
        );

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Password hash is required", violations.iterator().next().getMessage());
    }

    @Test
    void roleValidation_WhenInvalidValue_ShouldFailWithPatternMismatch() {
        // Arrange
        User user = new User(
            UUID.randomUUID().toString(),
            "invalid_role_user",
            "secureHash123",
            null
        );

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Role is required", violations.iterator().next().getMessage());
    }

    @Test
    void userValidation_WhenRequiredFieldsNull_ShouldReturnMultipleViolations() {
        // Arrange
        User user = new User(
            UUID.randomUUID().toString(),
            "",
            "",
            null
        );  // All required fields null
        
        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertEquals(3, violations.size(), 
                    "Should violate username, password, and role constraints");
    }
}
