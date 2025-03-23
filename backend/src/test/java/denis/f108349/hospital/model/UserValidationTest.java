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
            "John",
            "Doe",
            "7501020018"
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
            " ",
            "John",
            "Doe",
            "7501020018"
        );

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Username must be between 4 and 22 characters", violations.iterator().next().getMessage());
    }

    @Test
    void firstNameValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        User user = new User(
            UUID.randomUUID().toString(),
            "john_doe",
            " ",
            "Doe",
            "7501020018"
        );

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("First name is required", violation.getMessage());
        assertEquals("firstName", violation.getPropertyPath().toString());
    }
    
    @Test
    void lastNameValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        User user = new User(
            UUID.randomUUID().toString(),
            "john_doe",
            "John",
            " ",
            "7501020018"
        );

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Last name is required", violation.getMessage());
        assertEquals("lastName", violation.getPropertyPath().toString());
    }
    
    @Test
    void egnValidation_WhenInvalidLength_ShouldFailWithSizeViolation() {
        // Arrange
        User user = new User(
            UUID.randomUUID().toString(),
            "john_doe",
            "John",
            "Doe",
            "750100018"
        );

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Invalid EGN provided", 
                    violations.iterator().next().getMessage());
    }

    @Test
    void egnValidation_WhenContainsLetters_ShouldFailWithPatternViolation() {
        // Arrange
        User user = new User(
            UUID.randomUUID().toString(),
            "john_doe",
            "John",
            "Doe",
            "75010f0018"
        );

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Invalid EGN provided", 
                    violations.iterator().next().getMessage());
    }
}
