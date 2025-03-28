package denis.f108349.hospital.data.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EGNValidator implements ConstraintValidator<ValidEGN, String> {
    private static final Pattern EGN_PATTERN = Pattern.compile("^(\\d{2})(0[1-9]|1[0-2]|4[0-9]|5[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}\\d$");
    
    @Override
    public void initialize(ValidEGN constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || !EGN_PATTERN.matcher(s).matches()) {
            return false;
        }
        
        return this.isValidChecksum(s);
    }
    
    private boolean isValidChecksum(String egn) {
        int[] weights = {2, 4, 8, 5, 10, 9, 7, 3, 6};
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (egn.charAt(i) - '0') * weights[i];
        }
        int checksum = sum % 11;
        if (checksum == 10) checksum = 0;
        return checksum == (egn.charAt(9) - '0');
    }
}
