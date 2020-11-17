package by.epamtc.protsko.rentcar.service.validator.user;

import java.util.regex.Pattern;

/**
 * The class {@code SurnameValidatorUserCommand} validates
 * user's surname.
 *
 * @author Matvey Protsko
 */
public class SurnameValidatorUserCommand implements UserCommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userSurname) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.SURNAME);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userSurname).matches();
    }
}
