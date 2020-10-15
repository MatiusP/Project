package by.epamtc.protsko.rentcar.service.validator;

import java.util.regex.Pattern;

public class SurnameValidatorUserCommand implements UserCommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userSurname) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.SURNAME);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userSurname).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "";
    }
}
