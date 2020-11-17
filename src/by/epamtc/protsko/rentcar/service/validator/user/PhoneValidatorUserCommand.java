package by.epamtc.protsko.rentcar.service.validator.user;

import java.util.regex.Pattern;

/**
 * The class {@code PhoneValidatorUserCommand} validates
 * user's phone number.
 *
 * @author Matvey Protsko
 */
public class PhoneValidatorUserCommand implements UserCommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userPhone) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.PHONE);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userPhone).matches();
    }
}
