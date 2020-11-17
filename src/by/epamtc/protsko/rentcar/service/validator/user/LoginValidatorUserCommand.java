package by.epamtc.protsko.rentcar.service.validator.user;

import java.util.regex.Pattern;

/**
 * The class {@code LoginValidatorUserCommand} validates
 * user's login.
 *
 * @author Matvey Protsko
 */
public class LoginValidatorUserCommand implements UserCommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userLogin) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.LOGIN);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userLogin).matches();
    }
}
