package by.epamtc.protsko.rentcar.service.validator.user;

import java.util.regex.Pattern;

public class LoginValidatorUserCommand implements UserCommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userLogin) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.LOGIN);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userLogin).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "The login can contain only latin or russian letters and numbers. Login length: 5 to 45 characters";
    }
}