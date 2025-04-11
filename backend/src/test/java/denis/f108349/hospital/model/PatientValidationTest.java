package denis.f108349.hospital.model;

import denis.f108349.hospital.data.model.Patient;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PatientValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void patientValidation_WithValidFields_ShouldPassWithoutViolations() {
        // Arrange
        Patient patient = new Patient(
            UUID.randomUUID().toString(), null, null
        );

        // Act
        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);

        // Assert
        assertTrue(violations.isEmpty(), "Valid patient should have no violations");
    }

    @Test
    void keycloakIdValidation_WhenBlank_ShouldFailWithRequiredMessage() {
        // Arrange
        Patient patient = new Patient(
            " ", null, null
        );

        // Act
        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<Patient> violation = violations.iterator().next();
        assertEquals("Keycloak ID is required", violation.getMessage());
        assertEquals("keycloakId", violation.getPropertyPath().toString());
    }

    @Test
    void lastPaymentDateValidation_WhenFutureDate_ShouldFailWithPastOrPresentViolation() {
        // Arrange
        Patient patient = new Patient(
            UUID.randomUUID().toString(), null, null
        );
        patient.setLastPaymentDate(LocalDate.now().plusDays(1));

        // Act
        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("Last payment date cannot be in the future", 
                    violations.iterator().next().getMessage());
    }
}
