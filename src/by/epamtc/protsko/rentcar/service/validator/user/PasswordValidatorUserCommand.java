package by.epamtc.protsko.rentcar.service.validator.user;

import java.util.regex.Pattern;

public class PasswordValidatorUserCommand implements UserCommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userPassword) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.PASSWORD);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userPassword).matches();
    }
}
