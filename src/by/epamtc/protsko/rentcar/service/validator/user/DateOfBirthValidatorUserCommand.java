package by.epamtc.protsko.rentcar.service.validator.user;

import java.util.regex.Pattern;

/**
 * The class {@code DateOfBirthValidatorUserCommand} validates
 * user's date of birth.
 *
 * @author Matvey Protsko
 */
public class DateOfBirthValidatorUserCommand implements UserCommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userDateOfBirth) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.DATE_OF_BIRTH);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userDateOfBirth).matches();
    }
}
